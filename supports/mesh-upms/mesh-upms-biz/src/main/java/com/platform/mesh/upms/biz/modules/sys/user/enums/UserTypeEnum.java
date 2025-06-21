package com.platform.mesh.upms.biz.modules.sys.user.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 用户类型枚举
 * @author 蝉鸣
 */
@Schema(description = "用户类型枚举",enumAsRef = true)
public enum UserTypeEnum implements BaseEnum<UserTypeEnum, Integer> {

    /**
     * 系统用户
     */
    SYSTEM(1,  "系统用户"),
    /**
     * 游客用户
     */
    TOURIST(2,  "游客用户"),
    ;


    private final Integer value;

    private final String desc;

    UserTypeEnum(Integer value, String desc) {
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
