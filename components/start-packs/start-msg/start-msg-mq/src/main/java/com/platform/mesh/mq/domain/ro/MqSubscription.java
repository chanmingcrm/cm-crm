package com.platform.mesh.mq.domain.ro;

/**
 * 功能描述:
 * 〈逻辑消费者对业务主题的订阅〉
 * @author 蝉鸣
 */
public record MqSubscription(String topic, String consumerId) {
}
