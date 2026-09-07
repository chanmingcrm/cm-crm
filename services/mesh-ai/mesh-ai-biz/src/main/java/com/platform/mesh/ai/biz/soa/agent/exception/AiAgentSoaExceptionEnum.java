package com.platform.mesh.ai.biz.soa.agent.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 客服会话消息异常枚举
* @author 蝉鸣
*/
@Schema(description = "客服会话消息异常枚举",enumAsRef = true)
public enum AiAgentSoaExceptionEnum implements BaseExceptionEnum<AiAgentSoaExceptionEnum, String>  {

    /**
    * 异常信息
    */
   AI_BOT_TIMEOUT("ai-agent-soa",500, null,  "智能体响应超时"),
   AI_BOT_ERROR("ai-agent-soa",501, null,  "智能体响应错误"),
   AI_BOT_PLATFORM_NOT_SUPPORTS("ai-agent-soa",501, null,  "平台暂不支持"),
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


   AiAgentSoaExceptionEnum(String module, Integer code, Object[] args, String desc) {
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