package com.platform.mesh.utils.format;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 时间单位枚举
 * @author 蝉鸣
 */
@Schema(description = "时间单位枚举",enumAsRef = true)
public enum TimeUnitEnum implements BaseEnum<TimeUnitEnum, Integer> {

    /**
     * 元状态
     */
    INIT(0,  "元状态"),
    /**
     * 年
     */
    YEAR(1,  "年"),
    /**
     * 季
     */
    QUARTER(2,  "季"),
    /**
     * 月
     */
    MONTH(3,  "月"),
    /**
     * 周
     */
    WEEK(4,  "周"),
    /**
     * 日
     */
    DAY(5,  "日"),
    /**
     * 时
     */
    HOUR(6,  "时"),
    /**
     * 分
     */
    MINUTE(7,  "分"),
    /**
     * 秒
     */
    SECOND(8,  "秒"),
    ;


    private final Integer value;

    private final String desc;

    TimeUnitEnum(Integer value, String desc) {
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
