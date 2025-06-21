package com.platform.mesh.crm.biz.modules.crm.onfollow.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系跟进拜访异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系跟进拜访异常枚举",enumAsRef = true)
public enum CrmOnFollowExceptionEnum implements BaseExceptionEnum<CrmOnFollowExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("crm_on_follow",500, null,  "客户关系跟进拜访参数为空"),
     ADD_NO_INVALID("crm_on_follow",501, null,  "客户关系跟进拜访参数异常"),
     ADD_EXISTS_INVALID("crm_on_follow",502, null,  "客户关系跟进拜访已经存在"),
     ADD_MODULE_INVALID("crm_on_follow",503, null,  "客户关系跟进拜访存储与模块信息不一致"),
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


    CrmOnFollowExceptionEnum(String module, Integer code, Object[] args, String desc) {
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