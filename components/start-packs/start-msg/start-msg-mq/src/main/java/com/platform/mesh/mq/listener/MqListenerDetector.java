package com.platform.mesh.mq.listener;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 功能描述:
 * 〈发现并注册所有主题监听方法〉
 * @author 蝉鸣
 */
public class MqListenerDetector implements SmartInitializingSingleton {

    private final ConfigurableListableBeanFactory beanFactory;
    private final MqListenerRegistry listenerRegistry;
    private final MqSubscriptionRegistrar subscriptionRegistrar;

    /**
     * 功能描述:
     * 〈初始化 MQ 监听器发现器〉
     * @param beanFactory Spring Bean 工厂
     * @param listenerRegistry MQ 监听方法注册表
     * @param subscriptionRegistrar MQ 订阅注册器
     * @author 蝉鸣
     */
    public MqListenerDetector(ConfigurableListableBeanFactory beanFactory,
            MqListenerRegistry listenerRegistry,
            MqSubscriptionRegistrar subscriptionRegistrar) {
        this.beanFactory = beanFactory;
        this.listenerRegistry = listenerRegistry;
        this.subscriptionRegistrar = subscriptionRegistrar;
    }

    /**
     * 功能描述:
     * 〈单例初始化完成后发现监听器并启动订阅〉
     * @author 蝉鸣
     */
    @Override
    public void afterSingletonsInstantiated() {
        // 1. 遍历已创建的 Bean，发现并登记标注了 @MqListener 的方法。
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            Object bean;
            try {
                bean = beanFactory.getBean(beanName);
            } catch (RuntimeException exception) {
                continue;
            }
            listenerRegistry.registerBean(beanName, bean);
        }
        // 2. 监听器登记完成后，为所有外部提供方创建实际订阅。
        subscriptionRegistrar.start();
    }
}
