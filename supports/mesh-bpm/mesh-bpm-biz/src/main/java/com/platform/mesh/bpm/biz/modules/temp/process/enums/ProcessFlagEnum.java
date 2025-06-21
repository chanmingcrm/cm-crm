package com.platform.mesh.bpm.biz.modules.temp.process.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "流程类型枚举",enumAsRef = true)
public enum ProcessFlagEnum implements BaseEnum<ProcessFlagEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 表单流程
     */
    FORM(1, "表单流程"),
    /**
     * 阶段流程
     */
    STAGE(2, "阶段流程"),
    /**
     * OA流程
     */
    OA(3,  "OA流程"),
    ;

    private final Integer value;

    private final String desc;

    ProcessFlagEnum(Integer value, String desc) {
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
