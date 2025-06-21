package com.platform.mesh.app.biz.modules.app.modulebase.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 复制类型枚举
 * @author 蝉鸣
 */
public enum CopyTypeEnum implements BaseEnum<CopyTypeEnum, Integer> {

    /**
     * 复制模块父级
     */
    PARENT(1,  "复制父级"),

    /**
     * 复制模块同级
     */
    SELF(2,  "复制同级"),
    ;


    private final Integer value;

    private final String desc;

    CopyTypeEnum(Integer value, String desc) {
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
