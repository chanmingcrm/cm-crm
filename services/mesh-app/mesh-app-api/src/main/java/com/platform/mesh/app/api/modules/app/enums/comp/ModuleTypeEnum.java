package com.platform.mesh.app.api.modules.app.enums.comp;


import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "模块类型枚举",enumAsRef = true)
public enum ModuleTypeEnum implements BaseEnum<ModuleTypeEnum, Integer> {

    APPLICATION(1,"应用"),

    MODULE(2,"模块"),

    CATEGORY(3,"模块分类");
    private final Integer value;

    private final String desc;

    ModuleTypeEnum(Integer value, String desc) {
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
