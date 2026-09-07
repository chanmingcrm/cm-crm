package com.platform.mesh.gen.biz.modules.gen.grouprel.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 账户类型枚举
 * @author 蝉鸣
 */
@Schema(description = "账户类型枚举",enumAsRef = true)
public enum GenGroupRelEnum implements BaseEnum<GenGroupRelEnum, Integer> {

    /**
     * 构建
     */
    CODE_BUILD(1,  "构建"),
    /**
     * 构建配置
     */
    CODE_BUILD_CONF(2,  "构建配置"),
    /**
     * 数据源
     */
    CODE_DS(3,  "数据源"),
    /**
     * 字段映射
     */
    CODE_FILED_MAPPING(4,  "字段映射"),
    /**
     * 表单
     */
    CODE_TABLE(5,  "表单"),
    /**
     * 表单字段
     */
    CODE_TABLE_COLUMN(6,  "表单字段"),
    /**
     * 模板
     */
    CODE_TEMP(7,  "模板"),
    ;


    private final Integer value;

    private final String desc;

    GenGroupRelEnum(Integer value, String desc) {
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
