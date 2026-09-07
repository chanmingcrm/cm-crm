package com.platform.mesh.mq.soa.redis;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.domain.ro.MqSubscription;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.service.impl.MqConsumerProvider;
import com.platform.mesh.mq.soa.StreamMqProvider;
import org.redisson.api.RStream;
import org.redisson.api.RTopic;
import org.redisson.api.RMapCache;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.stream.StreamAddArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 * 〈Redis Topic 与 Stream MQ 适配器〉
 * @author 蝉鸣
 */
public class RedisMqProvider implements MqConsumerProvider {

    private final RedissonClient redissonClient;
    private final int queueShards;
    private final int maxAttempts;
    private final long retryBackoffMillis;

    /**
     * 功能描述:
     * 〈初始化 Redis MQ 提供方〉
     * @param redissonClient Redisson 客户端
     * @param queueShards 队列分片数
     * @author 蝉鸣
     */
    public RedisMqProvider(RedissonClient redissonClient, int queueShards) {
        this(redissonClient, queueShards, 3, 1000);
    }

    public RedisMqProvider(RedissonClient redissonClient, int queueShards,
            int maxAttempts, long retryBackoffMillis) {
        if (maxAttempts < 1 || retryBackoffMillis < 1) {
            throw MqExceptionEnum.RETRY_POLICY_INVALID.getBaseException(List.of(maxAttempts, retryBackoffMillis));
        }
        if (queueShards < 1) {
            throw MqExceptionEnum.QUEUE_SHARDS_INVALID.getBaseException();
        }
        this.redissonClient = redissonClient;
        this.queueShards = queueShards;
        this.maxAttempts = maxAttempts;
        this.retryBackoffMillis = retryBackoffMillis;
    }

    /**
     * 功能描述:
     * 〈获取 Redis 提供方类型〉
     * @return Redis 提供方类型
     * @author 蝉鸣
     */
    @Override
    public MqProviderType type() {
        return MqProviderType.REDIS;
    }

    /**
     * 功能描述:
     * 〈获取 Redis 支持的投递模式〉
     * @return 广播与队列投递模式
     * @author 蝉鸣
     */
    @Override
    public Set<MqMode> supportedModes() {
        return Set.of(MqMode.BROADCAST, MqMode.QUEUE);
    }

    /**
     * 功能描述:
     * 〈通过 Redis 发布广播或有序队列消息〉
     * @param message 统一 MQ 消息
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    @Override
    public MqPublishResult publish(MqMessage<?> message) {
        // 1. 广播消息直接发布到 Redis Topic。
        if (message.mode() == MqMode.BROADCAST) {
            String destination = StreamMqProvider.destination(message.topic(), message.mode());
            long receivers = redissonClient.getTopic(destination).publish(message);
            return new MqPublishResult(message.messageId(), Long.toString(receivers));
        }
        // 2. 队列消息依据顺序键固定分片，同键消息进入同一 Redis Stream。
        int shard = shard(message.orderingKey(), queueShards);
        String destination = StreamMqProvider.queueDestination(message.topic(), shard);
        RStream<String, Object> stream = redissonClient.getStream(destination);
        Object receipt = stream.add(StreamAddArgs.entry("message", message));
        return new MqPublishResult(message.messageId(), String.valueOf(receipt));
    }

    /**
     * 功能描述:
     * 〈为逻辑消费者订阅 Redis 广播和队列消息〉
     * @param subscription 逻辑消费者订阅信息
     * @param consumer 消息消费函数
     * @return 可关闭的订阅句柄
     * @author 蝉鸣
     */
    @Override
    public AutoCloseable subscribe(MqSubscription subscription,
            Consumer<MqMessage<?>> consumer) {
        registerConsumerIdentity(subscription.consumerId());
        List<AutoCloseable> handles = new ArrayList<>();
        try {
            RTopic topic = redissonClient.getTopic(
                    StreamMqProvider.destination(subscription.topic(), MqMode.BROADCAST));
            RMapCache<String, Boolean> deliveries = redissonClient.getMapCache(
                    "mesh.mq.broadcast." + Integer.toHexString(subscription.consumerId().hashCode()));
            int listenerId = topic.addListener(MqMessage.class,
                    (channel, message) -> consumeBroadcastOnce(deliveries, message, consumer));
            handles.add(() -> topic.removeListener(listenerId));
            for (int shard = 0; shard < queueShards; shard++) {
                RedisStreamSubscription handle = new RedisStreamSubscription(redissonClient,
                        subscription, shard, consumer, maxAttempts, retryBackoffMillis);
                handles.add(handle);
                handle.start();
            }
        } catch (RuntimeException | Error failure) {
            try {
                closeAll(handles);
            } catch (Exception cleanupFailure) {
                failure.addSuppressed(cleanupFailure);
            }
            throw failure;
        }
        return () -> closeAll(handles);
    }

    private void registerConsumerIdentity(String consumerId) {
        // Keep legacy group/dedup keys; never silently migrate or replay existing streams.
        // Global scope is required because broadcast delivery records are shared across topics.
        RMap<String, String> identities = redissonClient.getMap("mesh.mq.consumer.identities");
        String legacyId = Integer.toHexString(consumerId.hashCode());
        String existing = identities.putIfAbsent(legacyId, consumerId);
        if (existing != null && !existing.equals(consumerId)) {
            throw MqExceptionEnum.REDIS_CONSUMER_IDENTITY_CONFLICT
                    .getBaseException(List.of(legacyId, existing, consumerId));
        }
    }

    private static void closeAll(List<AutoCloseable> handles) throws Exception {
        Exception failure = null;
        boolean interrupted = Thread.interrupted();
        for (AutoCloseable handle : handles) {
            try {
                handle.close();
            } catch (Exception exception) {
                if (exception instanceof InterruptedException) interrupted = true;
                if (failure == null) failure = exception;
                else failure.addSuppressed(exception);
            } finally {
                interrupted |= Thread.interrupted();
            }
        }
        if (interrupted) Thread.currentThread().interrupt();
        if (failure != null) throw failure;
    }

    /**
     * 功能描述:
     * 〈根据顺序键计算 Redis 队列分片〉
     * @param orderingKey 顺序键
     * @param shardCount 分片总数
     * @return 分片编号
     * @author 蝉鸣
     */
    static int shard(String orderingKey, int shardCount) {
        return Math.floorMod(orderingKey.hashCode(), shardCount);
    }

    /**
     * 功能描述:
     * 〈保证同一逻辑消费者只消费一次广播消息〉
     * @param deliveries 广播投递记录
     * @param message 统一 MQ 消息
     * @param consumer 消息消费函数
     * @author 蝉鸣
     */
    private void consumeBroadcastOnce(RMapCache<String, Boolean> deliveries,
            MqMessage<?> message, Consumer<MqMessage<?>> consumer) {
        Boolean previous = deliveries.putIfAbsent(
                message.messageId(), Boolean.TRUE, 24, TimeUnit.HOURS);
        if (previous != null) return;
        try {
            consumer.accept(message);
        } catch (RuntimeException exception) {
            deliveries.remove(message.messageId());
            throw exception;
        }
    }
}
