package com.platform.mesh.es.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

public enum EsBoolEnum implements BaseEnum<EsBoolEnum, Integer> {

    /**
     * 必须匹配每个子项
     */
    MUST(1,  "必须匹配每个子项"),
    /**
     * 选择匹配,不参与算分
     */
    MUST_NOT(2,  "选择匹配,不参与算分"),
    /**
     * 必须不匹配
     */
    SHOULD(3,  "必须不匹配"),
    /**
     * 必须匹配,不参与算分
     */
    FILTER(4,  "必须匹配,不参与算分"),
    ;


    private final Integer value;

    private final String desc;

    EsBoolEnum(Integer value, String desc) {
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
