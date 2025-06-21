package com.platform.mesh.upms.biz.sms.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 短信异常枚举
* @author 蝉鸣
*/
@Schema(description = "短信异常枚举",enumAsRef = true)
public enum SmsExceptionEnum implements BaseExceptionEnum<SmsExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("uaa-sms",500, null,  "手机号码参数为空"),
   ADD_NO_INVALID("uaa-sms",501, null,  "手机号码参数异常"),
   ADD_HAS_EXISTS("uaa-sms",502, null,  "已发送验证码仍在有效期!!!"),
   ADD_NO_SERVER("uaa-sms",503, null,  "短信发送异常!!!"),
   ADD_LIMIT_SERVER("uaa-sms",504, null,  "短信发送繁忙,请稍后重试!!!"),
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


   SmsExceptionEnum(String module, Integer code, Object[] args, String desc) {
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