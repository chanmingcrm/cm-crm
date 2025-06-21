package com.platform.mesh.app.api.modules.init.db.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description 应用数据对象异常枚举
* @author 蝉鸣
*/
@Schema(description = "应用数据对象异常枚举",enumAsRef = true)
public enum DbExceptionEnum implements BaseExceptionEnum<DbExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("app",500, null,  "应用数据对象参数为空"),
   ADD_NO_INVALID("app",501, null,  "应用数据对象参数异常"),
   DB_TRANS_COLUMN_INVALID("app",503, null,  "未匹配到对应字段信息"),
   DB_TRANS_COLUMN_MAPPING_INVALID("app",504, null,  "未匹配到对应字段映射信息"),
   DB_TRANS_MOVE_SCHEMA_INVALID("app",505, null,  "数据转移表存储不一致"),
   DB_TRANS_DATA_INVALID("app",506, null,  "数据转移未匹配到对应数据信息"),
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


   DbExceptionEnum(String module, Integer code, Object[] args, String desc) {
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