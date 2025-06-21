package com.platform.mesh.app.api.modules.init.es.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 客户关系初始化ES异常枚举
* @author 蝉鸣
*/
@Schema(description = "客户关系初始化ES异常枚举",enumAsRef = true)
public enum AppInitEsExceptionEnum implements BaseExceptionEnum<AppInitEsExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("app-init-es",500, null,  "初始化ES参数为空"),
   ADD_NO_INVALID("app-init-es",501, null,  "初始化ES参数异常"),
   ADD_NO_INDEX("app-init-es",500, null,  "没有需要初始化的索引,请检查索引是否有效或者ES是否已经存在"),
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


   AppInitEsExceptionEnum(String module, Integer code, Object[] args, String desc) {
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