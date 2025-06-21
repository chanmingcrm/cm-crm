package com.platform.mesh.bpm.biz.data.form.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 数据表单异常枚举
 * @author 蝉鸣
 */
@Schema(description = "数据表单异常枚举",enumAsRef = true)
public enum DataFormRelExceptionEnum implements BaseExceptionEnum<DataFormRelExceptionEnum, String>  {

    /**
     * 异常信息
     */
    ADD_NO_ARGS("data_form_rel",500, null,  "数据表单参数为空"),
    ADD_NO_INVALID("data_form_rel",501, null,  "数据表单参数异常"),
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


    DataFormRelExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
