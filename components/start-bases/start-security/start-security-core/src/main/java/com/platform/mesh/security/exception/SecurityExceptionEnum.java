package com.platform.mesh.security.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;

/**
 * @description 动作异常枚举
 * @author 蝉鸣
 */
public enum SecurityExceptionEnum implements BaseExceptionEnum<SecurityExceptionEnum, String>  {

    /**
     * 异常信息
     */
    SECURITY_NO_ARGS("base-security",500, null,  "参数为空"),
    SECURITY_NO_USER_INFO("base-security",501, null,  "获取用户信息主体失败"),
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


    SecurityExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
