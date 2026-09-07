package com.platform.mesh.upms.biz.modules.log.update.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 更新日志异常枚举
* @author 蝉鸣
*/
@Schema(description = "更新日志异常枚举",enumAsRef = true)
public enum LogUpdateExceptionEnum implements BaseExceptionEnum<LogUpdateExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("log-update",500, null,  "base参数为空"),
   ADD_NO_INVALID("log-update",501, null,  "base参数异常"),
   ADD_CODE_INVALID("log-update",502, null,  "版本号必须大于之前版本号"),
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


   LogUpdateExceptionEnum(String module, Integer code, Object[] args, String desc) {
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