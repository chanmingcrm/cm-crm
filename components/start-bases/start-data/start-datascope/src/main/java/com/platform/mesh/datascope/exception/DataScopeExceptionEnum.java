package com.platform.mesh.datascope.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 层级异常枚举
 * @author 蝉鸣
 */
@Schema(description = "数据权限异常枚举",enumAsRef = true)
public enum DataScopeExceptionEnum implements BaseExceptionEnum<DataScopeExceptionEnum, String>  {

    /**
     * 异常信息
     */
    DATA_SCOPE_LOGIN_USER("data-scope",500, null,  "登录人员信息异常"),
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


    DataScopeExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
