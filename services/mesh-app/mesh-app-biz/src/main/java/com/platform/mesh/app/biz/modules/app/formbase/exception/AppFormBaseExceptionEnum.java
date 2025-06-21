package com.platform.mesh.app.biz.modules.app.formbase.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 单异常枚举
 * @author 蝉鸣
 */
@Schema(description = "单异常枚举",enumAsRef = true)
public enum AppFormBaseExceptionEnum implements BaseExceptionEnum<AppFormBaseExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("app-form-base",500, null,  "表单参数为空"),
     ADD_NO_INVALID("app-form-base",501, null,  "表单参数异常"),
     ADD_NO_DEFAULT("app-form-base",502, null,  "同类型表单没有默认值异常"),
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


    AppFormBaseExceptionEnum(String module, Integer code, Object[] args, String desc) {
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