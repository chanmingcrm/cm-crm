package com.platform.mesh.utils.context;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 功能描述:
 * 〈线程上下文扩展注册表〉
 * @author 蝉鸣
 */
public final class ThreadContextRegistry {

    private static final CopyOnWriteArrayList<ThreadContextAccessor<?>> ACCESSORS =
            new CopyOnWriteArrayList<>();

    private ThreadContextRegistry() {
    }

    /**
     * 功能描述:
     * 〈注册线程上下文扩展〉
     * @param accessor 线程上下文扩展
     * @return 注销句柄
     * @author 蝉鸣
     */
    public static Registration register(ThreadContextAccessor<?> accessor) {
        ThreadContextAccessor<?> contextAccessor = Objects.requireNonNull(accessor, "线程上下文扩展不能为空");
        boolean exists = ACCESSORS.stream().anyMatch(item -> item == contextAccessor);
        if (!exists) {
            ACCESSORS.add(contextAccessor);
        }
        AtomicBoolean registered = new AtomicBoolean(!exists);
        return () -> {
            if (registered.compareAndSet(true, false)) {
                ACCESSORS.removeIf(item -> item == contextAccessor);
            }
        };
    }

    static List<ThreadContextAccessor<?>> accessors() {
        return List.copyOf(ACCESSORS);
    }

    static void clearForTest() {
        ACCESSORS.clear();
    }

    /**
     * 功能描述:
     * 〈线程上下文扩展注销句柄〉
     * @author 蝉鸣
     */
    public interface Registration extends AutoCloseable {

        /**
         * 功能描述:
         * 〈注销线程上下文扩展〉
         * @author 蝉鸣
         */
        @Override
        void close();
    }
}
