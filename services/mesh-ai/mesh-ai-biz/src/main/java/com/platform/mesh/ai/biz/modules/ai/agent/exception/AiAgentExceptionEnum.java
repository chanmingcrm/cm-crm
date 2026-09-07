package com.platform.mesh.ai.biz.modules.ai.agent.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description AIAgent异常枚举
 * @author 蝉鸣
 */
@Schema(description = "AIAgent异常枚举",enumAsRef = true)
public enum AiAgentExceptionEnum implements BaseExceptionEnum<AiAgentExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("ai-agent",500, null,  "Agent参数为空"),
    ADD_NO_INVALID("ai-agent",501, null,  "Agent参数异常"),
    ADD_NO_INIT("ai-agent",502, null,  "请先通过密钥绑定创建智能体"),
    ADD_NO_PROMPT("ai-agent",503, null,  "无效的智能体提示词"),
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


    AiAgentExceptionEnum(String module, Integer code, Object[] args, String desc) {
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