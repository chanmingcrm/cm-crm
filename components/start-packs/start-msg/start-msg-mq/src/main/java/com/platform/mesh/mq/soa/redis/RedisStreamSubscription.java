package com.platform.mesh.mq.soa.redis;

import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqSubscription;
import com.platform.mesh.mq.soa.StreamMqProvider;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.stream.PendingEntry;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.api.stream.StreamMessageId;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** One watchdog-protected worker per consumer group and stream shard. */
final class RedisStreamSubscription implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(RedisStreamSubscription.class);
    private final RedissonClient client;
    private final MqSubscription subscription;
    private final int shard;
    private final Consumer<MqMessage<?>> consumer;
    private final int maxAttempts;
    private final long retryBackoffMillis;
    private final String workerId = UUID.randomUUID().toString();
    private volatile boolean running;
    private Thread worker;

    RedisStreamSubscription(RedissonClient client, MqSubscription subscription,
            int shard, Consumer<MqMessage<?>> consumer) {
        this(client, subscription, shard, consumer, 3, 1000);
    }

    RedisStreamSubscription(RedissonClient client, MqSubscription subscription,
            int shard, Consumer<MqMessage<?>> consumer, int maxAttempts, long retryBackoffMillis) {
        this.client = client;
        this.subscription = subscription;
        this.shard = shard;
        this.consumer = consumer;
        this.maxAttempts = maxAttempts;
        this.retryBackoffMillis = retryBackoffMillis;
    }

    void start() {
        running = true;
        worker = Thread.ofVirtual().name("mq-redis-" + shard + "-" + workerId).start(this::consumeLoop);
    }

    @Override
    public void close() throws InterruptedException {
        running = false;
        if (worker != null && worker != Thread.currentThread()) {
            worker.interrupt();
            worker.join(Duration.ofSeconds(5));
            if (worker.isAlive()) {
                log.warn("Redis MQ consumer has not stopped; retaining ownership until callback exits: topic={}, shard={}",
                        subscription.topic(), shard);
            }
        }
    }

    private boolean active() {
        return running && !Thread.currentThread().isInterrupted();
    }

    private void consumeLoop() {
        String streamName = StreamMqProvider.queueDestination(subscription.topic(), shard);
        String group = Integer.toHexString(subscription.consumerId().hashCode());
        RStream<String, Object> stream = client.getStream(streamName);
        RMap<String, Map<String, Object>> attempts = client.getMap(streamName + ":" + group + ":attempts");
        RLock lock = client.getLock(streamName + ":" + group + ":active");
        while (active()) {
            boolean locked = false;
            try {
                // No fixed lease: Redisson's watchdog renews ownership during slow callbacks.
                locked = lock.tryLock(1, TimeUnit.SECONDS);
                if (!locked) continue;
                ensureGroup(stream, group);
                cleanAcknowledgedAttempts(stream, group, attempts, lock);
                while (active() && lock.isHeldByCurrentThread()) {
                    // Pending always precedes new deliveries, including entries left by another process.
                    List<PendingEntry> pending = stream.listPending(group, StreamMessageId.MIN, StreamMessageId.MAX, 1);
                    Map<StreamMessageId, Map<String, Object>> entries;
                    if (!pending.isEmpty()) {
                        entries = stream.claim(group, workerId, 0, TimeUnit.MILLISECONDS, pending.getFirst().getId());
                        if (entries.isEmpty()) {
                            throw MqExceptionEnum.REDIS_PENDING_PAYLOAD_MISSING
                                    .getBaseException(List.of(pending.getFirst().getId()));
                        }
                    } else {
                        entries = stream.readGroup(group, workerId, StreamReadGroupArgs.neverDelivered()
                                .count(1).timeout(Duration.ofSeconds(1)));
                    }
                    for (var entry : entries.entrySet()) {
                        deliver(streamName, group, stream, attempts, lock, entry);
                    }
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            } catch (RuntimeException exception) {
                if (active()) {
                    log.error("Redis MQ shard will retry: stream={}, group={}", streamName, group, exception);
                    pause(retryBackoffMillis);
                }
            } finally {
                if (locked) unlockQuietly(lock, streamName, group);
            }
        }
    }

    private void cleanAcknowledgedAttempts(RStream<String, Object> stream, String group,
            RMap<String, Map<String, Object>> attempts, RLock lock) {
        // Recover the ACK -> HDEL failure window after reconnect/restart. Never expire
        // pending retry state: that would reset poison-message attempts during an outage.
        for (var state : attempts.entrySet()) {
            if (!active() || !lock.isHeldByCurrentThread()) return;
            if (!Boolean.TRUE.equals(state.getValue().get("completed"))) continue;
            String[] parts = state.getKey().split("-", 2);
            StreamMessageId id = new StreamMessageId(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
            if (stream.listPending(group, id, id, 1).isEmpty()) {
                attempts.remove(state.getKey());
            }
        }
    }

    private void ensureGroup(RStream<String, Object> stream, String group) {
        try {
            stream.createGroup(StreamCreateGroupArgs.name(group).id(new StreamMessageId(0, 0)).makeStream());
        } catch (RuntimeException exception) {
            // Do not confuse a connection, authorization or WRONGTYPE error with an existing group.
            for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
                if (cause.getMessage() != null && cause.getMessage().contains("BUSYGROUP")) return;
            }
            throw exception;
        }
    }

    private void deliver(String streamName, String group, RStream<String, Object> stream,
            RMap<String, Map<String, Object>> attempts, RLock lock,
            Map.Entry<StreamMessageId, Map<String, Object>> entry) {
        String id = entry.getKey().toString();
        Map<String, Object> saved = attempts.get(id);
        Map<String, Object> state = saved == null ? new HashMap<>() : new HashMap<>(saved);
        while (active() && lock.isHeldByCurrentThread()) {
            int failures = ((Number) state.getOrDefault("attempts", 0)).intValue();
            if (!Boolean.TRUE.equals(state.get("completed")) && failures < maxAttempts) {
                try {
                    Object envelope = entry.getValue().get("message");
                    if (!(envelope instanceof MqMessage<?> message)) {
                        throw MqExceptionEnum.MESSAGE_FORMAT_INVALID.getBaseException();
                    }
                    consumer.accept(message);
                } catch (Exception exception) {
                    if (!active() || !lock.isHeldByCurrentThread()) return;
                    failures++;
                    state.put("attempts", failures);
                    String reason = exception.getClass().getName() + ": " + exception.getMessage();
                    state.put("failureReason", reason.substring(0, Math.min(reason.length(), 4096)));
                    attempts.put(id, state);
                    log.warn("Redis MQ delivery failed: stream={}, group={}, entry={}, attempt={}/{}",
                            streamName, group, id, failures, maxAttempts, exception);
                    if (failures < maxAttempts) pause(backoff(failures));
                    continue;
                }
                if (!active() || !lock.isHeldByCurrentThread()) return;
                state.put("completed", true);
                attempts.put(id, state);
            }
            if (!active() || !lock.isHeldByCurrentThread()) return;
            if (!Boolean.TRUE.equals(state.get("completed"))) {
                // Persist the envelope before ACK. Ambiguous connection failures may duplicate DLQ entries.
                Map<String, Object> deadLetter = new HashMap<>(entry.getValue());
                deadLetter.putAll(state);
                deadLetter.put("originalStream", streamName);
                deadLetter.put("originalEntryId", id);
                deadLetter.put("consumerGroup", group);
                deadLetter.put("failedAt", Instant.now().toString());
                if (entry.getValue().get("message") instanceof MqMessage<?> message) {
                    deadLetter.put("messageId", message.messageId());
                    deadLetter.put("topic", message.topic());
                    deadLetter.put("orderingKey", message.orderingKey());
                }
                RStream<String, Object> dlq = client.getStream(streamName + "_dlq");
                dlq.add(StreamAddArgs.entries(deadLetter));
                state.put("completed", true);
                attempts.put(id, state);
                log.error("Redis MQ moved to dead letter: stream={}, group={}, entry={}, attempts={}",
                        streamName, group, id, failures);
            }
            if (!active() || !lock.isHeldByCurrentThread()) return;
            stream.ack(group, entry.getKey());
            attempts.remove(id);
            return;
        }
    }

    private long backoff(int failures) {
        long factor = 1L << Math.min(failures - 1, 5);
        return retryBackoffMillis > 30000 / factor ? 30000 : retryBackoffMillis * factor;
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private void unlockQuietly(RLock lock, String streamName, String group) {
        // Clear interruption briefly so Redisson can actually send the unlock command on shutdown.
        boolean interrupted = Thread.interrupted();
        try {
            if (lock.isHeldByCurrentThread()) lock.unlock();
        } catch (RuntimeException exception) {
            log.warn("Redis MQ lock release failed: stream={}, group={}", streamName, group, exception);
        } finally {
            if (interrupted) Thread.currentThread().interrupt();
        }
    }
}
