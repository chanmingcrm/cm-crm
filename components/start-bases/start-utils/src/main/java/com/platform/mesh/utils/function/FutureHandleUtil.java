package com.platform.mesh.utils.function;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.NumberConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
public class FutureHandleUtil {

    private final static Logger log = LoggerFactory.getLogger(FutureHandleUtil.class);

    /**
     * 功能描述:
     * 〈初始线程池〉
     * @param num 线程数量
     * @return 正常返回:{@link ExecutorService}
     * @author 蝉鸣
     */

    public static ExecutorService initExecutorService(Integer num){
        //初始化线程池
        return Executors.newFixedThreadPool(num);
    }

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
        ExecutorService executorService = initExecutorService(NumberConst.NUM_5);
        return runWithResult(taskList,executorService, futureWithResultFunction);
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
        // 获取当前线程请求头信息(解决丢失请求头问题)
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        //收集返回值
        List<R> resultList = CollUtil.newArrayList();
        //初始化线程池
        //全流式处理转换成CompletableFuture[]
        CompletableFuture.allOf(
                //组装成一个无返回值CompletableFuture
                taskList
                    .stream()
                    .map(
                        task -> CompletableFuture
                                //异步执行无入参无返回值，自定义线程池
                                .supplyAsync(() -> {
                                    // 每一个线程都来共享之前的请求数据
                                    RequestContextHolder.setRequestAttributes(attributes);
                                    return futureWithResultFunction.handle(task);
                                }, executorService)
                                //返回结果whenComplete获取
                                .whenComplete((v, e) -> {
                                    //收集返回结果
                                    resultList.add(v);
                                })
                    ).toArray(CompletableFuture[]::new)
        //join等待执行完毕。
        ).join();
        //关闭线程池
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
        //返回结果集
        return resultList;
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param taskList taskList
     * @param futureNoResultConsumer flowNoResultConsumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(List<T> taskList, FutureNoResultConsumer<T> futureNoResultConsumer){
        //定长5线程池
        ExecutorService executorService = initExecutorService(NumberConst.NUM_5);
        //执行条件
        runNoResult(taskList,executorService, futureNoResultConsumer);
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
        // 获取当前线程请求头信息(解决丢失请求头问题)
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        //join等待执行完毕。无返回值。
        CompletableFuture
                .runAsync(()-> {
                    // 每一个线程都来共享之前的请求数据
                    RequestContextHolder.setRequestAttributes(attributes);
                    futureNoResultConsumer.handle(taskList);
                },executorService)
                .join();
        //关闭线程池
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
    }

    /**
     * 功能描述:
     * 〈执行不带返回值的Future〉
     * @param task task
     * @param consumer consumer
     * @author 蝉鸣
     */
    public static <T> void runNoResult(T task, Consumer<T> consumer){
        //定长5线程池
        ExecutorService executorService = initExecutorService(NumberConst.NUM_5);
        // 获取当前线程请求头信息(解决丢失请求头问题)
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        //join等待执行完毕。无返回值。
        CompletableFuture
                .runAsync(()-> {
                    // 每一个线程都来共享之前的请求数据
                    RequestContextHolder.setRequestAttributes(attributes);
                    consumer.accept(task);
                },executorService)
                .join();
        //关闭线程池
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
    }
}

