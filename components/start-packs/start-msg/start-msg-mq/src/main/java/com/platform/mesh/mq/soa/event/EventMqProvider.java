package com.platform.mesh.mq.soa.event;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.service.MqProvider;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Set;
import java.util.List;

/**
 * 功能描述:
 * 〈Spring 进程内事件提供方〉
 * @author 蝉鸣
 */
public class EventMqProvider implements MqProvider {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 功能描述:
     * 〈初始化 Spring 本地事件提供方〉
     * @param eventPublisher Spring 事件发布器
     * @author 蝉鸣
     */
    public EventMqProvider(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * 功能描述:
     * 〈获取本地事件提供方类型〉
     * @return 本地事件提供方类型
     * @author 蝉鸣
     */
    @Override
    public MqProviderType type() {
        return MqProviderType.EVENT;
    }

    /**
     * 功能描述:
     * 〈获取本地事件支持的投递模式〉
     * @return 广播投递模式
     * @author 蝉鸣
     */
    @Override
    public Set<MqMode> supportedModes() {
        return Set.of(MqMode.BROADCAST);
    }

    /**
     * 功能描述:
     * 〈发布 Spring 进程内广播消息〉
     * @param message 统一 MQ 消息
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    @Override
    public MqPublishResult publish(MqMessage<?> message) {
        if (!supports(message.mode())) {
            throw MqExceptionEnum.MODE_UNSUPPORTED.getBaseException(
                    List.of(MqProviderType.EVENT + " " + message.mode()));
        }
        eventPublisher.publishEvent(new LocalMqEvent(message));
        return new MqPublishResult(message.messageId(), "local");
    }
}
