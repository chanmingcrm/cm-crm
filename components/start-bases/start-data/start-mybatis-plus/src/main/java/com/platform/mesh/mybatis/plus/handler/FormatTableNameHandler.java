package com.platform.mesh.mybatis.plus.handler;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @description 动态表名处理
 * @author 蝉鸣
 */
@Component
@AllArgsConstructor
public class FormatTableNameHandler implements TableNameHandler {

    /**
     * 线程隔离，避免多线程数据冲突
     */
    private static final ThreadLocal<Boolean> ENABLE_TABLE_NAME = new ThreadLocal<>();
    /**
     * 线程隔离，避免多线程数据冲突
     */
    private static final ThreadLocal<String> FORMAT_TABLE_NAME = new ThreadLocal<>();

    /**
     * 开启重定表单名称
     * @param enable enable
     */
    public static void enableTableName(Boolean enable) {
        ENABLE_TABLE_NAME.set(enable);
    }

    /**
     * 关闭重定表单名称
     */
    public static void unEnableTableName() {
        FORMAT_TABLE_NAME.remove();
        ENABLE_TABLE_NAME.remove();
    }

    /**
     * 设置表单名称
     * @param tableName tableName
     */
    public static void setTableName(String tableName) {
        FORMAT_TABLE_NAME.set(tableName);
    }

    /**
     * 重置表单名称
     */
    public static void removeTableName() {
        FORMAT_TABLE_NAME.remove();
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        Boolean enabled = ENABLE_TABLE_NAME.get();
        if(ObjectUtil.isEmpty(enabled) || !enabled) {
            return tableName;
        }
        return FORMAT_TABLE_NAME.get();
    }
}
