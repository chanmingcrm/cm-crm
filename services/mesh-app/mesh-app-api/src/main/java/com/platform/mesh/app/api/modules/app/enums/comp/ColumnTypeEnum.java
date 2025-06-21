package com.platform.mesh.app.api.modules.app.enums.comp;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "字段组件需要特殊处理类型枚举",enumAsRef = true)
public enum ColumnTypeEnum implements BaseEnum<ColumnTypeEnum, Integer> {

    /**
     * 自定义
     */
    CUSTOM(0,  "CUSTOM"),
    /**
     * 新建
     */
    ADD(1,  "ADD"),
    /**
     * 编辑
     */
    EDIT(2,  "EDIT"),
    /**
     * 详情
     */
    DETAIL(3,  "DETAIL"),
    /**
     * 删除
     */
    DELETE(4,  "DELETE"),
    /**
     * 批量删除
     */
    DELETE_BATCH(5,  "DELETE_BATCH"),
    /**
     * 导入
     */
    IMPORT(6,  "IMPORT"),
    /**
     * 导出
     */
    EXPORT(7,  "EXPORT"),
    /**
     * 转化
     */
    TRANS_DATA(8,  "转化"),
    /**
     * 转移
     */
    TRANS_USER(9,  "转移"),
    ;


    private final Integer value;

    private final String desc;

    ColumnTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }

}
