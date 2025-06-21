package com.platform.mesh.upms.biz.modules.sys.user.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 用户异常枚举
 * @author 蝉鸣
 */
@Schema(description = "用户异常枚举",enumAsRef = true)
public enum UserExceptionEnum implements BaseExceptionEnum<UserExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("upms-user",500, null,  "用户参数为空"),
    ADD_NO_INVALID("upms-user",501, null,  "用户参数异常"),
    USER_INIT_EXISTS_PHONE("upms-user",502, null,  "用户初始化手机已存在"),
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


    UserExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
