package com.platform.mesh.app.api.modules.app.enums.comp;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 需要在代码中处理的组件类型枚举
 * @author 蝉鸣
 */
@Schema(description = "需要在代码中处理的组件类型枚举",enumAsRef = true)
public enum CompMacEnum implements BaseEnum<CompMacEnum, Integer> {
    REMIND(1,"remind"),
    BUTTON(2,"button"),
    DIALOG(3,"dialog"),
    DATE(4,"date"),
    NUMBER(5,"number"),
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
