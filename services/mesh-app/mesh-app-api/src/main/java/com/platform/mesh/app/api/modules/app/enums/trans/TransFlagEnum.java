package com.platform.mesh.app.api.modules.app.enums.trans;


import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "模块转移状态类型枚举",enumAsRef = true)
public enum TransFlagEnum implements BaseEnum<TransFlagEnum, Integer> {

    INIT(0,"初始"),

    TODO(1,"待转移"),

    DONE(2,"已转移"),
    ;

    private final Integer value;

    private final String desc;

    TransFlagEnum(Integer value, String desc) {
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
