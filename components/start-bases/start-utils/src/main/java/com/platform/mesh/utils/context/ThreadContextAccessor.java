package com.platform.mesh.utils.context;

/**
 * 功能描述:
 * 〈线程上下文捕获与恢复扩展〉
 * @param <S> 上下文快照类型
 * @author 蝉鸣
 */
public interface ThreadContextAccessor<S> {

    /**
     * 功能描述:
     * 〈捕获当前线程上下文〉
     * @return 当前线程上下文快照
     * @author 蝉鸣
     */
    S capture();

    /**
     * 功能描述:
     * 〈安装线程上下文快照〉
     * @param snapshot 上下文快照
     * @return 上下文恢复作用域
     * @author 蝉鸣
     */
    Scope install(S snapshot);

    /**
     * 功能描述:
     * 〈线程上下文恢复作用域〉
     * @author 蝉鸣
     */
    interface Scope extends AutoCloseable {

        /**
         * 功能描述:
         * 〈恢复原线程上下文〉
         * @author 蝉鸣
         */
        @Override
        void close();
    }
}
