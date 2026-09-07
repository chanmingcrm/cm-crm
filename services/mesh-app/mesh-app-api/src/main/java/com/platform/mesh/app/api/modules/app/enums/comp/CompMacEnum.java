package com.platform.mesh.app.api.modules.app.enums.comp;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 需要在代码中处理的组件类型枚举
 * @author 蝉鸣
 */
@Schema(description = "需要在代码中处理的组件类型枚举",enumAsRef = true)
public enum CompMacEnum implements BaseEnum<CompMacEnum, Integer> {
    TEXT(0,"text"),
    TEXT_AREA(1,"textarea"),
    TEXT_MULTI(2,"text_multi"),
    REMIND(3,"remind"),
    BUTTON(4,"button"),
    DIALOG(5,"dialog"),
    DATE(6,"date"),
    TIME(7,"time"),
    DATE_TIME(8,"datetime"),
    NUMBER(9,"number"),
    SECOND_TABLE(10,"secondTable"),
    CHILD_TABLE(11,"childTable"),

    ;


    private final Integer value;
    private final String desc;

    CompMacEnum(Integer value,  String desc) {
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
