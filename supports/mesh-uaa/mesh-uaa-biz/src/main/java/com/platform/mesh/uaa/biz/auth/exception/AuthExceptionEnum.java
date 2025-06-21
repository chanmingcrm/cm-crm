package com.platform.mesh.uaa.biz.auth.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 动作异常枚举
 * @author 蝉鸣
 */
@Schema(description = "授权异常枚举",enumAsRef = true)
public enum AuthExceptionEnum implements BaseExceptionEnum<AuthExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("uaa-auth",500, null,  "参数为空"),
    ADD_NO_INVALID("uaa-auth",501, null,  "动作参数异常"),
    USER_NO_EXIST("uaa-auth",502, null,  "用户:{}不存在异常"),
    USER_IN_FREEZE("uaa-auth",503, null,  "用户:{}已冻结异常"),
    AUTH_CLIENT_INVALID("uaa-auth",504, null,  "客户端clientId 不合法"),
    AUTH_CLIENT_SOURCE_INVALID("uaa-auth",505, null,  "客户端来源不合法"),
    AUTH_CLIENT_LOGIN_INVALID("uaa-auth",506, null,  "客户端登录授权不合法"),
    AUTH_CLIENT_LOGIN_PREFIX_INVALID("uaa-auth",506, null,  "客户端登录授权请求前缀:BEARER不合法"),
    AUTH_LOGIN_SMS_EXPIRE("uaa-auth",507, null,  "短信验证码无效"),
    ;

    /**
     * 所属模块
     */
    private final String module;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误码对应的参数
     */
    private final Object[] args;

    /**
     * 错误消息
     */
    private final String desc;


    AuthExceptionEnum(String module, Integer code, Object[] args, String desc) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.desc = desc;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getDesc() {
        return desc;
    }



}
