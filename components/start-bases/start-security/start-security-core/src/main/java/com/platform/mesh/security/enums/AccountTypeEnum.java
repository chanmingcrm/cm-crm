package com.platform.mesh.security.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 账户类型枚举
 * @author 蝉鸣
 */
public enum AccountTypeEnum implements BaseEnum<AccountTypeEnum, Integer> {

    /**
     * 系统用户
     */
    SYSTEM(1,  "系统用户"),
    /**
     * 游客用户
     */
    TOURIST(2,  "游客用户"),
    ;


    private final Integer value;

    private final String desc;

    AccountTypeEnum(Integer value, String desc) {
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
