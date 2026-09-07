package com.platform.mesh.mq.soa.rabbitmq;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能描述:
 * 〈通过持久化队列和事务提交确认 RabbitMQ 死信写入〉
 * @author 蝉鸣
 */
public final class RabbitDeadLetterPublisher {
    private RabbitDeadLetterPublisher() { }

    /**
     * 功能描述:
     * 〈保留失败元数据并等待死信提交成功，失败时不确认原消息〉
     * @param factory Binder 使用的连接工厂
     * @param queue 持久化死信队列名称
     * @param body 原始消息载荷
     * @param metadata 消费者、尝试次数和失败原因
     * @param messageId 原始消息标识
     * @author 蝉鸣
     */
    public static void publish(ConnectionFactory factory, String queue,
            byte[] body, Map<String, Object> metadata, String messageId) {
        // 默认交换机直接路由到已声明的持久队列；mandatory return 先于
        // tx.commit-ok 返回，避免队列被删除时将未路由消息误判为成功。
        try (Connection connection = factory.createConnection();
                Channel channel = connection.createChannel(true)) {
            AtomicBoolean returned = new AtomicBoolean();
            var listener = channel.addReturnListener(value -> returned.set(true));
            try {
                channel.queueDeclare(queue, true, false, false, null);
                channel.txSelect();
                var properties = new AMQP.BasicProperties.Builder()
                        .contentType(HttpConst.APPLICATION_JSON)
                        .deliveryMode(2)
                        .messageId(messageId)
                        .headers(metadata)
                        .build();
                channel.basicPublish("", queue, true, properties, body);
                channel.txCommit();
                if (returned.get()) {
                    throw MqExceptionEnum.DEAD_LETTER_PUBLISH_FAILED.getBaseException(List.of(queue));
                }
            } finally {
                // 连接工厂会缓存 Channel，必须移除本次发布的临时监听器。
                channel.removeReturnListener(listener);
            }
        } catch (Exception failure) {
            var exception = MqExceptionEnum.DEAD_LETTER_PUBLISH_FAILED.getBaseException(List.of(queue));
            exception.initCause(failure);
            throw exception;
        }
    }
}
