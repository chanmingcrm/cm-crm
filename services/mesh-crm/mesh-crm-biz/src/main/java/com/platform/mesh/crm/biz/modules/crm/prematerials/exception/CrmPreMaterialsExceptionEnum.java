package com.platform.mesh.crm.biz.modules.crm.prematerials.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系活动物料异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系活动物料异常枚举",enumAsRef = true)
public enum CrmPreMaterialsExceptionEnum implements BaseExceptionEnum<CrmPreMaterialsExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("crm_pre_materials",500, null,  "客户关系活动物料参数为空"),
     ADD_NO_INVALID("crm_pre_materials",501, null,  "客户关系活动物料参数异常"),
     ADD_EXISTS_INVALID("crm_pre_materials",502, null,  "客户关系活动物料已经存在"),
     ADD_MODULE_INVALID("crm_pre_materials",503, null,  "客户关系活动物料存储与模块信息不一致"),
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


    CrmPreMaterialsExceptionEnum(String module, Integer code, Object[] args, String desc) {
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