package com.platform.mesh.mq.service.impl;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqSubscription;
import com.platform.mesh.mq.service.MqProvider;

import java.util.function.Consumer;

/**
 * 功能描述:
 * 〈具备消费订阅能力的 MQ 提供方〉
 * @author 蝉鸣
 */
public interface MqConsumerProvider extends MqProvider {
    /**
     * 功能描述:
     * 〈订阅业务主题并注册消息消费者〉
     * @param subscription 逻辑消费者订阅信息
     * @param consumer 消息消费函数
     * @return 可关闭的订阅句柄
     * @author 蝉鸣
     */
    AutoCloseable subscribe(MqSubscription subscription, Consumer<MqMessage<?>> consumer);
}
