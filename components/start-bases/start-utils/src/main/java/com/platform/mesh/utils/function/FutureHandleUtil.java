package com.platform.mesh.utils.function;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.utils.context.ThreadContextSnapshot;
import com.platform.mesh.utils.thread.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
public class FutureHandleUtil {

    private final static Logger log = LoggerFactory.getLogger(FutureHandleUtil.class);

    /**
     * 功能描述:
     * 〈执行带返回值的Future〉
     * @param taskList taskList
     * @param futureWithResultFunction flowWithResultConsumer
     * @return 正常返回:{@link List<T>}
     * @author 蝉鸣
     */
    public static <T,R> List<R> runWithResult(List<T> taskList, FutureWithResultFunction<T,R> futureWithResultFunction){
        //收集返回值
        //定长5线程池
        return runWithResult(taskList, ThreadPoolUtil.getInstance(), futureWithResultFunction);
    }

    /**
     * 功能描述:
     * 〈执行带返回值的Future〉
     * @param taskList taskList
     * @param executorService executorService
     * @param futureWithResultFunction flowWithResultConsumer
     * @return 正常返回:{@link List<T>}
     * @author 蝉鸣
     */
    public static <T,R> List<R> runWithResult(List<T> taskList,ExecutorService executorService, FutureWithResultFunction<T,R> futureWithResultFunction){
        if (CollUtil.isEmpty(taskList)) {
            return CollUtil.newArrayList();
        }
        //捕获当前线程上下文
        ThreadContextSnapshot contextSnapshot = ThreadContextSnapshot.capture();
        //全流式处理转换成CompletableFuture[]
        return taskList
                .stream()
                .filter(ObjectUtil::isNotNull)
                .map(
                        task -> CompletableFuture
                                //异步执行无入参无返回值，自定义线程池
                                .supplyAsync(contextSnapshot.wrap(() ->
                                        futureWithResultFunction.handle(task)), executorService)
                )
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param taskList taskList
     * @param futureNoResultConsumer flowNoResultConsumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(List<T> taskList, FutureNoResultConsumer<T> futureNoResultConsumer){
        //执行条件
        runNoResult(taskList, ThreadPoolUtil.getInstance(), futureNoResultConsumer);
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param taskList taskList
     * @param executorService executorService
     * @param futureNoResultConsumer flowNoResultConsumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(List<T> taskList,ExecutorService executorService, FutureNoResultConsumer<T> futureNoResultConsumer){
        if (CollUtil.isEmpty(taskList)) {
            return;
        }
        //捕获当前线程上下文
        ThreadContextSnapshot contextSnapshot = ThreadContextSnapshot.capture();
        //join等待执行完毕。无返回值。
        CompletableFuture
                .runAsync(contextSnapshot.wrap(() ->
                        futureNoResultConsumer.handle(taskList)),executorService)
                .join();
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param taskList taskList
     * @param consumer consumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(List<T> taskList, Consumer<T> consumer){
        //捕获当前线程上下文
        ThreadContextSnapshot contextSnapshot = ThreadContextSnapshot.capture();
        //join等待执行完毕。无返回值。
        CompletableFuture.allOf(
                taskList.stream()
                        .map(task -> CompletableFuture.runAsync(
                                contextSnapshot.wrap(() -> consumer.accept(task)),
                                ThreadPoolUtil.getInstance()))
                        .toArray(CompletableFuture[]::new)
        ).join();
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param task task
     * @param consumer consumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(T task, Consumer<T> consumer){
        //捕获当前线程上下文
        ThreadContextSnapshot contextSnapshot = ThreadContextSnapshot.capture();
        //join等待执行完毕。无返回值。
        CompletableFuture
                .runAsync(contextSnapshot.wrap(() -> consumer.accept(task)),
                        ThreadPoolUtil.getInstance())
                .join();
    }

}

