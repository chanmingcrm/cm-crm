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
    USER_LOCKED("uaa-auth",504, null,  "用户被锁"),
    USER_DISABLE("uaa-auth",505, null,  "用户禁用"),
    USER_EXPIRED("uaa-auth",506, null,  "用户过期"),
    AUTH_CLIENT_INVALID("uaa-auth",507, null,  "客户端clientId 异常"),
    AUTH_CLIENT_SOURCE_INVALID("uaa-auth",508, null,  "客户端来源异常"),
    AUTH_CLIENT_LOGIN_INVALID("uaa-auth",509, null,  "客户端登录授权异常"),
    AUTH_CLIENT_LOGIN_PREFIX_INVALID("uaa-auth",510, null,  "客户端登录授权请求前缀:BEARER异常"),
    AUTH_PASSWORD_INVALID("uaa-auth",511, null,  "用户名或密码错误"),
    AUTH_USERNAME_NOT_FOUND("uaa-auth",512, null,  "用户名未找到"),
    AUTH_BAD_CREDENTIALS("uaa-auth",513, null,  "错误凭证"),
    AUTH_CREDENTIALS_EXPIRED("uaa-auth",514, null,  "证书过期"),
    AUTH_SCOPE_IS_EMPTY("uaa-auth",515, null,  "scope 为空异常"),
    AUTH_TOKEN_MISSING("uaa-auth",516, null,  "令牌不存在"),
    AUTH_UN_KNOW_LOGIN_ERROR("uaa-auth",517, null,  "未知的登录异常"),
    TENANT_DISABLE("uaa-auth",518, null,  "租户禁用"),
    TENANT_EXPIRED("uaa-auth",519, null,  "租户过期"),
    TENANT_DELETE("uaa-auth",520, null,  "租户已删除"),
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
