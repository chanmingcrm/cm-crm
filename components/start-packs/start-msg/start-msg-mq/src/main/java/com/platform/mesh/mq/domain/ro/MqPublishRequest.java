package com.platform.mesh.mq.domain.ro;

import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;

import java.util.Map;

/**
 * 功能描述:
 * 〈MQ 发布请求，provider 为空时使用 mesh.mq.default-provider〉
 * @author 蝉鸣
 */
public record MqPublishRequest<T>(MqProviderType provider, String topic,
        MqMode mode, String orderingKey, Map<String, String> headers, T payload) {

    /**
     * 功能描述:
     * 〈规范化 MQ 发布请求〉
     * @author 蝉鸣
     */
    public MqPublishRequest {
        headers = headers == null ? Map.of() : Map.copyOf(headers);
    }
}
