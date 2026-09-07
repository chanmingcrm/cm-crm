package com.platform.mesh.crm.biz.modules.crm.precustomer.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 成交状态枚举
 * @author 蝉鸣
 */
public enum ConfirmFlagEnum implements BaseEnum<ConfirmFlagEnum, Integer> {

    /**
     * 无
     */
    INIT(0,  "无"),
    /**
     * 已成交
     */
    YES(1,  "已成交"),
    /**
     * 未成交
     */
    NO(2,  "未成交"),
    ;


    private final Integer value;

    private final String desc;

    ConfirmFlagEnum(Integer value, String desc) {
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
