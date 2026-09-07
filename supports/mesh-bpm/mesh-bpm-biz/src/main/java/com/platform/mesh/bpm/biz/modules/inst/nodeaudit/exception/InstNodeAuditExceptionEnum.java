package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 流程节点审批信息异常枚举
 * @author 蝉鸣
 */
@Schema(description = "流程节点审批信息异常枚举",enumAsRef = true)
public enum InstNodeAuditExceptionEnum implements BaseExceptionEnum<InstNodeAuditExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("bpm-node-audit",500, null,  "审批节点审核人参数为空"),
    ADD_NO_INVALID("bpm-node-audit",501, null,  "审批节点审核人参数异常"),
    ADD_NO_INVALID_INST_NODE("bpm-node-audit",502, null,  "未找到有效的流程节点信息"),
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


    InstNodeAuditExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
