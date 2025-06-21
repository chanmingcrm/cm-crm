package com.platform.mesh.core.enums.custom;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description
 * @author 蝉鸣
 */
public enum LoginTypeEnum implements BaseEnum<LoginTypeEnum, Integer> {

    /**
     * 登录类型枚举
     */
    APP(1,  "移动端"),
    PC(2,  "PC端"),
    H5(3,  "H5端"),
    ;
    private final Integer value;

    private final String desc;

    LoginTypeEnum(Integer value, String desc) {
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
