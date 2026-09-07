package com.platform.mesh.mq.listener;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.exception.MqExceptionEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import tools.jackson.databind.ObjectMapper;

/**
 * 功能描述:
 * 〈已注册的 MQ 监听方法〉
 * @author 蝉鸣
 */
public record MqListenerEndpoint(String topic, String consumerId, Object bean, Method method,
        ObjectMapper objectMapper) {

    /**
     * 功能描述:
     * 〈获取监听方法名称〉
     * @return 监听方法名称
     * @author 蝉鸣
     */
    public String methodName() {
        return method.getName();
    }

    /**
     * 功能描述:
     * 〈调用监听方法消费统一消息或业务载荷〉
     * @param message 统一 MQ 消息
     * @author 蝉鸣
     */
    public void invoke(MqMessage<?> message) {
        // 1. 根据监听方法参数类型选择传入完整消息或仅传入业务载荷。
        Type parameterType = method.getGenericParameterTypes()[0];
        Object argument = MqMessage.class.isAssignableFrom(method.getParameterTypes()[0])
                ? convertMessage(message, parameterType)
                : objectMapper.convertValue(message.payload(), objectMapper.constructType(parameterType));
        // 2. 反射调用业务方法，并保留业务运行时异常的原始语义。
        try {
            method.setAccessible(true);
            method.invoke(bean, argument);
        } catch (IllegalAccessException exception) {
            throw MqExceptionEnum.LISTENER_ACCESS_FAILED.getBaseException(exception);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof RuntimeException runtimeException) {
                throw runtimeException;
            }
            throw MqExceptionEnum.LISTENER_EXECUTE_FAILED.getBaseException(cause);
        }
    }

    /**
     * 功能描述:
     * 〈按监听方法声明的泛型转换统一消息业务载荷〉
     * @param message 统一 MQ 消息
     * @param parameterType 监听方法参数类型
     * @return 已完成业务载荷转换的统一消息
     * @author 蝉鸣
     */
    private MqMessage<?> convertMessage(MqMessage<?> message, Type parameterType) {
        if (!(parameterType instanceof ParameterizedType parameterizedType)) {
            return message;
        }
        Type payloadType = parameterizedType.getActualTypeArguments()[0];
        Object payload = objectMapper.convertValue(message.payload(),
                objectMapper.constructType(payloadType));
        return new MqMessage<>(message.messageId(), message.topic(), message.mode(),
                message.orderingKey(), message.occurredAt(), message.traceId(),
                message.headers(), payload);
    }
}
