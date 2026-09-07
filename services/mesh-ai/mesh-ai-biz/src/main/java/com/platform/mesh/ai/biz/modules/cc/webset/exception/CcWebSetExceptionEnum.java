package com.platform.mesh.ai.biz.modules.cc.webset.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 客服人员异常枚举
* @author 蝉鸣
*/
@Schema(description = "客服人员异常枚举",enumAsRef = true)
public enum CcWebSetExceptionEnum implements BaseExceptionEnum<CcWebSetExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("cc-user",500, null,  "人员参数为空"),
   ADD_NO_INVALID("cc-user",501, null,  "人员参数异常"),
   CONSULTATION_CONFIG_NOT_FOUND("cc-web-set", 502, null, "客服配置不存在"),
   CONSULTATION_GROUP_NOT_FOUND("cc-web-set", 503, null, "客服会话尚未建立，请稍后重试"),
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


   CcWebSetExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
