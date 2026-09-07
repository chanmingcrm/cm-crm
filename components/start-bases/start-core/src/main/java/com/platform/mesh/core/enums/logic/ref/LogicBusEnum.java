package com.platform.mesh.core.enums.logic.ref;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "逻辑业务枚举",enumAsRef = true)
public enum LogicBusEnum implements BaseEnum<LogicBusEnum, Integer> {

    /**
     * 元状态:无需特殊处理的条件
     */
    INIT(0,  "元状态"),
    /**
     * 根据模块名称查询
     */
    CUSTOM_MODULE_ID(1,  "根据模块名称查询"),
    ;

    private final Integer value;

    private final String desc;

    LogicBusEnum(Integer value, String desc) {
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
