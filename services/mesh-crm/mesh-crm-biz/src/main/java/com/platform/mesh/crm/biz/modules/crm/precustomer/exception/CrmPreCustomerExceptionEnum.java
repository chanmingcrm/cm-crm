package com.platform.mesh.crm.biz.modules.crm.precustomer.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系客户对象异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系客户对象异常枚举",enumAsRef = true)
public enum CrmPreCustomerExceptionEnum implements BaseExceptionEnum<CrmPreCustomerExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("crm-pre-customer",500, null,  "客户关系客户对象参数为空"),
    ADD_NO_INVALID("crm-pre-customer",501, null,  "客户关系客户对象参数异常"),
    ADD_EXISTS_INVALID("crm-pre-customer",502, null,  "客户关系客户对象已经存在"),
    ADD_MODULE_INVALID("crm-pre-customer",503, null,  "客户关系客户对象存储与模块信息不一致"),
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


    CrmPreCustomerExceptionEnum(String module, Integer code, Object[] args, String desc) {
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