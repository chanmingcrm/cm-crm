package com.platform.mesh.log.enums;

import com.platform.mesh.core.enums.base.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description
 * @author 蝉鸣
 */
@Schema(description = "登陆标识枚举",enumAsRef = true)
public enum LoginFlagEnum implements BaseEnum<LoginFlagEnum, Integer> {

    /**
     * 登录标识枚举
     */
    ERROR(1,  "登录异常"),
    LOGIN(2,  "登录成功"),
    LOGOUT(3,  "登出成功"),
    ;
    private final Integer value;

    private final String desc;

    LoginFlagEnum(Integer value, String desc) {
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
