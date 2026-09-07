package com.platform.mesh.crm.biz.modules.crm.predrainage.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description 客户关系活动引流异常枚举
 * @author 蝉鸣
 */
@Schema(description = "客户关系活动引流异常枚举",enumAsRef = true)
public enum CrmPreDrainageExceptionEnum implements BaseExceptionEnum<CrmPreDrainageExceptionEnum, String>  {

     /**
     * 异常信息
     */
     ADD_NO_ARGS("crm_pre_drainage",500, null,  "客户关系活动引流参数为空"),
     ADD_NO_INVALID("crm_pre_drainage",501, null,  "客户关系活动引流参数异常"),
     ADD_EXISTS_INVALID("crm_pre_drainage",502, null,  "客户关系活动引流已经存在"),
     ADD_MODULE_INVALID("crm_pre_drainage",503, null,  "客户关系活动引流存储与模块信息不一致"),
     ADD_MODULE_TODO_FOLLOW("crm_pre_drainage",504, null,  "有新的线索信息需要联系"),
     ADD_MODULE_SYNC_DATA_NO_TOKEN("crm_pre_drainage",505, null,  "同步信息未获取正确授权"),
     ADD_MODULE_SYNC_DATA_NO_FIELD("crm_pre_drainage",506, null,  "同步信息未设置对应字段映射"),
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


    CrmPreDrainageExceptionEnum(String module, Integer code, Object[] args, String desc) {
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