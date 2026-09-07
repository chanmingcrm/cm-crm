package com.platform.mesh.app.api.modules.app.enums.comp;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description  需要与前端组件类型同步
 * @author 蝉鸣
 */
@Schema(description = "字段组件需要特殊处理类型枚举",enumAsRef = true)
public enum ColumnTypeEnum implements BaseEnum<ColumnTypeEnum, Integer> {

    /**
     * 自定义
     */
    CUSTOM(0,  "自定义"),
    /**
     * 新建
     */
    ADD(1,  "新建"),
    /**
     * 编辑
     */
    EDIT(2,  "编辑"),
    /**
     * 详情
     */
    DETAIL(3,  "详情"),
    /**
     * 删除
     */
    DELETE(4,  "删除"),
    /**
     * 批量删除
     */
    DELETE_BATCH(5,  "批量删除"),
    /**
     * 导入
     */
    IMPORT(6,  "导入"),
    /**
     * 导出
     */
    EXPORT(7,  "导出"),
    /**
     * 表格
     */
    TABLE(8,  "表格"),
    /**
     * 转移
     */
    TRANS_USER(9,  "转移"),
    /**
     * 分配
     */
    PEEK_DATA(10,  "分配"),
    /**
     * 转化
     */
    TRANS_DATA(11,  "转化"),
    /**
     * 流程阶段
     */
    PROCESS_STAGE(13,  "流程阶段"),

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
