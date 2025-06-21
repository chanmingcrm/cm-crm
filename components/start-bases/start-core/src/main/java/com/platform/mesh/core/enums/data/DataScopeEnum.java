package com.platform.mesh.core.enums.data;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 数据权限
 * @author 蝉鸣
 */
public enum DataScopeEnum implements BaseEnum<DataScopeEnum, Integer> {

    /**
     * 全部
     */
    ALL(1,  "全部"),
    /**
     * 包含下属
     */
    SUB(2,  "包含下属"),
    /**
     * 同级
     */
    LEVEL(3,  "同级"),
    /**
     * 个人
     */
    SELF(4,  "个人"),
    /**
     * 自定义
     */
    CUSTOM(5,  "自定义"),
    ;


    private final Integer value;

    private final String desc;

    DataScopeEnum(Integer value, String desc) {
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
