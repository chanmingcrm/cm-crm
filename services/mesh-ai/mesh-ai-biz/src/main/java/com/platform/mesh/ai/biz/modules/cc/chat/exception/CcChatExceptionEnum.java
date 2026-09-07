package com.platform.mesh.ai.biz.modules.cc.chat.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 会话聊天信息异常枚举
* @author 蝉鸣
*/
@Schema(description = "会话群信息异常枚举",enumAsRef = true)
public enum CcChatExceptionEnum implements BaseExceptionEnum<CcChatExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("cc-group",500, null,  "会话群参数为空"),
   ADD_NO_INVALID("cc-group",501, null,  "会话群参数异常"),
   AI_UNAVAILABLE_MESSAGE("cc-chat",502, null,  "AI客服暂时无法回复，请稍后再试或联系人工客服。"),
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


   CcChatExceptionEnum(String module, Integer code, Object[] args, String desc) {
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