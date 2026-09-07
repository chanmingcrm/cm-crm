package com.platform.mesh.ai.biz.job.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description AI对话异常枚举
* @author 蝉鸣
*/
@Schema(description = "AI生成异常枚举",enumAsRef = true)
public enum AiJobExceptionEnum implements BaseExceptionEnum<AiJobExceptionEnum, String>  {

    /**
    * 异常信息
    */
   JOB_GEN_ERROR("ai-gen-article",500, null,  "生成文章失败"),
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


   AiJobExceptionEnum(String module, Integer code, Object[] args, String desc) {
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