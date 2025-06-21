package com.platform.mesh.upms.biz.modules.sys.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 用户类型
 * @author 蝉鸣
 */
@Schema(description = "用户类型枚举",enumAsRef = true)
public enum AdminFlagEnum implements BaseEnum<AdminFlagEnum, Integer> {

    /**
     * 系统管理员
     */
    SYSTEM(1,  "系统管理员"),
    /**
     * 普通管理员
     */
    TENANT(2,  "普通管理员"),
    /**
     * 普通账户
     */
    COMMON(2,  "普通账户"),
    ;


    private final Integer value;

    private final String desc;

    AdminFlagEnum(Integer value, String desc) {
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
