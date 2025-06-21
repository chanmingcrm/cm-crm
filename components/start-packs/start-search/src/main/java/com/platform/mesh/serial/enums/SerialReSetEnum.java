package com.platform.mesh.serial.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.utils.format.DateTimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 蝉鸣
 * @since 2024/8/29 16:54
 **/
@Schema(description = "字段类型枚举",enumAsRef = true)
public enum SerialReSetEnum implements BaseEnum<SerialReSetEnum, Integer> {
    NONE(1,"从不"),
    YEAR(2,"每年"),
    MONTH(3,"每月"),
    DAY(4,"每天"),
    ;


    private final Integer value;
    private final String desc;

    SerialReSetEnum(Integer value, String desc) {
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

    //获取开始时间
    public LocalDateTime getBeginTime() {
        return switch (this) {
            case NONE -> null;
            case YEAR -> DateTimeUtil.getDayStartDateTime(DateTimeUtil.getYearStartDate(LocalDate.now()));
            case MONTH -> DateTimeUtil.getDayStartDateTime(DateTimeUtil.getMonthStartDate(LocalDate.now()));
            case DAY -> DateTimeUtil.getDayStartDateTime(LocalDate.now());
        };
    }
}
