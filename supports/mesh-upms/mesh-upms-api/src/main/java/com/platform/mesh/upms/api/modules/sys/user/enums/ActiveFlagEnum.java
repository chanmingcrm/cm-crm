package com.platform.mesh.upms.api.modules.sys.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 激活类型枚举
 * @author 蝉鸣
 */
@Schema(description = "激活类型枚举",enumAsRef = true)
public enum ActiveFlagEnum implements BaseEnum<ActiveFlagEnum, Integer> {

    /**
     * 使用中
     */
    USING(1,  "已激活"),
    /**
     * 已停用
     */
    STOPPED(2,  "已停用"),
    ;


    private final Integer value;

    private final String desc;

    ActiveFlagEnum(Integer value, String desc) {
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
