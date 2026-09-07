package com.platform.mesh.ai.biz.modules.cc.setword.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 提示语类型
 * @author 蝉鸣
 */
@Schema(description = "提示语频率",enumAsRef = true)
public enum WordRateEnum implements BaseEnum<WordRateEnum, Integer> {

    /**
     * 一次
     */
    ONE(1,  "一次"),
    /**
     * 多次
     */
    MORE(2,  "多次"),
    ;


    private final Integer value;

    private final String desc;

    WordRateEnum(Integer value, String desc) {
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
