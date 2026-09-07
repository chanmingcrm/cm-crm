package com.platform.mesh.upms.biz.soa.org.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 第三方组织架构对接
* @author 蝉鸣
*/
@Schema(description = "第三方组织架构对接",enumAsRef = true)
public enum ThirdOrgExceptionEnum implements BaseExceptionEnum<ThirdOrgExceptionEnum, String>  {

    /**
    * 异常信息
    */
   AI_BOT_TIMEOUT("upms-third-org",500, null,  "第三方组织架构对接"),
   AI_BOT_ERROR("upms-third-org",501, null,  "第三方组织架构对接"),
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


   ThirdOrgExceptionEnum(String module, Integer code, Object[] args, String desc) {
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