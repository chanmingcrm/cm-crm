package com.platform.mesh.mq.service;

import com.platform.mesh.mq.domain.ro.MqPublishRequest;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;

import java.util.Map;

/**
 * 功能描述:
 * 〈统一 MQ 发布器〉
 * @author 蝉鸣
 */
public interface MqPublisher {
    /**
     * 功能描述:
     * 〈发布统一 MQ 消息〉
     * @param request MQ 发布请求
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    MqPublishResult publish(MqPublishRequest<?> request);

    /**
     * 使用配置的默认提供方广播消息。
     * @param topic 业务主题
     * @param payload 业务载荷
     * @return 发布结果
     */
    default MqPublishResult broadcast(String topic, Object payload) {
        return publish(new MqPublishRequest<>(null, topic, MqMode.BROADCAST,
                null, Map.of(), payload));
    }

    /**
     * 固定使用指定提供方广播消息，不受默认配置影响。
     * @param provider 指定提供方，不能为 null
     * @param topic 业务主题
     * @param payload 业务载荷
     * @return 发布结果
     */
    default MqPublishResult broadcast(MqProviderType provider, String topic, Object payload) {
        if (provider == null) {
            throw MqExceptionEnum.PROVIDER_EMPTY.getBaseException();
        }
        return publish(new MqPublishRequest<>(provider, topic, MqMode.BROADCAST,
                null, Map.of(), payload));
    }
}
