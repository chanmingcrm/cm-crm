package com.platform.mesh.netty.server.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description Netty对话异常枚举
* @author 蝉鸣
*/
@Schema(description = "Netty对话异常枚举",enumAsRef = true)
public enum NettyExceptionEnum implements BaseExceptionEnum<NettyExceptionEnum, String>  {

    /**
    * 异常信息
    */
   NO_ARGS("start-netty",500, null,  "Netty参数为空"),
   NO_INVALID("start-netty",501, null,  "Netty参数异常"),
   CONNECT_ERROR("start-netty",501, null,  "连接异常"),
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


   NettyExceptionEnum(String module, Integer code, Object[] args, String desc) {
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