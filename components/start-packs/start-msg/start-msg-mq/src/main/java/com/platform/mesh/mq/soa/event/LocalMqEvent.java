package com.platform.mesh.mq.soa.event;

import com.platform.mesh.mq.domain.ro.MqMessage;

/**
 * 功能描述:
 * 〈Spring 本地 MQ 事件〉
 * @author 蝉鸣
 */
public record LocalMqEvent(MqMessage<?> message) {
}
