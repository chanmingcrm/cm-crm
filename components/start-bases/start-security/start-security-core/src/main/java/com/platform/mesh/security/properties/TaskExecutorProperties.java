package com.platform.mesh.security.properties;

import com.platform.mesh.core.constants.StrConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 功能描述:
 * 〈公共异步任务线程池配置〉
 *
 * @author qingfeng
 */
@Data
@ConfigurationProperties(prefix = "mesh.executor")
public class TaskExecutorProperties {

    /** 核心线程数 */
    private int corePoolSize = Runtime.getRuntime().availableProcessors();

    /** 最大线程数 */
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /** 等待队列容量 */
    private int queueCapacity = 500;

    /** 应用关闭时等待任务完成的最长时间 */
    private Duration awaitTermination = Duration.ofSeconds(60);

    /** 工作线程名称前缀 */
    private String threadNamePrefix = StrConst.THREAD_PREFIX;
}
