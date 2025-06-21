package com.platform.mesh.app.biz.modules.map.log.enums;

import com.platform.mesh.core.enums.base.BaseEnum;

/**
 * @description 地图打卡类型枚举
 * @author 蝉鸣
 */
public enum MapLogFlagEnum implements BaseEnum<MapLogFlagEnum, Integer> {

    /**
     * CRM客户外勤签到
     */
    CRM_FIELD_CHECK_IN(1,  "CRM客户外勤签到"),

    /**
     * 人事考勤打卡
     */
    HRM_WORK_CHECK_ON(2,  "人事考勤打卡"),
    ;


    private final Integer value;

    private final String desc;

    MapLogFlagEnum(Integer value, String desc) {
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
