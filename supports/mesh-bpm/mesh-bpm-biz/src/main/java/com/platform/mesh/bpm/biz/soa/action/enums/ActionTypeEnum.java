package com.platform.mesh.bpm.biz.soa.action.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 过程类型枚举
 * @author 蝉鸣
 */
@Schema(description = "动作类型枚举",enumAsRef = true)
public enum ActionTypeEnum implements BaseEnum<ActionTypeEnum, Integer> {

    /**
     * 加载时
     */
    ON_LOAD(1, "ON_LOAD"),
    /**
     * 开始时
     */
    ON_START(2,"ON_START"),
    /**
     * 执行时
     */
    ON_PROCESS(3, "ON_PROCESS"),
    /**
     * 成功时
     */
    ON_SUCCESS(4, "ON_SUCCESS"),
    /**
     * 失败时
     */
    ON_ERROR(5, "ON_ERROR"),
    /**
     * 结束时
     */
    ON_END(6, "ON_END")

    ;


    private final Integer value;

    private final String desc;

    ActionTypeEnum(Integer value, String desc) {
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
