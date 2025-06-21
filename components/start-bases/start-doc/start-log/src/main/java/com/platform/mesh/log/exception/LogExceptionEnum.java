package com.platform.mesh.log.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;

/**
 * @description 动作异常枚举
 * @author 蝉鸣
 */
public enum LogExceptionEnum implements BaseExceptionEnum<LogExceptionEnum, String>  {

    /**
     * 异常信息
     */
    LOG_NO_ARGS("packs-file",500, null,  "参数为空"),
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


    LogExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
