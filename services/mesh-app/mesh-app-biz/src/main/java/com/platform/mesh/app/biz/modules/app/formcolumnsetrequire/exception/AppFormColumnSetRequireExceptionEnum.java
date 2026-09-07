package com.platform.mesh.app.biz.modules.app.formcolumnsetrequire.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 字段请求异常枚举
 * @author 蝉鸣
 */
@Schema(description = "字段请求异常枚举",enumAsRef = true)
public enum AppFormColumnSetRequireExceptionEnum implements BaseExceptionEnum<AppFormColumnSetRequireExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("app_form_column_set_require",500, null,  "formColumnSetRequire参数为空"),
    ADD_NO_INVALID("app_form_column_set_require",501, null,  "formColumnSetRequire参数异常"),
    ADD_NO_AUTH("app_form_column_set_require",502, null,  "系统配置，不支持修改"),
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


    AppFormColumnSetRequireExceptionEnum(String module, Integer code, Object[] args, String desc) {
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