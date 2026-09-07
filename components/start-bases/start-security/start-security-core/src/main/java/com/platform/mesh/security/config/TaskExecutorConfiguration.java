package com.platform.mesh.security.config;

import com.platform.mesh.security.context.SecurityContextThreadAccessor;
import com.platform.mesh.utils.context.ThreadContextRegistry;
import com.platform.mesh.security.properties.TaskExecutorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import static org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME;
import static org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME;

/**
 * 功能描述:
 * 〈Spring 异步任务线程池自动配置〉
 *
 * @author 蝉鸣
 */
@AutoConfiguration
@EnableConfigurationProperties(TaskExecutorProperties.class)
public class TaskExecutorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutorConfiguration.class);

    /**
     * 功能描述:
     * 〈注册 Spring Security 线程上下文传播扩展〉
     * @return 上下文扩展注销句柄
     * @author 蝉鸣
     */
    @Bean(destroyMethod = "close")
    public ThreadContextRegistry.Registration securityContextThreadRegistration() {
        return ThreadContextRegistry.register(new SecurityContextThreadAccessor());
    }

    /**
     * 功能描述:
     * 〈创建公共异步任务执行器〉
     * @param properties 线程池配置
     * @return 异步任务执行器
     * @author qingfeng
    */
    @Bean(name = { APPLICATION_TASK_EXECUTOR_BEAN_NAME, DEFAULT_TASK_EXECUTOR_BEAN_NAME })
    @ConditionalOnMissingBean(name = APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor taskExecutor(TaskExecutorProperties properties) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(properties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(properties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(properties.getQueueCapacity());
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationMillis(properties.getAwaitTermination().toMillis());
        taskExecutor.setThreadNamePrefix(properties.getThreadNamePrefix());
        taskExecutor.setTaskDecorator(new MTaskDecorator());
        return taskExecutor;
    }

    /**
     * 功能描述:
     * 〈创建异步任务配置器〉
     * @param taskExecutor 异步任务执行器
     * @return 异步任务配置器
     * @author qingfeng
    */
    @Bean
    @ConditionalOnMissingBean(AsyncConfigurer.class)
    public AsyncConfigurer asyncConfigurer(
            @Qualifier(APPLICATION_TASK_EXECUTOR_BEAN_NAME) Executor taskExecutor) {
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {
                return taskExecutor;
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return TaskExecutorConfiguration.this::handleAsyncException;
            }
        };
    }

    /**
     * 功能描述:
     * 〈记录异步任务未捕获异常〉
     * @param exception 异步任务异常
     * @param method 异步方法
     * @param parameters 方法参数
     * @author qingfeng
     */
    private void handleAsyncException(Throwable exception, Method method, Object... parameters) {
        log.error("异步任务执行失败，method={}", method.toGenericString(), exception);
    }
}
