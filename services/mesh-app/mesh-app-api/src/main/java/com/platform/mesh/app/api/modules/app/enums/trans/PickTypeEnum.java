package com.platform.mesh.app.api.modules.app.enums.trans;


import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "模块分配类型枚举",enumAsRef = true)
public enum PickTypeEnum implements BaseEnum<PickTypeEnum, Integer> {

    RANDOM(0,"随机"),

    LOOP(1,"数量"),

    RATIO(2,"比例");

    private final Integer value;

    private final String desc;

    PickTypeEnum(Integer value, String desc) {
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
