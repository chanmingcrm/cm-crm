package com.platform.mesh.security.config;

import com.platform.mesh.core.constants.StrConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description Spring异步任务线程池配置
 * @author 蝉鸣
 */
@AutoConfiguration
@ConditionalOnMissingBean(AsyncConfigurer.class)
public class TaskExecutorConfiguration implements AsyncConfigurer {

	private final static Logger log = LoggerFactory.getLogger(TaskExecutorConfiguration.class);

	/**
	 * 获取当前机器的核数, 不一定准确 请根据实际场景 CPU密集 || IO 密集
	 */
	public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

	@Value("${thread.pool.corePoolSize:}")
	private Optional<Integer> corePoolSize;

	@Value("${thread.pool.maxPoolSize:}")
	private Optional<Integer> maxPoolSize;

	@Value("${thread.pool.queueCapacity:}")
	private Optional<Integer> queueCapacity;

	@Value("${thread.pool.awaitTerminationSeconds:}")
	private Optional<Integer> awaitTerminationSeconds;

	/**
	 * 功能描述:
	 * 〈异步线程配置〉
	 * @return 正常返回:{@link Executor}
	 * @author 蝉鸣
	 */
	@Bean
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 核心线程大小 默认区 CPU 数量
		taskExecutor.setCorePoolSize(corePoolSize.orElse(CPU_NUM));
		// 最大线程大小 默认区 CPU * 2 数量
		taskExecutor.setMaxPoolSize(maxPoolSize.orElse(CPU_NUM * 2));
		// 队列最大容量
		taskExecutor.setQueueCapacity(queueCapacity.orElse(500));
		// 设置拒绝策略
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 等待所有任务结束后再关闭线程池
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		// 等待时间
		taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds.orElse(60));
		// 默认线程池前缀
		taskExecutor.setThreadNamePrefix(StrConst.THREAD_PREFIX);
		// 上下文传递方法
		taskExecutor.setTaskDecorator(new MTaskDecorator());
		// 初始化
		taskExecutor.initialize();
		return taskExecutor;
	}

	/**
	 * 功能描述:
	 * 〈异步线程异常处理〉
	 * @return 正常返回:{@link AsyncUncaughtExceptionHandler}
	 * @author 蝉鸣
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			// 异步任务异常处理逻辑
			log.error(ex.getLocalizedMessage());
		};
	}
}