package com.platform.mesh.mq.properties;

import com.platform.mesh.mq.constant.MqConst;
import com.platform.mesh.mq.enums.MqProviderType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 功能描述:
 * 〈MQ 配置信息〉
 * @author 蝉鸣
 */
@ConfigurationProperties(MqConst.CONFIG_PREFIX)
@Data
public class MqProperties {

    /**
     * 是否启用 MQ 组件
     */
    private boolean enabled;

    /**
     * 未指定提供方时使用的默认值；不配置时只能显式指定提供方发布
     */
    private MqProviderType defaultProvider;

    /**
     * Spring 本地事件配置
     */
    private final Provider event = new Provider();

    /**
     * Redis 配置
     */
    private final Provider redis = new Provider();

    /**
     * RocketMQ 配置
     */
    private final Provider rocketmq = new Provider();

    /**
     * RabbitMQ 配置
     */
    private final Provider rabbitmq = new Provider();

    /**
     * Kafka 配置
     */
    private final Provider kafka = new Provider();

    /**
     * Outbox 配置
     */
    private final Provider outbox = new Provider();

    /**
     * 功能描述:
     * 〈MQ 提供方开关配置〉
     * @author 蝉鸣
     */
    @Data
    public static class Provider {

        /**
         * 是否启用当前功能
         */
        private boolean enabled;

        /**
         * 当前提供方的队列分片数
         */
        private int queueShards = 16;

        /**
         * 消费总尝试次数，包含第一次；耗尽后可靠写入死信再确认原消息
         */
        private int maxAttempts = 3;

        /**
         * 消费重试间隔（毫秒）
         */
        private long retryBackoffMillis = 1000;

    }
}
