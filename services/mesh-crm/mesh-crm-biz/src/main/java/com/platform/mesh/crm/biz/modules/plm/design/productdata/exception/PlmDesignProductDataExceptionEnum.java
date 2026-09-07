package com.platform.mesh.crm.biz.modules.plm.design.productdata.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


 /**
 * @description 产品设计数据异常枚举
 * @author 蝉鸣
 */
@Schema(description = "产品设计异常枚举",enumAsRef = true)
public enum PlmDesignProductDataExceptionEnum implements BaseExceptionEnum<PlmDesignProductDataExceptionEnum,String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("plm_design_product_data",500, null,  "产品设计数据参数为空"),
    ADD_NO_INVALID("plm_design_product_data",501, null,  "产品设计数据参数异常"),
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


    PlmDesignProductDataExceptionEnum(String module, Integer code, Object[] args, String desc) {
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