package com.platform.mesh.mq.listener;

import com.platform.mesh.mq.annotation.MqListener;
import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 〈MQ 监听方法注册表〉
 * @author 蝉鸣
 */
public class MqListenerRegistry {

    private final String applicationName;
    private final ObjectMapper objectMapper;
    private final Map<String, List<MqListenerEndpoint>> endpoints = new LinkedHashMap<>();

    /**
     * 功能描述:
     * 〈初始化 MQ 监听方法注册表〉
     * @param applicationName 应用名称
     * @author 蝉鸣
     */
    public MqListenerRegistry(String applicationName) {
        this(applicationName, new ObjectMapper());
    }

    /**
     * 功能描述:
     * 〈初始化支持业务载荷转换的 MQ 监听方法注册表〉
     * @param applicationName 应用名称
     * @param objectMapper JSON 对象映射器
     * @author 蝉鸣
     */
    public MqListenerRegistry(String applicationName, ObjectMapper objectMapper) {
        this.applicationName = applicationName == null || applicationName.isBlank()
                ? "application" : applicationName.trim();
        this.objectMapper = objectMapper;
    }

    /**
     * 功能描述:
     * 〈扫描并注册 Bean 中的 MQ 监听方法〉
     * @param beanName Bean 名称
     * @param bean Bean 实例
     * @author 蝉鸣
     */
    public void registerBean(String beanName, Object bean) {
        // 1. 获取代理对象对应的实际业务类型。
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        // 2. 查找当前类型中所有标注了 @MqListener 的方法。
        Map<Method, MqListener> methods = MethodIntrospector.selectMethods(targetClass,
                (MethodIntrospector.MetadataLookup<MqListener>) method ->
                        AnnotatedElementUtils.findMergedAnnotation(method, MqListener.class));
        // 3. 按稳定顺序注册，保证不同实例生成一致的逻辑消费者标识。
        methods.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Method::toGenericString)))
                .forEach(entry -> register(bean, targetClass, entry.getKey(), entry.getValue()));
    }

    /**
     * 功能描述:
     * 〈获取指定主题的监听端点〉
     * @param topic 业务主题
     * @return 监听端点列表
     * @author 蝉鸣
     */
    public List<MqListenerEndpoint> endpoints(String topic) {
        return List.copyOf(endpoints.getOrDefault(topic, List.of()));
    }

    /**
     * 功能描述:
     * 〈获取全部监听端点〉
     * @return 全部监听端点
     * @author 蝉鸣
     */
    public List<MqListenerEndpoint> allEndpoints() {
        return endpoints.values().stream().flatMap(List::stream).toList();
    }

    /**
     * 功能描述:
     * 〈将消息分发给同主题的全部业务监听器〉
     * @param message 统一 MQ 消息
     * @return 实际调用的监听器数量
     * @author 蝉鸣
     */
    public int dispatch(MqMessage<?> message) {
        List<MqListenerEndpoint> matched = endpoints(message.topic());
        RuntimeException failure = null;
        for (MqListenerEndpoint endpoint : matched) {
            try {
                endpoint.invoke(message);
            } catch (RuntimeException exception) {
                if (failure == null) failure = exception;
                else if (failure != exception) failure.addSuppressed(exception);
            }
        }
        // 本地广播尽力执行全部监听器，最终仍向发布方报告失败。
        if (failure != null) throw failure;
        return matched.size();
    }

    /**
     * 功能描述:
     * 〈校验并登记单个 MQ 监听方法〉
     * @param bean Bean 实例
     * @param targetClass 目标业务类型
     * @param method 监听方法
     * @param listener 监听注解
     * @author 蝉鸣
     */
    private void register(Object bean, Class<?> targetClass, Method method, MqListener listener) {
        // 1. 校验主题及监听方法参数约束。
        String topic = listener.topic() == null ? "" : listener.topic().trim();
        if (topic.isEmpty() || method.getParameterCount() != 1) {
            throw MqExceptionEnum.LISTENER_INVALID.getBaseException(List.of(method));
        }
        // 2. 由应用、类、方法、参数和主题生成跨实例稳定的消费者标识。
        String consumerId = applicationName + ":" + targetClass.getName() + "#"
                + method.getName() + "(" + method.getParameterTypes()[0].getName() + "):" + topic;
        // 3. 拒绝重复消费者，避免同一业务方法被重复订阅。
        boolean duplicate = allEndpoints().stream()
                .anyMatch(endpoint -> endpoint.consumerId().equals(consumerId));
        if (duplicate) {
            throw MqExceptionEnum.LISTENER_DUPLICATE.getBaseException(List.of(consumerId));
        }
        // 4. 按主题保存监听端点，支持一个主题扩展多个业务消费者。
        endpoints.computeIfAbsent(topic, key -> new ArrayList<>())
                .add(new MqListenerEndpoint(topic, consumerId, bean, method, objectMapper));
    }
}
