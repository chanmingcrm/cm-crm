package com.platform.mesh.app.api.modules.app.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 应用数据对象异常枚举
* @author 蝉鸣
*/
@Schema(description = "应用数据对象异常枚举",enumAsRef = true)
public enum AppExceptionEnum implements BaseExceptionEnum<AppExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("app",500, null,  "应用数据对象参数为空"),
   ADD_NO_INVALID("app",501, null,  "应用数据对象参数异常"),
   ADD_EXISTS_INVALID("app",502, null,  "应用数据对象已经存在"),
   ADD_MODULE_INVALID("app",503, null,  "应用数据对象存储与模块信息不一致"),
   ADD_NO_ORG_SCOPE_INVALID("app",504, null,  "应用数据对象没有有效组织信息"),
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


   AppExceptionEnum(String module, Integer code, Object[] args, String desc) {
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