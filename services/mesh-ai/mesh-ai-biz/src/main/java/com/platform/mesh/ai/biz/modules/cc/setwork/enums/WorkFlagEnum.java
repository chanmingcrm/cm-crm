package com.platform.mesh.ai.biz.modules.cc.setwork.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 排班类型
 * @author 蝉鸣
 */
@Schema(description = "排班类型",enumAsRef = true)
public enum WorkFlagEnum implements BaseEnum<WorkFlagEnum, Integer> {

    /**
     * AI
     */
    AI(1,  "AI"),
    /**
     * 临时
     */
    TEMP(2,  "临时"),
    /**
     * 长久的
     */
    LONG(3,  "长久的"),
    ;


    private final Integer value;

    private final String desc;

    WorkFlagEnum(Integer value, String desc) {
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
