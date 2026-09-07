package com.platform.mesh.utils.thread;

import com.platform.mesh.core.constants.NumberConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @description 全局线程池工具类,提供统一的线程池管理，避免频繁创建线程
 * @author 蝉鸣
 */
public class ThreadPoolUtil {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);

    // 核心线程池配置
    // 核心线程数
    private static final int CORE_POOL_SIZE = 20;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 200;
    // 队列容量
    private static final int QUEUE_CAPACITY = 2000;
    // 线程名前缀
    private static final String THREAD_NAME_PREFIX = "mesh-biz-thread-";

    // 单例线程池
    private static volatile ExecutorService executorService;

    /**
     * 获取线程池实例（双重检查锁）
     */
    public static ExecutorService getInstance() {
        if (executorService == null) {
            synchronized (ThreadPoolUtil.class) {
                if (executorService == null) {
                    executorService = createThreadPool();
                }
            }
        }
        return executorService;
    }

    /**
     * 创建自定义线程池
     */
    private static ExecutorService createThreadPool() {
        log.info("初始化全局线程池: core={}, max={}, queue={}",
                CORE_POOL_SIZE, MAX_POOL_SIZE, QUEUE_CAPACITY);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                NumberConst.NUM_1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 允许核心线程也超时回收
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    /**
     * 自定义线程工厂
     */
    private static class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(THREAD_NAME_PREFIX + thread.threadId());
            // 非守护线程
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            // 设置未捕获异常处理器
            thread.setUncaughtExceptionHandler((t, e) ->
                    log.error("线程 {} 执行异常: {}", t.getName(), e.getMessage(), e)
            );
            return thread;
        }
    }

    /**
     * 优雅关闭线程池
     */
    public static void shutdown() {
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        log.info("开始关闭线程池...");
        executorService.shutdown();  // 不再接受新任务
        try {
            // 等待现有任务完成（最多30秒）
            if (!executorService.awaitTermination(NumberConst.NUM_31, TimeUnit.SECONDS)) {
                log.warn("线程池未在30秒内关闭，尝试强制关闭");
                executorService.shutdownNow();  // 强制关闭
                // 再次等待
                if (!executorService.awaitTermination(NumberConst.NUM_10, TimeUnit.SECONDS)) {
                    log.error("线程池强制关闭失败");
                }
            }
        } catch (InterruptedException e) {
            log.error("线程池关闭被中断", e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("线程池关闭完成");
    }

    /**
     * 获取线程池状态
     */
    public static String getStatus() {
        if (executorService == null || executorService.isShutdown()) {
            return "线程池未初始化或已关闭";
        }
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executorService;
        return String.format(
                "活跃线程: %d, 核心线程: %d, 最大线程: %d, 队列大小: %d, 已完成任务: %d",
                pool.getActiveCount(),
                pool.getCorePoolSize(),
                pool.getMaximumPoolSize(),
                pool.getQueue().size(),
                pool.getCompletedTaskCount()
        );
    }

    /**
     * 提交任务（便捷方法）
     */
    public static Future<?> submit(Runnable task) {
        return getInstance().submit(task);
    }

    /**
     * 提交带返回值的任务
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return getInstance().submit(task);
    }

}
