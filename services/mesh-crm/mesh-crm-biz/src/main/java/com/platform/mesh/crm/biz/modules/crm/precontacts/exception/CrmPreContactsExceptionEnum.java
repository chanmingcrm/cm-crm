package com.platform.mesh.crm.biz.modules.crm.precontacts.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系联系人异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系联系人异常枚举",enumAsRef = true)
public enum CrmPreContactsExceptionEnum implements BaseExceptionEnum<CrmPreContactsExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("crm-pre-contacts",500, null,  "客户关系联系人参数为空"),
     ADD_NO_INVALID("crm-pre-contacts",501, null,  "客户关系联系人参数异常"),
     ADD_EXISTS_INVALID("crm-pre-contacts",502, null,  "客户关系联系人已经存在"),
     ADD_MODULE_INVALID("crm-pre-contacts",503, null,  "客户关系联系人存储与模块信息不一致"),
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


    CrmPreContactsExceptionEnum(String module, Integer code, Object[] args, String desc) {
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