package com.platform.mesh.app.biz.modules.map.log.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 地图打卡类型枚举
 * @author 蝉鸣
 */
public enum InOutFlagEnum implements BaseEnum<InOutFlagEnum, Integer> {

    /**
     * 进场
     */
    IN(1,  "进场"),

    /**
     * 出场
     */
    OUT(2,  "出场"),
    ;


    private final Integer value;

    private final String desc;

    InOutFlagEnum(Integer value, String desc) {
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
