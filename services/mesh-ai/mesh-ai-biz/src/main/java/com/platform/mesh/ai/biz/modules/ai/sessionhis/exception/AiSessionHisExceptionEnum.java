package com.platform.mesh.ai.biz.modules.ai.sessionhis.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;



 /**
 * @description AI会话历史异常枚举
 * @author 蝉鸣
 */
@Schema(description = "AI会话历史异常枚举",enumAsRef = true)
public enum AiSessionHisExceptionEnum implements BaseExceptionEnum<AiSessionHisExceptionEnum, String>  {

     /**
     * 异常信息
     */
    ADD_NO_ARGS("ai-session-his",500, null,  "会话历史参数为空"),
    ADD_NO_INVALID("ai-session-his",501, null,  "会话历史参数异常"),
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


    AiSessionHisExceptionEnum(String module, Integer code, Object[] args, String desc) {
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