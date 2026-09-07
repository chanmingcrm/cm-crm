package com.platform.mesh.utils.context;

import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 功能描述:
 * 〈线程上下文快照〉
 * @author 蝉鸣
 */
public final class ThreadContextSnapshot {

    private final RequestAttributes requestAttributes;
    private final Map<String, String> mdcContext;
    private final List<CapturedContext<?>> contexts;

    private ThreadContextSnapshot(RequestAttributes requestAttributes,
            Map<String, String> mdcContext, List<CapturedContext<?>> contexts) {
        this.requestAttributes = requestAttributes;
        this.mdcContext = mdcContext;
        this.contexts = contexts;
    }

    /**
     * 功能描述:
     * 〈捕获当前线程上下文〉
     * @return 线程上下文快照
     * @author 蝉鸣
     */
    public static ThreadContextSnapshot capture() {
        List<CapturedContext<?>> contexts = new ArrayList<>();
        for (ThreadContextAccessor<?> accessor : ThreadContextRegistry.accessors()) {
            contexts.add(capture(accessor));
        }
        return new ThreadContextSnapshot(RequestContextHolder.getRequestAttributes(),
                MDC.getCopyOfContextMap(), List.copyOf(contexts));
    }

    /**
     * 功能描述:
     * 〈包装无返回值任务〉
     * @param runnable 原任务
     * @return 带上下文传播的任务
     * @author 蝉鸣
     */
    public Runnable wrap(Runnable runnable) {
        return () -> execute(() -> {
            runnable.run();
            return null;
        });
    }

    /**
     * 功能描述:
     * 〈包装带返回值任务〉
     * @param supplier 原任务
     * @return 带上下文传播的任务
     * @param <T> 返回值类型
     * @author 蝉鸣
     */
    public <T> Supplier<T> wrap(Supplier<T> supplier) {
        return () -> execute(supplier);
    }

    private <T> T execute(Supplier<T> supplier) {
        RequestAttributes previousRequestAttributes = RequestContextHolder.getRequestAttributes();
        Map<String, String> previousMdcContext = MDC.getCopyOfContextMap();
        List<ThreadContextAccessor.Scope> scopes = new ArrayList<>();
        Throwable failure = null;
        try {
            setRequestAttributes(requestAttributes);
            setMdcContext(mdcContext);
            for (CapturedContext<?> context : contexts) {
                scopes.add(context.install());
            }
            return supplier.get();
        }
        catch (RuntimeException | Error exception) {
            failure = exception;
            throw exception;
        }
        finally {
            RuntimeException closeException = closeScopes(scopes);
            setRequestAttributes(previousRequestAttributes);
            setMdcContext(previousMdcContext);
            if (closeException != null) {
                if (failure != null) {
                    failure.addSuppressed(closeException);
                }
                else {
                    throw closeException;
                }
            }
        }
    }

    private RuntimeException closeScopes(List<ThreadContextAccessor.Scope> scopes) {
        Collections.reverse(scopes);
        RuntimeException failure = null;
        for (ThreadContextAccessor.Scope scope : scopes) {
            try {
                scope.close();
            }
            catch (RuntimeException exception) {
                if (failure == null) {
                    failure = exception;
                }
                else {
                    failure.addSuppressed(exception);
                }
            }
        }
        return failure;
    }

    private static void setRequestAttributes(RequestAttributes attributes) {
        if (attributes == null) {
            RequestContextHolder.resetRequestAttributes();
        }
        else {
            RequestContextHolder.setRequestAttributes(attributes);
        }
    }

    private static void setMdcContext(Map<String, String> context) {
        if (context == null) {
            MDC.clear();
        }
        else {
            MDC.setContextMap(context);
        }
    }

    private static <S> CapturedContext<S> capture(ThreadContextAccessor<S> accessor) {
        return new CapturedContext<>(accessor, accessor.capture());
    }

    private record CapturedContext<S>(ThreadContextAccessor<S> accessor, S snapshot) {

        private ThreadContextAccessor.Scope install() {
            return accessor.install(snapshot);
        }
    }
}
