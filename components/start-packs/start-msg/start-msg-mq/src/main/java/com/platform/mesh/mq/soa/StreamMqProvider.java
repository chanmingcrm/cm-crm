package com.platform.mesh.mq.soa;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.domain.ro.MqSubscription;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.constant.MqConst;
import com.platform.mesh.mq.soa.rabbitmq.RabbitDeadLetterPublisher;
import org.springframework.cloud.stream.binder.rabbit.RabbitMessageChannelBinder;
import org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.amqp.core.AcknowledgeMode;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.platform.mesh.mq.service.impl.MqConsumerProvider;
import com.alibaba.cloud.stream.binder.rocketmq.RocketMQMessageChannelBinder;
import com.alibaba.cloud.stream.binder.rocketmq.constant.RocketMQConst;
import org.springframework.cloud.stream.binder.BinderHeaders;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;
import tools.jackson.databind.cfg.DateTimeFeature;

import java.util.Set;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.kafka.support.KafkaHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能描述:
 * 〈基于 Spring Cloud Stream 的外部 MQ 适配器〉
 * @author 蝉鸣
 */
public class StreamMqProvider implements MqConsumerProvider {

    public static final String ORDERING_KEY = "mesh_mq_ordering_key";
    public static final String MESSAGE_ID = "mesh_mq_message_id";
    public static final String MODE = "mesh_mq_mode";

    private final MqProviderType type;
    private final StreamBridge streamBridge;
    private final BindingService bindingService;
    private final ObjectMapper objectMapper;
    private final ObjectReader envelopeReader;
    private final int queuePartitions;
    private final int maxAttempts;
    private final long retryBackoffMillis;
    private final Set<String> preparedProducers = ConcurrentHashMap.newKeySet();
    private static final Logger log = LoggerFactory.getLogger(StreamMqProvider.class);

    /**
     * 功能描述:
     * 〈初始化仅支持发布的外部 MQ 提供方〉
     * @param type MQ 提供方类型
     * @param streamBridge Spring Cloud Stream 发布桥接器
     * @author 蝉鸣
     */
    public StreamMqProvider(MqProviderType type, StreamBridge streamBridge) {
        this(type, streamBridge, null, new ObjectMapper(), 16);
    }

    /**
     * 功能描述:
     * 〈初始化支持发布和订阅的外部 MQ 提供方〉
     * @param type MQ 提供方类型
     * @param streamBridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @author 蝉鸣
     */
    public StreamMqProvider(MqProviderType type, StreamBridge streamBridge,
            BindingService bindingService) {
        this(type, streamBridge, bindingService, new ObjectMapper(), 16);
    }

    /**
     * 功能描述:
     * 〈初始化支持消息反序列化的外部 MQ 提供方〉
     * @param type MQ 提供方类型
     * @param streamBridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @param objectMapper JSON 对象映射器
     * @author 蝉鸣
     */
    public StreamMqProvider(MqProviderType type, StreamBridge streamBridge,
            BindingService bindingService, ObjectMapper objectMapper) {
        this(type, streamBridge, bindingService, objectMapper, 16);
    }

    /**
     * 功能描述:
     * 〈初始化可配置队列分区数的外部 MQ 提供方〉
     * @param type MQ 提供方类型
     * @param streamBridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @param queuePartitions 队列分区数
     * @author 蝉鸣
     */
    public StreamMqProvider(MqProviderType type, StreamBridge streamBridge,
            BindingService bindingService, ObjectMapper objectMapper, int queuePartitions) {
        this(type, streamBridge, bindingService, objectMapper, queuePartitions, 3, 1000);
    }

    public StreamMqProvider(MqProviderType type, StreamBridge streamBridge,
            BindingService bindingService, ObjectMapper objectMapper, int queuePartitions,
            int maxAttempts, long retryBackoffMillis) {
        if (maxAttempts < 1 || retryBackoffMillis < 0) {
            throw MqExceptionEnum.RETRY_POLICY_INVALID.getBaseException(List.of(maxAttempts, retryBackoffMillis));
        }
        this.maxAttempts = maxAttempts;
        this.retryBackoffMillis = retryBackoffMillis;
        if (type == MqProviderType.EVENT || type == MqProviderType.REDIS) {
            throw MqExceptionEnum.STREAM_PROVIDER_TYPE_INVALID.getBaseException();
        }
        this.type = type;
        this.streamBridge = streamBridge;
        this.bindingService = bindingService;
        this.objectMapper = objectMapper;
        // The MQ wire format uses epoch milliseconds; keep ISO-8601 parsing and
        // configure only this reader rather than changing the application mapper.
        this.envelopeReader = objectMapper.readerFor(MqMessage.class)
                .without(DateTimeFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        if (queuePartitions < 1) {
            throw MqExceptionEnum.QUEUE_PARTITIONS_INVALID.getBaseException();
        }
        this.queuePartitions = queuePartitions;
    }

    /**
     * 功能描述:
     * 〈获取外部 MQ 提供方类型〉
     * @return MQ 提供方类型
     * @author 蝉鸣
     */
    @Override
    public MqProviderType type() {
        return type;
    }

    /**
     * 功能描述:
     * 〈获取外部 MQ 支持的投递模式〉
     * @return 广播与队列投递模式
     * @author 蝉鸣
     */
    @Override
    public Set<MqMode> supportedModes() {
        return Set.of(MqMode.BROADCAST, MqMode.QUEUE);
    }

    /**
     * 功能描述:
     * 〈通过指定 Binder 发布统一 MQ 消息〉
     * @param message 统一 MQ 消息
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    @Override
    public MqPublishResult publish(MqMessage<?> message) {
        // 1. 将统一消息信封和路由元数据构建为 Spring 消息。
        MessageBuilder<?> builder = MessageBuilder.withPayload(message)
                .setHeader(MESSAGE_ID, message.messageId())
                .setHeader(MODE, message.mode().name());
        if (type == MqProviderType.ROCKETMQ) {
            builder.setHeader(RocketMQConst.Headers.KEYS, message.messageId());
        }
        int queueShard = 0;
        if (message.orderingKey() != null) {
            // 2. 相同顺序键固定映射到同一队列分片，不同顺序键可由不同分片并行处理。
            builder.setHeader(ORDERING_KEY, message.orderingKey());
            queueShard = Math.floorMod(message.orderingKey().hashCode(), queuePartitions);
            builder.setHeader(BinderHeaders.PARTITION_OVERRIDE, 0);
        }
        // 3. 根据业务主题和投递模式生成目标地址，并明确选择提供方 Binder。
        Message<?> brokerMessage = builder.build();
        String destination = message.mode() == MqMode.QUEUE
                ? queueDestination(message.topic(), queueShard)
                : destination(message.topic(), message.mode());
        prepareProducerBinding(destination, message.mode());
        boolean sent = streamBridge.send(destination, type.name().toLowerCase(), brokerMessage);
        if (!sent) {
            throw MqExceptionEnum.PUBLISH_FAILED.getBaseException(
                    List.of(type + " " + destination));
        }
        return new MqPublishResult(message.messageId(), destination);
    }

    /**
     * 功能描述:
     * 〈为动态发布目标配置固定物理分区〉
     * @param destination 中间件目标地址
     * @param mode 投递模式
     * @author 蝉鸣
     */
    private synchronized void prepareProducerBinding(String destination, MqMode mode) {
        if (bindingService == null || preparedProducers.contains(destination)) return;
        BindingServiceProperties serviceProperties = bindingService.getBindingServiceProperties();
        serviceProperties.getBindings().computeIfAbsent(destination, key -> {
            BindingProperties properties = new BindingProperties();
            properties.setDestination(destination);
            properties.setBinder(type.name().toLowerCase());
            ProducerProperties producer = new ProducerProperties();
            producer.setErrorChannelEnabled(false);
            properties.setProducer(producer);
            return properties;
        });
        var wrapper = bindingService.createBinderWrapper(
                type.name().toLowerCase(), destination, MessageChannel.class);
        if (wrapper.binder() instanceof RocketMQMessageChannelBinder binder) {
            var producer = binder.getExtendedProducerProperties(destination);
            producer.setSendType("SYNC");
            producer.setSendFailureChannel(null);
            if (mode == MqMode.QUEUE) {
                producer.setMessageQueueSelector(MqConst.ROCKETMQ_QUEUE_SELECTOR_BEAN);
            }
        } else if (wrapper.binder() instanceof RabbitMessageChannelBinder binder) {
            // basicPublish alone only writes to a socket. A commit fences successive
            // sends even when different calling threads borrow different AMQP channels.
            binder.getExtendedProducerProperties(destination).setTransacted(true);
            binder.getExtendedProducerProperties(destination).setBatchingEnabled(false);
        } else if (wrapper.binder() instanceof KafkaMessageChannelBinder binder) {
            binder.getExtendedProducerProperties(destination).setSync(true);
        }
        preparedProducers.add(destination);
    }

    /**
     * 功能描述:
     * 〈动态创建广播和队列消费绑定〉
     * @param subscription 逻辑消费者订阅信息
     * @param consumer 消息消费函数
     * @return 可关闭的绑定句柄
     * @author 蝉鸣
     */
    @Override
    public AutoCloseable subscribe(MqSubscription subscription,
            Consumer<MqMessage<?>> consumer) {
        if (bindingService == null) {
            throw MqExceptionEnum.BINDING_SERVICE_MISSING.getBaseException();
        }
        List<String> bindings = new ArrayList<>();
        AtomicBoolean active = new AtomicBoolean(true);
        String subscriptionId = UUID.randomUUID().toString();
        try {
            bind(subscription, consumer, MqMode.BROADCAST, -1,
                    destination(subscription.topic(), MqMode.BROADCAST), bindings, active, subscriptionId);
            for (int shard = 0; shard < queuePartitions; shard++) {
                bind(subscription, consumer, MqMode.QUEUE, shard,
                        queueDestination(subscription.topic(), shard), bindings, active, subscriptionId);
            }
        } catch (RuntimeException | Error failure) {
            active.set(false);
            try {
                unbind(bindings);
            } catch (RuntimeException cleanupFailure) {
                failure.addSuppressed(cleanupFailure);
            }
            throw failure;
        }
        return () -> {
            if (active.compareAndSet(true, false)) unbind(bindings);
        };
    }

    private void unbind(List<String> bindings) {
        RuntimeException failure = null;
        for (String name : bindings) {
            try {
                bindingService.unbindConsumers(name);
            } catch (RuntimeException exception) {
                if (failure == null) failure = exception;
                else failure.addSuppressed(exception);
            } finally {
                bindingService.getBindingServiceProperties().getBindings().remove(name);
            }
        }
        if (failure != null) throw failure;
    }

    /**
     * 功能描述:
     * 〈创建单个外部 MQ 动态消费绑定〉
     * @param subscription 逻辑消费者订阅信息
     * @param consumer 消息消费函数
     * @param mode 投递模式
     * @param shard 队列分片编号，广播为负数
     * @param destination 中间件目标地址
     * @param bindings 已创建绑定集合
     * @author 蝉鸣
     */
    private void bind(MqSubscription subscription, Consumer<MqMessage<?>> consumer,
            MqMode mode, int shard, String destination, List<String> bindings, AtomicBoolean active, String subscriptionId) {
        // 1. 注册目标地址、稳定消费者组及当前提供方 Binder。
        String bindingName = bindingName(subscription, mode, shard) + "." + subscriptionId;
        BindingProperties properties = new BindingProperties();
        properties.setDestination(destination);
        properties.setGroup(consumerGroup(subscription, mode, shard));
        properties.setBinder(type.name().toLowerCase());
        ConsumerProperties consumerProperties = new ConsumerProperties();
        consumerProperties.setConcurrency(1);
        // The listener owns retry and confirmed recovery; binder error channels must
        // never turn an unsuccessful dead-letter publish into an acknowledgement.
        consumerProperties.setMaxAttempts(1);
        properties.setConsumer(consumerProperties);
        BindingServiceProperties serviceProperties = bindingService.getBindingServiceProperties();
        serviceProperties.getBindings().put(bindingName, properties);
        bindings.add(bindingName);
        var wrapper = bindingService.createBinderWrapper(
                type.name().toLowerCase(), bindingName, MessageChannel.class);
        if (wrapper.binder() instanceof RabbitMessageChannelBinder binder) {
            var extension = binder.getExtendedConsumerProperties(bindingName);
            extension.setPrefetch(1);
            extension.setMaxConcurrency(1);
            extension.setAcknowledgeMode(AcknowledgeMode.AUTO);
            extension.setRepublishToDlq(false);
            extension.setRequeueRejected(true);
            // Exclusive consumers preserve the existing queue declaration while
            // allowing another instance to take over after this consumer disconnects.
            extension.setExclusive(true);
        } else if (wrapper.binder() instanceof RocketMQMessageChannelBinder binder) {
            var push = binder.getExtendedConsumerProperties(bindingName).getPush();
            push.setOrderly(true);
            push.setConsumeMessageBatchMaxSize(1);
            push.setMaxReconsumeTimes(Integer.MAX_VALUE);
        }
        // 2. 将中间件载荷还原为统一消息并交给业务监听方法。
        DirectChannel channel = new DirectChannel();
        Map<Integer, KafkaDeliveryFailure> kafkaFailures = new HashMap<>();
        channel.subscribe(springMessage -> {
            if (type == MqProviderType.KAFKA) {
                consumeKafkaWithRecovery(springMessage, consumer, destination,
                        subscription.consumerId(), properties.getGroup(), active, kafkaFailures);
            } else {
                consumeWithRecovery(springMessage, consumer, destination,
                        subscription.consumerId(), properties.getGroup(), active);
            }
        });
        bindingService.bindConsumer(channel, bindingName);
    }

    /**
     * 功能描述:
     * 〈单次执行 Kafka 消费或死信发送，失败交由可持续 poll 的容器暂停重试〉
     * @author 蝉鸣
     */
    private void consumeKafkaWithRecovery(Message<?> message, Consumer<MqMessage<?>> consumer,
            String destination, String consumerId, String consumerGroup, AtomicBoolean active,
            Map<Integer, KafkaDeliveryFailure> failures) {
        requireActive(active);
        Object partitionHeader = message.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION);
        Object offsetHeader = message.getHeaders().get(KafkaHeaders.OFFSET);
        if (!(partitionHeader instanceof Number partition) || !(offsetHeader instanceof Number offset)) {
            throw MqExceptionEnum.KAFKA_DELIVERY_METADATA_MISSING.getBaseException();
        }
        int partitionId = partition.intValue();
        long recordOffset = offset.longValue();
        KafkaDeliveryFailure saved = failures.get(partitionId);
        if (saved != null && saved.offset() != recordOffset) {
            // 每个绑定、分区最多保留当前失败 offset，避免长期重平衡累积历史状态。
            failures.remove(partitionId);
            saved = null;
        }
        if (saved == null || saved.attempts() < maxAttempts) {
            try {
                consumer.accept(toMqMessage(message.getPayload()));
                failures.remove(partitionId);
                return;
            } catch (RuntimeException failure) {
                saved = new KafkaDeliveryFailure(recordOffset,
                        saved == null ? 1 : saved.attempts() + 1, failure);
                failures.put(partitionId, saved);
                if (saved.attempts() < maxAttempts) throw failure;
            }
        }
        requireActive(active);
        // 失败状态保留至死信确认成功；DLQ 不可用时后续调用只重试发送，
        // 不再调用业务。状态不跨进程持久化，重启/跨实例重平衡仍是至少一次投递。
        publishDeadLetter(message, destination, consumerId, consumerGroup, saved.failure());
        failures.remove(partitionId);
    }

    private record KafkaDeliveryFailure(long offset, int attempts, RuntimeException failure) { }

    private void consumeWithRecovery(Message<?> message, Consumer<MqMessage<?>> consumer,
            String destination, String consumerId, String consumerGroup, AtomicBoolean active) {
        RuntimeException lastFailure = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            requireActive(active);
            try {
                consumer.accept(toMqMessage(message.getPayload()));
                return;
            } catch (RuntimeException failure) {
                lastFailure = failure;
                if (attempt < maxAttempts) pause(active);
            }
        }
        // Keep this delivery unacknowledged and block only its partition/queue.
        // Once business retries are exhausted, only retry durable DLQ publication.
        for (;;) {
            requireActive(active);
            try {
                publishDeadLetter(message, destination, consumerId, consumerGroup, lastFailure);
                return;
            } catch (RuntimeException failure) {
                log.warn("MQ dead-letter publish failed; retaining delivery for {}", destination, failure);
                pause(active);
            }
        }
    }

    private void publishDeadLetter(Message<?> message, String source, String consumerId,
            String consumerGroup, RuntimeException failure) {
        String destination = source + "_dlq";
        String reason = failure.getMessage() == null ? failure.getClass().getName() : failure.getMessage();
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("mesh_mq_original_destination", source);
        metadata.put("mesh_mq_consumer_id", consumerId);
        metadata.put("mesh_mq_consumer_group", consumerGroup);
        metadata.put("mesh_mq_attempts", maxAttempts);
        metadata.put("mesh_mq_failure_type", failure.getClass().getName());
        metadata.put("mesh_mq_failure_reason", reason.substring(0, Math.min(reason.length(), 4096)));
        String messageId = null;
        MqMessage<?> decodedEnvelope = null;
        try {
            decodedEnvelope = toMqMessage(message.getPayload());
            messageId = decodedEnvelope.messageId();
        } catch (RuntimeException invalidEnvelope) {
            // Malformed payloads still reach the DLQ; retain transport metadata if available.
            Object header = message.getHeaders().get(MESSAGE_ID);
            if (header != null) messageId = header.toString();
        }
        if (messageId != null) {
            metadata.put(MESSAGE_ID, messageId);
            if (type == MqProviderType.ROCKETMQ) metadata.put(RocketMQConst.Headers.KEYS, messageId);
        }
        if (type == MqProviderType.RABBITMQ) {
            var wrapper = bindingService.createBinderWrapper("rabbitmq", source, MessageChannel.class);
            if (!(wrapper.binder() instanceof RabbitMessageChannelBinder binder)) {
                throw MqExceptionEnum.STREAM_BINDER_UNAVAILABLE.getBaseException(List.of(MqProviderType.RABBITMQ));
            }
            Object payload = message.getPayload();
            byte[] bytes = payload instanceof byte[] raw ? raw
                    : payload instanceof String json ? json.getBytes(StandardCharsets.UTF_8)
                    : objectMapper.writeValueAsBytes(payload);
            RabbitDeadLetterPublisher.publish(binder.getConnectionFactory(), destination,
                    bytes, metadata, messageId);
            return;
        }
        prepareProducerBinding(destination, MqMode.BROADCAST);
        // Use neutral Java containers: Binder converters may use Jackson 2, which
        // treats a Jackson 3 JsonNode as a bean. Preserve numbers and unknown fields
        // without converting timestamps through Instant or encoding byte[] as Base64.
        Object raw = message.getPayload();
        Object deadLetterPayload = raw;
        boolean jsonPayload = true;
        try {
            ObjectReader jsonReader = objectMapper.readerFor(Object.class)
                    .with(tools.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
            if (raw instanceof byte[] bytes) deadLetterPayload = jsonReader.readValue(bytes);
            else if (raw instanceof String text) deadLetterPayload = jsonReader.readValue(text);
            if ((raw instanceof byte[] || raw instanceof String)
                    && !(deadLetterPayload instanceof Map<?, ?> || deadLetterPayload instanceof List<?>)) {
                // Preserve empty input and JSON scalars (including null) verbatim.
                throw MqExceptionEnum.MESSAGE_FORMAT_INVALID.getBaseException();
            }
        } catch (RuntimeException invalidJson) {
            jsonPayload = false;
            deadLetterPayload = raw instanceof String text ? text.getBytes(StandardCharsets.UTF_8) : raw;
        }
        Message<?> deadLetter = MessageBuilder.withPayload(deadLetterPayload)
                .copyHeaders(message.getHeaders())
                .removeHeader(BinderHeaders.PARTITION_OVERRIDE)
                .copyHeaders(metadata)
                .setHeader(org.springframework.messaging.MessageHeaders.CONTENT_TYPE,
                        !jsonPayload ? org.springframework.util.MimeTypeUtils.APPLICATION_OCTET_STREAM
                                : org.springframework.util.MimeTypeUtils.APPLICATION_JSON)
                .build();
        if (!streamBridge.send(destination, type.name().toLowerCase(), deadLetter)) {
            throw MqExceptionEnum.DEAD_LETTER_PUBLISH_FAILED.getBaseException(List.of(destination));
        }
    }

    private void pause(AtomicBoolean active) {
        long remaining = Math.max(1, retryBackoffMillis);
        while (remaining > 0) {
            requireActive(active);
            long interval = Math.min(100, remaining);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException failure) {
                Thread.currentThread().interrupt();
                var exception = MqExceptionEnum.DELIVERY_INTERRUPTED.getBaseException();
                exception.initCause(failure);
                throw exception;
            }
            remaining -= interval;
        }
    }

    private static void requireActive(AtomicBoolean active) {
        if (!active.get() || Thread.currentThread().isInterrupted()) {
            throw MqExceptionEnum.SUBSCRIPTION_CLOSED.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈将 Binder 接收的对象或 JSON 字节还原为统一消息〉
     * @param payload Binder 消息载荷
     * @return 统一 MQ 消息
     * @author 蝉鸣
     */
    private MqMessage<?> toMqMessage(Object payload) {
        if (payload instanceof MqMessage<?> mqMessage) {
            return mqMessage;
        }
        try {
            if (payload instanceof byte[] bytes) {
                return envelopeReader.readValue(bytes);
            }
            if (payload instanceof String json) {
                return envelopeReader.readValue(json);
            }
        } catch (RuntimeException exception) {
            throw MqExceptionEnum.MESSAGE_FORMAT_INVALID.getBaseException(exception);
        }
        throw MqExceptionEnum.MESSAGE_FORMAT_INVALID.getBaseException();
    }

    /**
     * 功能描述:
     * 〈生成业务主题对应的中间件目标地址〉
     * @param topic 业务主题
     * @param mode 投递模式
     * @return 中间件目标地址
     * @author 蝉鸣
     */
    public static String destination(String topic, MqMode mode) {
        String businessTopic = topic.trim();
        String normalizedTopic = businessTopic.replaceAll("[^a-zA-Z0-9_-]", "_");
        return "mesh_" + normalizedTopic + "_" + digest(businessTopic)
                + "_" + mode.name().toLowerCase();
    }

    /**
     * 功能描述:
     * 〈生成业务主题及顺序键分片对应的队列目标地址〉
     * @param topic 业务主题
     * @param shard 队列分片编号
     * @return 队列目标地址
     * @author 蝉鸣
     */
    public static String queueDestination(String topic, int shard) {
        return destination(topic, MqMode.QUEUE) + "_" + shard;
    }

    /**
     * 功能描述:
     * 〈生成逻辑监听器对应的消费者组〉
     * @param subscription 逻辑消费者订阅信息
     * @return 消费者组名称
     * @author 蝉鸣
     */
    static String consumerGroup(MqSubscription subscription) {
        return "mesh_" + digest(subscription.consumerId());
    }

    /**
     * 功能描述:
     * 〈生成投递模式及分片隔离的物理消费者组〉
     * @param subscription 逻辑消费者订阅信息
     * @param mode 投递模式
     * @param shard 队列分片编号，广播为负数
     * @return 物理消费者组名称
     * @author 蝉鸣
     */
    private static String consumerGroup(MqSubscription subscription, MqMode mode, int shard) {
        String suffix = shard < 0 ? "" : "_" + shard;
        return consumerGroup(subscription) + "_" + mode.name().toLowerCase() + suffix;
    }

    /**
     * 功能描述:
     * 〈生成动态消费绑定名称〉
     * @param subscription 逻辑消费者订阅信息
     * @param mode 投递模式
     * @return 动态绑定名称
     * @author 蝉鸣
     */
    private String bindingName(MqSubscription subscription, MqMode mode, int shard) {
        String suffix = shard < 0 ? "" : "." + shard;
        return type.name().toLowerCase() + "." + digest(subscription.consumerId())
                + "." + mode.name().toLowerCase() + suffix;
    }

    /**
     * 功能描述:
     * 〈生成稳定且紧凑的标识摘要〉
     * @param value 原始标识
     * @return 十六进制摘要
     * @author 蝉鸣
     */
    private static String digest(String value) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
            return java.util.HexFormat.of().formatHex(bytes, 0, 8);
        } catch (NoSuchAlgorithmException exception) {
            throw MqExceptionEnum.DIGEST_FAILED.getBaseException(exception);
        }
    }
}
