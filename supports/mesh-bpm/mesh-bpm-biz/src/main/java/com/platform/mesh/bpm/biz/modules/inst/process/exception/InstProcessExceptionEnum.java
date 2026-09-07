package com.platform.mesh.bpm.biz.modules.inst.process.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 流程过程信息异常枚举
 * @author 蝉鸣
 */
@Schema(description = "流程过程信息异常枚举",enumAsRef = true)
public enum InstProcessExceptionEnum implements BaseExceptionEnum<InstProcessExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("bpm-process-inst",500, null,  "流程过程实例参数为空"),
    ADD_NO_INVALID("bpm-process-inst",501, null,  "流程实例数据异常"),
    DATA_HAS_RUNNING("bpm-process-inst",502, null,  "该数据已有运行中的流程审批"),
    TEMP_NO_ARGS("bpm-process-inst",503, null,  "流程模板参数异常"),
    TEMP_NO_INVALID("bpm-process-inst",504, null,  "无效的流程模板"),
    INST_HAS_RUN("bpm-process-inst",505, null,  "流程已运行"),
    INST_HAS_CANCEL("bpm-process-inst",506, null,  "流程已无效"),
    INST_HAS_END("bpm-process-inst",507, null,  "流程已结束"),
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


    InstProcessExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
