package com.platform.mesh.upms.biz.modules.sys.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "用户类型枚举",enumAsRef = true)
public enum UserFlagEnum implements BaseEnum<UserFlagEnum, Integer> {

    /**
     * 使用中
     */
    USING(1,  "已激活"),
    /**
     * 已停用
     */
    STOPED(2,  "已停用"),
    ;


    private final Integer value;

    private final String desc;

    UserFlagEnum(Integer value, String desc) {
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
