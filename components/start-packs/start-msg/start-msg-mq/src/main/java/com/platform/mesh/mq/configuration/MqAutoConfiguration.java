package com.platform.mesh.mq.configuration;

import com.platform.mesh.mq.constant.MqConst;
import com.alibaba.cloud.stream.binder.rocketmq.integration.inbound.RocketMQInboundChannelAdapter;
import org.springframework.cloud.stream.config.ConsumerEndpointCustomizer;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.integration.core.MessageProducer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.ContainerPausingBackOffHandler;
import org.springframework.kafka.listener.ListenerContainerPauseService;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.backoff.FixedBackOff;
import com.platform.mesh.mq.enums.MqProviderType;
import com.platform.mesh.mq.exception.MqExceptionEnum;
import com.platform.mesh.mq.listener.MqListenerDetector;
import com.platform.mesh.mq.listener.MqListenerRegistry;
import com.platform.mesh.mq.listener.MqSubscriptionRegistrar;
import com.platform.mesh.mq.properties.MqProperties;
import com.platform.mesh.mq.service.impl.DefaultMqPublisher;
import com.platform.mesh.mq.service.MqProvider;
import com.platform.mesh.mq.service.impl.MqConsumerProvider;
import com.platform.mesh.mq.service.MqPublisher;
import com.platform.mesh.mq.service.manual.MqProviderRegistry;
import com.platform.mesh.mq.soa.StreamMqProvider;
import com.platform.mesh.mq.soa.event.EventMqConsumerAdapter;
import com.platform.mesh.mq.soa.event.EventMqProvider;
import com.platform.mesh.mq.soa.redis.RedisMqProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.binding.BindingService;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tools.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.MessageQueueSelector;

import java.time.Clock;
import java.util.List;
import java.util.Locale;

/**
 * 功能描述:
 * 〈MQ 组件自动配置〉
 * @author 蝉鸣
 */
@AutoConfiguration
@EnableConfigurationProperties(MqProperties.class)
@ConditionalOnProperty(prefix = MqConst.CONFIG_PREFIX, name = MqConst.ENABLED,
        havingValue = MqConst.ENABLED_VALUE)
public class MqAutoConfiguration {

    /**
     * 功能描述:
     * 〈创建 RocketMQ 逻辑分片固定物理队列选择器〉
     * @return RocketMQ 消息队列选择器
     * @author 蝉鸣
     */
    @Bean(MqConst.ROCKETMQ_QUEUE_SELECTOR_BEAN)
    @ConditionalOnMissingBean(name = MqConst.ROCKETMQ_QUEUE_SELECTOR_BEAN)
    public MessageQueueSelector rocketMqQueueSelector() {
        return (queues, message, argument) -> queues.getFirst();
    }

    /**
     * 功能描述:
     * 〈创建 MQ 监听方法注册表〉
     * @param environment Spring 环境配置
     * @return MQ 监听方法注册表
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnMissingBean
    public MqListenerRegistry mqListenerRegistry(Environment environment,
            ObjectProvider<ObjectMapper> objectMapperProvider) {
        String applicationName = environment.getProperty(
                MqConst.APPLICATION_NAME, MqConst.DEFAULT_APPLICATION_NAME);
        return new MqListenerRegistry(applicationName,
                objectMapperProvider.getIfAvailable(ObjectMapper::new));
    }

    /**
     * 功能描述:
     * 〈创建 MQ 监听器发现器〉
     * @param beanFactory Spring Bean 工厂
     * @param listenerRegistry MQ 监听方法注册表
     * @param subscriptionRegistrar MQ 订阅注册器
     * @return MQ 监听器发现器
     * @author 蝉鸣
     */
    @Bean
    public MqListenerDetector mqListenerDetector(ConfigurableListableBeanFactory beanFactory,
            MqListenerRegistry listenerRegistry,
            MqSubscriptionRegistrar subscriptionRegistrar) {
        return new MqListenerDetector(beanFactory, listenerRegistry, subscriptionRegistrar);
    }

    /**
     * 功能描述:
     * 〈创建 MQ 订阅注册器〉
     * @param registry MQ 监听方法注册表
     * @param providers 已启用的消费提供方
     * @return MQ 订阅注册器
     * @author 蝉鸣
     */
    @Bean(destroyMethod = "close")
    public MqSubscriptionRegistrar mqSubscriptionRegistrar(MqListenerRegistry registry,
            List<MqConsumerProvider> providers) {
        return new MqSubscriptionRegistrar(registry, providers);
    }

    /**
     * 功能描述:
     * 〈创建 Spring 本地事件提供方〉
     * @param publisher Spring 事件发布器
     * @return Spring 本地事件提供方
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnProperty(prefix = MqConst.EVENT_CONFIG_PREFIX, name = MqConst.ENABLED,
            havingValue = MqConst.ENABLED_VALUE)
    public EventMqProvider eventMqProvider(ApplicationEventPublisher publisher) {
        return new EventMqProvider(publisher);
    }

    /**
     * 功能描述:
     * 〈创建 Spring 本地事件消费适配器〉
     * @param registry MQ 监听方法注册表
     * @return Spring 本地事件消费适配器
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnBean(EventMqProvider.class)
    public EventMqConsumerAdapter eventMqConsumerAdapter(MqListenerRegistry registry) {
        return new EventMqConsumerAdapter(registry);
    }

    /**
     * 功能描述:
     * 〈创建 Redis MQ 提供方〉
     * @param client Redisson 客户端
     * @param properties MQ 配置
     * @return Redis MQ 提供方
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnClass(RedissonClient.class)
    @ConditionalOnBean(RedissonClient.class)
    @ConditionalOnProperty(prefix = MqConst.REDIS_CONFIG_PREFIX, name = MqConst.ENABLED,
            havingValue = MqConst.ENABLED_VALUE)
    public RedisMqProvider redisMqProvider(RedissonClient client, MqProperties properties) {
        return new RedisMqProvider(client, properties.getRedis().getQueueShards(),
                properties.getRedis().getMaxAttempts(), properties.getRedis().getRetryBackoffMillis());
    }

    /**
     * 功能描述:
     * 〈创建 Kafka MQ 提供方〉
     * @param bridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @return Kafka MQ 提供方
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnClass(StreamBridge.class)
    @ConditionalOnProperty(prefix = MqConst.KAFKA_CONFIG_PREFIX, name = MqConst.ENABLED,
            havingValue = MqConst.ENABLED_VALUE)
    public StreamMqProvider kafkaMqProvider(StreamBridge bridge, BindingService bindingService,
            ObjectProvider<ObjectMapper> objectMapperProvider, MqProperties properties) {
        return new StreamMqProvider(MqProviderType.KAFKA, bridge, bindingService,
                objectMapperProvider.getIfAvailable(ObjectMapper::new),
                properties.getKafka().getQueueShards(),
                properties.getKafka().getMaxAttempts(), properties.getKafka().getRetryBackoffMillis());
    }

    /**
     * 功能描述:
     * 〈创建 RabbitMQ 提供方〉
     * @param bridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @return RabbitMQ 提供方
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnClass(StreamBridge.class)
    @ConditionalOnProperty(prefix = MqConst.RABBITMQ_CONFIG_PREFIX, name = MqConst.ENABLED,
            havingValue = MqConst.ENABLED_VALUE)
    public StreamMqProvider rabbitMqProvider(StreamBridge bridge, BindingService bindingService,
            ObjectProvider<ObjectMapper> objectMapperProvider, MqProperties properties) {
        return new StreamMqProvider(MqProviderType.RABBITMQ, bridge, bindingService,
                objectMapperProvider.getIfAvailable(ObjectMapper::new),
                properties.getRabbitmq().getQueueShards(),
                properties.getRabbitmq().getMaxAttempts(), properties.getRabbitmq().getRetryBackoffMillis());
    }

    /**
     * 功能描述:
     * 〈创建 RocketMQ 提供方〉
     * @param bridge Spring Cloud Stream 发布桥接器
     * @param bindingService Spring Cloud Stream 绑定服务
     * @return RocketMQ 提供方
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnClass(StreamBridge.class)
    @ConditionalOnProperty(prefix = MqConst.ROCKETMQ_CONFIG_PREFIX, name = MqConst.ENABLED,
            havingValue = MqConst.ENABLED_VALUE)
    public StreamMqProvider rocketMqProvider(StreamBridge bridge, BindingService bindingService,
            ObjectProvider<ObjectMapper> objectMapperProvider, MqProperties properties) {
        return new StreamMqProvider(MqProviderType.ROCKETMQ, bridge, bindingService,
                objectMapperProvider.getIfAvailable(ObjectMapper::new),
                properties.getRocketmq().getQueueShards(),
                properties.getRocketmq().getMaxAttempts(), properties.getRocketmq().getRetryBackoffMillis());
    }

    /**
     * 保持 RocketMQ 失败向 Broker 传播；当前 Binder 的内部 RetryTemplate 会吞异常。
     * 业务重试和死信由 StreamMqProvider 统一执行。
     */
    @Bean
    public ConsumerEndpointCustomizer<MessageProducer> mqConsumerEndpointCustomizer() {
        return (endpoint, destination, group) -> {
            if (destination.startsWith("mesh_") && endpoint instanceof RocketMQInboundChannelAdapter adapter) {
                adapter.setRetryTemplate(null);
                adapter.setErrorChannel(null);
            } else if (destination.startsWith("mesh_")
                    && endpoint instanceof KafkaMessageDrivenChannelAdapter<?, ?> adapter) {
                adapter.setRetryTemplate(null);
                adapter.setErrorChannel(null);
            }
        };
    }

    /**
     * 功能描述:
     * 〈创建 Kafka 暂停重试调度器，随 Spring 上下文关闭释放〉
     * @author 蝉鸣
     */
    @Bean
    public ThreadPoolTaskScheduler mqKafkaRetryScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("mq-kafka-retry-");
        scheduler.setRemoveOnCancelPolicy(true);
        return scheduler;
    }

    /**
     * 关闭或死信发送失败时不跳过 Kafka offset，交给后续消费者继续处理。
     */
    @Bean
    public ListenerContainerCustomizer<AbstractMessageListenerContainer<?, ?>> mqKafkaContainerCustomizer(
            MqProperties properties,
            @Qualifier("mqKafkaRetryScheduler") ThreadPoolTaskScheduler scheduler) {
        return (container, destination, group) -> {
            if (destination.startsWith("mesh_")) {
                var pauseService = new ListenerContainerPauseService(null, scheduler);
                DefaultErrorHandler handler = new DefaultErrorHandler(null, new FixedBackOff(
                        Math.max(1, properties.getKafka().getRetryBackoffMillis()), FixedBackOff.UNLIMITED_ATTEMPTS),
                        new ContainerPausingBackOffHandler(pauseService));
                handler.setSeekAfterError(false);
                handler.setAckAfterHandle(false);
                handler.setCommitRecovered(false);
                handler.setClassifications(java.util.Map.of(), true);
                container.setCommonErrorHandler(handler);
            }
        };
    }

    /**
     * 功能描述:
     * 〈创建已启用 MQ 提供方注册表〉
     * @param providers 已启用的 MQ 提供方
     * @return MQ 提供方注册表
     * @author 蝉鸣
     */
    @Bean
    public MqProviderRegistry mqProviderRegistry(List<MqProvider> providers, MqProperties properties) {
        MqProviderRegistry registry = new MqProviderRegistry(providers);
        MqProviderType defaultProvider = properties.getDefaultProvider();
        if (defaultProvider != null) {
            MqProperties.Provider config = switch (defaultProvider) {
                case EVENT -> properties.getEvent();
                case REDIS -> properties.getRedis();
                case KAFKA -> properties.getKafka();
                case RABBITMQ -> properties.getRabbitmq();
                case ROCKETMQ -> properties.getRocketmq();
            };
            String name = defaultProvider.name().toLowerCase(Locale.ROOT);
            if (!config.isEnabled()) {
                var error = MqExceptionEnum.DEFAULT_PROVIDER_DISABLED;
                throw error.getBaseException(error.getModule(), error.getCode(),
                        error.getDesc().formatted(name, name));
            }
            if (providers.stream().noneMatch(provider -> provider.type() == defaultProvider)) {
                var error = MqExceptionEnum.DEFAULT_PROVIDER_UNAVAILABLE;
                throw error.getBaseException(error.getModule(), error.getCode(),
                        error.getDesc().formatted(name));
            }
        }
        return registry;
    }

    /**
     * 功能描述:
     * 〈创建统一 MQ 发布器〉
     * @param registry MQ 提供方注册表
     * @return 统一 MQ 发布器
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnMissingBean
    public MqPublisher mqPublisher(MqProviderRegistry registry, MqProperties properties) {
        return new DefaultMqPublisher(registry, Clock.systemUTC(), properties.getDefaultProvider());
    }
}
