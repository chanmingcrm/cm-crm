package com.platform.mesh.ai.biz.modules.ai.model.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description AI模型异常枚举
 * @author 蝉鸣
 */
@Schema(description = "AI模型异常枚举",enumAsRef = true)
public enum AiModelExceptionEnum implements BaseExceptionEnum<AiModelExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("ai-model",500, null,  "模型参数为空"),
    ADD_NO_INVALID("ai-model",501, null,  "模型参数异常"),
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


    AiModelExceptionEnum(String module, Integer code, Object[] args, String desc) {
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