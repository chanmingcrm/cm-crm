package com.platform.mesh.ai.biz.modules.ai.prompttemp.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description AI提示词模板异常枚举
 * @author 蝉鸣
 */
@Schema(description = "AI提示词模板异常枚举",enumAsRef = true)
public enum AiPromptTempExceptionEnum implements BaseExceptionEnum<AiPromptTempExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("ai-prompt-temp",500, null,  "提示词模板参数为空"),
    ADD_NO_INVALID("ai-prompt-temp",501, null,  "提示词模板参数异常"),
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


    AiPromptTempExceptionEnum(String module, Integer code, Object[] args, String desc) {
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