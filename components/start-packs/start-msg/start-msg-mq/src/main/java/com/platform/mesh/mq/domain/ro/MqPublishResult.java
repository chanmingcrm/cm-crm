package com.platform.mesh.mq.domain.ro;

/**
 * 功能描述:
 * 〈MQ 发布结果〉
 * @author 蝉鸣
 */
public record MqPublishResult(String messageId, String providerReceipt) {
}
