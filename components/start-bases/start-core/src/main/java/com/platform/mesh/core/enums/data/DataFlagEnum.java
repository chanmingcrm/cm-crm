package com.platform.mesh.core.enums.data;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 数据类型
 * @author 蝉鸣
 */
public enum DataFlagEnum implements BaseEnum<DataFlagEnum, Integer> {

    /**
     * 组织
     */
    ORG(1,  "组织"),
    /**
     * 人员
     */
    USER(2,  "人员"),
    ;


    private final Integer value;

    private final String desc;

    DataFlagEnum(Integer value, String desc) {
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
