package com.platform.mesh.mq.listener;

import com.platform.mesh.mq.domain.ro.MqSubscription;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mq.service.impl.MqConsumerProvider;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 * 〈将业务监听方法注册到所有已启用外部提供方〉
 * @author 蝉鸣
 */
public class MqSubscriptionRegistrar implements AutoCloseable,
        ApplicationListener<ContextClosedEvent> {

    private final MqListenerRegistry listenerRegistry;
    private final List<MqConsumerProvider> providers;
    private final List<AutoCloseable> subscriptions = new ArrayList<>();
    private boolean started;

    /**
     * 功能描述:
     * 〈初始化 MQ 订阅注册器〉
     * @param listenerRegistry MQ 监听方法注册表
     * @param providers 已启用的消费提供方
     * @author 蝉鸣
     */
    public MqSubscriptionRegistrar(MqListenerRegistry listenerRegistry,
            List<MqConsumerProvider> providers) {
        this.listenerRegistry = listenerRegistry;
        this.providers = List.copyOf(providers);
    }

    /**
     * 功能描述:
     * 〈为全部监听端点启动跨中间件订阅〉
     * @author 蝉鸣
     */
    public synchronized void start() {
        if (started) return;
        try {
            for (MqConsumerProvider provider : providers) {
                for (MqListenerEndpoint endpoint : listenerRegistry.allEndpoints()) {
                    MqSubscription subscription = new MqSubscription(
                            endpoint.topic(), endpoint.consumerId());
                    subscriptions.add(provider.subscribe(subscription, endpoint::invoke));
                }
            }
            started = true;
        } catch (RuntimeException exception) {
            // 后续订阅失败时释放前面成功的订阅，避免启动失败后遗留消费者。
            try {
                close();
            } catch (RuntimeException closeFailure) {
                if (exception != closeFailure) exception.addSuppressed(closeFailure);
            }
            throw exception;
        }
    }

    /**
     * 功能描述:
     * 〈关闭全部中间件订阅并汇总关闭异常〉
     * @author 蝉鸣
     */
    @Override
    public synchronized void close() {
        BaseException failure = null;
        for (AutoCloseable subscription : subscriptions.reversed()) {
            try {
                subscription.close();
            } catch (Exception exception) {
                if (failure == null) {
                    failure = MqExceptionEnum.SUBSCRIPTION_CLOSE_FAILED.getBaseException(exception);
                }
                else failure.addSuppressed(exception);
            }
        }
        subscriptions.clear();
        started = false;
        if (failure != null) throw failure;
    }

    /**
     * 功能描述:
     * 〈在 Spring Bean 销毁前主动解除全部 MQ 消费绑定〉
     * @param event Spring 上下文关闭事件
     * @author 蝉鸣
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // 上下文关闭事件早于单例销毁，避免解绑过程再次请求已销毁的 Binder Bean。
        close();
    }
}
