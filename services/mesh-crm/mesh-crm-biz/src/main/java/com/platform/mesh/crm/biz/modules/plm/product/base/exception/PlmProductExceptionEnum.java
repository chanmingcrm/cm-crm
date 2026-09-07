package com.platform.mesh.crm.biz.modules.plm.product.base.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 供应链产品异常枚举
 * @author 蝉鸣
 */
@Schema(description = "供应链产品异常枚举",enumAsRef = true)
public enum PlmProductExceptionEnum implements BaseExceptionEnum<PlmProductExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("plm_product",500, null,  "供应链产品参数为空"),
    ADD_NO_INVALID("plm_product",501, null,  "供应链产品参数异常"),
    ADD_EXISTS_INVALID("plm_product",502, null,  "供应链产品已经存在"),
    ADD_MODULE_INVALID("plm_product",503, null,  "供应链产品存储与模块信息不一致"),
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


    PlmProductExceptionEnum(String module, Integer code, Object[] args, String desc) {
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