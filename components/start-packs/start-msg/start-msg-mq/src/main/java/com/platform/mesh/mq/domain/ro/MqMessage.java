package com.platform.mesh.mq.domain.ro;

import com.platform.mesh.mq.enums.MqMode;

import java.time.Instant;
import java.util.Map;

/**
 * 功能描述:
 * 〈中间件之间传递的统一 MQ 消息信封〉
 * @author 蝉鸣
 */
public record MqMessage<T>(String messageId, String topic, MqMode mode,
        String orderingKey, Instant occurredAt, String traceId,
        Map<String, String> headers, T payload) {

    /**
     * 功能描述:
     * 〈规范化统一 MQ 消息信封〉
     * @author 蝉鸣
     */
    public MqMessage {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
}
