package com.platform.mesh.core.enums.custom;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 基础枚举
 * @author 蝉鸣
 */
public enum YesOrNoEnum implements BaseEnum<YesOrNoEnum, Integer> {

    /**
     * 无
     */
    INIT(0,  "无"),
    /**
     * 确定
     */
    YES(1,  "确定"),
    /**
     * 否定
     */
    NO(2,  "否定"),
    ;


    private final Integer value;

    private final String desc;

    YesOrNoEnum(Integer value, String desc) {
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
