package com.platform.mesh.core.enums.custom;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 消息类型枚举
 * @author 蝉鸣
 */
public enum SmsFlagEnum implements BaseEnum<SmsFlagEnum, Integer> {

    /**
     * 其他
     */
    INIT(0,  "INIT"),

    /**
     * 注册
     */
    REGISTER(1,  "REGISTER"),

    /**
     * 登录
     */
    LOGIN(2,  "LOGIN"),

    /**
     * 校验
     */
    CHECK(3,  "CHECK"),

    /**
     * 密码
     */
    PASSWORD(4,  "PASSWORD"),
    ;


    private final Integer value;

    private final String desc;

    SmsFlagEnum(Integer value, String desc) {
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
