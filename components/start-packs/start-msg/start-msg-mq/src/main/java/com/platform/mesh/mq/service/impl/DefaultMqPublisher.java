package com.platform.mesh.mq.service.impl;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqPublishRequest;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.service.MqProvider;
import com.platform.mesh.mq.service.MqPublisher;
import com.platform.mesh.mq.service.manual.MqProviderRegistry;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

/**
 * 功能描述:
 * 〈默认统一 MQ 发布器〉
 * @author 蝉鸣
 */
public class DefaultMqPublisher implements MqPublisher {

    private final MqProviderRegistry providerRegistry;
    private final Clock clock;
    private final MqProviderType defaultProvider;

    /**
     * 功能描述:
     * 〈初始化统一 MQ 发布器〉
     * @param providerRegistry MQ 提供方注册表
     * @param clock 系统时钟
     * @author 蝉鸣
     */
    public DefaultMqPublisher(MqProviderRegistry providerRegistry, Clock clock) {
        this(providerRegistry, clock, null);
    }

    /**
     * 功能描述:
     * 〈初始化支持配置默认提供方的发布器〉
     * @param providerRegistry MQ 提供方注册表
     * @param clock 系统时钟
     * @param defaultProvider 默认提供方，可为空
     * @author 蝉鸣
     */
    public DefaultMqPublisher(MqProviderRegistry providerRegistry, Clock clock,
            MqProviderType defaultProvider) {
        this.providerRegistry = providerRegistry;
        this.clock = clock;
        this.defaultProvider = defaultProvider;
    }

    /**
     * 功能描述:
     * 〈校验并发布统一 MQ 消息〉
     * @param request MQ 发布请求
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    @Override
    public MqPublishResult publish(MqPublishRequest<?> request) {
        // 1. 校验发布参数，队列模式必须提供顺序键。
        validate(request);
        // 2. 获取指定提供方，并确认提供方支持当前投递模式。
        MqProviderType providerType = request.provider() == null
                ? defaultProvider : request.provider();
        if (providerType == null) {
            throw MqExceptionEnum.DEFAULT_PROVIDER_EMPTY.getBaseException();
        }
        MqProvider provider = providerRegistry.requiredMqProvider(providerType);
        if (!provider.supports(request.mode())) {
            throw MqExceptionEnum.MODE_UNSUPPORTED.getBaseException(List.of(
                    providerType + " " + request.mode()));
        }
        // 3. 生成消息标识并构建与中间件无关的统一消息信封。
        String messageId = UUID.randomUUID().toString();
        MqMessage<?> message = new MqMessage<>(messageId, request.topic().trim(), request.mode(),
                normalize(request.orderingKey()), clock.instant(), request.headers().get("traceId"),
                request.headers(), request.payload());
        // 4. 委托提供方发送消息，并统一封装提供方回执。
        MqPublishResult result = provider.publish(message);
        return new MqPublishResult(messageId, result == null ? null : result.providerReceipt());
    }

    /**
     * 功能描述:
     * 〈校验 MQ 发布请求〉
     * @param request MQ 发布请求
     * @author 蝉鸣
     */
    private void validate(MqPublishRequest<?> request) {
        if (request == null) throw MqExceptionEnum.PUBLISH_REQUEST_EMPTY.getBaseException();
        if (request.topic() == null || request.topic().isBlank()) {
            throw MqExceptionEnum.TOPIC_EMPTY.getBaseException();
        }
        if (request.mode() == null) throw MqExceptionEnum.MODE_EMPTY.getBaseException();
        if (request.payload() == null) throw MqExceptionEnum.PAYLOAD_EMPTY.getBaseException();
        if (request.mode() == MqMode.QUEUE
                && (request.orderingKey() == null || request.orderingKey().isBlank())) {
            throw MqExceptionEnum.ORDERING_KEY_EMPTY.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈规范化可选字符串参数〉
     * @param value 原始字符串
     * @return 规范化后的字符串
     * @author 蝉鸣
     */
    private String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
