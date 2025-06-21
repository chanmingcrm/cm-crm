package com.platform.mesh.es.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description Elasticsearch异常枚举
* @author 蝉鸣
*/
@Schema(description = "Elasticsearch异常枚举",enumAsRef = true)
public enum EsExceptionEnum implements BaseExceptionEnum<EsExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ES_INDEX_CREATE("es_index_create",500, null,  "Elasticsearch 创建索引异常"),
   ES_INDEX_DELETE("es_index_delete",501, null,  "Elasticsearch 删除索引异常"),
   ES_DOC_CREATE("es_doc_create",502, null,  "Elasticsearch 创建文档异常"),
   ES_DOC_UPDATE("es_doc_update",503, null,  "Elasticsearch 修改文档异常"),
   ES_DOC_DELETE("es_doc_delete",504, null,  "Elasticsearch 删除文档异常"),
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


   EsExceptionEnum(String module, Integer code, Object[] args, String desc) {
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