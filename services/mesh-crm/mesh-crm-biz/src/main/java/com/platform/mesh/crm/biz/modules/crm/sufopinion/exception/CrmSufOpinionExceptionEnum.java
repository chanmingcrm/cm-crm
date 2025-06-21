package com.platform.mesh.crm.biz.modules.crm.sufopinion.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系意见评价异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系意见评价异常枚举",enumAsRef = true)
public enum CrmSufOpinionExceptionEnum implements BaseExceptionEnum<CrmSufOpinionExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("crm_suf_opinion",500, null,  "客户关系意见评价参数为空"),
     ADD_NO_INVALID("crm_suf_opinion",501, null,  "客户关系意见评价参数异常"),
     ADD_EXISTS_INVALID("crm_suf_opinion",502, null,  "客户关系意见评价已经存在"),
     ADD_MODULE_INVALID("crm_suf_opinion",503, null,  "客户关系意见评价存储与模块信息不一致"),
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


    CrmSufOpinionExceptionEnum(String module, Integer code, Object[] args, String desc) {
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