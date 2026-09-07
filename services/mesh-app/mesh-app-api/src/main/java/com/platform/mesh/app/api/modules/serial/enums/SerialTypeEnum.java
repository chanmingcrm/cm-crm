package com.platform.mesh.app.api.modules.serial.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author 蝉鸣
 * @since 2024/8/29 16:54
 **/
@Schema(description = "字段类型枚举",enumAsRef = true)
public enum SerialTypeEnum implements BaseEnum<SerialTypeEnum, Integer> {
    TEXT(1,"文本"),
    DATE(2,"日期"),
    NUM(3,"数字"),
    ;


    private final Integer value;
    private final String desc;

    SerialTypeEnum(Integer value, String desc) {
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
