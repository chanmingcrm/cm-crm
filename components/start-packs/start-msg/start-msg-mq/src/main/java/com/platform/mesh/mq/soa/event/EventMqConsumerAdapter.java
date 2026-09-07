package com.platform.mesh.mq.soa.event;

import com.platform.mesh.mq.listener.MqListenerRegistry;
import org.springframework.context.event.EventListener;

/**
 * 功能描述:
 * 〈将 Spring 本地事件交给主题监听器〉
 * @author 蝉鸣
 */
public class EventMqConsumerAdapter {

    private final MqListenerRegistry listenerRegistry;

    /**
     * 功能描述:
     * 〈初始化本地事件消费适配器〉
     * @param listenerRegistry MQ 监听方法注册表
     * @author 蝉鸣
     */
    public EventMqConsumerAdapter(MqListenerRegistry listenerRegistry) {
        this.listenerRegistry = listenerRegistry;
    }

    /**
     * 功能描述:
     * 〈消费并分发 Spring 本地 MQ 事件〉
     * @param event Spring 本地 MQ 事件
     * @author 蝉鸣
     */
    @EventListener
    public void onEvent(LocalMqEvent event) {
        listenerRegistry.dispatch(event.message());
    }
}
