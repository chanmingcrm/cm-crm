package com.platform.mesh.mq.constant;

/**
 * 功能描述:
 * 〈MQ 公共常量〉
 * @author 蝉鸣
 */
public interface MqConst {

    /**
     * MQ 配置前缀
     */
    String CONFIG_PREFIX = "mesh.mq";

    /**
     * Spring 本地事件配置前缀
     */
    String EVENT_CONFIG_PREFIX = CONFIG_PREFIX + ".event";

    /**
     * Redis 配置前缀
     */
    String REDIS_CONFIG_PREFIX = CONFIG_PREFIX + ".redis";

    /**
     * Kafka 配置前缀
     */
    String KAFKA_CONFIG_PREFIX = CONFIG_PREFIX + ".kafka";

    /**
     * RabbitMQ 配置前缀
     */
    String RABBITMQ_CONFIG_PREFIX = CONFIG_PREFIX + ".rabbitmq";

    /**
     * RocketMQ 配置前缀
     */
    String ROCKETMQ_CONFIG_PREFIX = CONFIG_PREFIX + ".rocketmq";

    /**
     * 启用配置名称
     */
    String ENABLED = "enabled";

    /**
     * 启用配置值
     */
    String ENABLED_VALUE = "true";

    /**
     * Spring Boot 应用名称配置键
     */
    String APPLICATION_NAME = "spring.application.name";

    /**
     * 默认应用名称
     */
    String DEFAULT_APPLICATION_NAME = "application";

    /**
     * RocketMQ 固定物理队列选择器 Bean 名称
     */
    String ROCKETMQ_QUEUE_SELECTOR_BEAN = "meshMqQueueSelector";

}
