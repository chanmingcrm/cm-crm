package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 第三方字段映射设置异常枚举
 * @author 蝉鸣
 */
@Schema(description = "第三方字段映射设置异常枚举",enumAsRef = true)
public enum ThirdFormColumnMappingExceptionEnum implements BaseExceptionEnum<ThirdFormColumnMappingExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("third_form_column_mapping",500, null,  "第三方字段映射设置参数为空"),
    ADD_NO_INVALID("third_form_column_mapping",501, null,  "第三方字段映射设置参数异常"),
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


    ThirdFormColumnMappingExceptionEnum(String module, Integer code, Object[] args, String desc) {
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