package com.platform.mesh.app.biz.modules.app.formcolumnrole.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 表单字段权限异常枚举
 * @author 蝉鸣
 */
@Schema(description = "表单字段权限异常枚举", enumAsRef = true)
public enum AppFormColumnRoleExceptionEnum implements BaseExceptionEnum<AppFormColumnRoleExceptionEnum, String> {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("app-form-column-role", 500, null, "字段权限参数为空"),
    ADD_NO_INVALID("app-form-column-role", 501, null, "字段权限参数异常"),
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

    AppFormColumnRoleExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
