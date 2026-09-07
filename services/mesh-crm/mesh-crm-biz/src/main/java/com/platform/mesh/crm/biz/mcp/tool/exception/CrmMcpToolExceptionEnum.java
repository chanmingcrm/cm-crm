package com.platform.mesh.crm.biz.mcp.tool.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 功能描述:
 * 〈定义 CRM MCP 工具调用异常信息〉
 * @author qingfeng
 */
@Schema(description = "Mcp异常枚举",enumAsRef = true)
public enum CrmMcpToolExceptionEnum implements BaseExceptionEnum<CrmMcpToolExceptionEnum, String>  {

    /**
    * 异常信息
    */
   MCP_NO_REGISTER("crm_on_business",500, null,  "CRM MCP 工具未注册"),
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


   CrmMcpToolExceptionEnum(String module, Integer code, Object[] args, String desc) {
       this.module = module;
       this.code = code;
       this.args = args;
       this.desc = desc;
   }

   /**
    * 功能描述:
    * 〈获取异常所属模块〉
    * @return 业务处理结果
    * @author qingfeng
    */
   @Override
   public String getModule() {
       return module;
   }

   /**
    * 功能描述:
    * 〈获取异常编码〉
    * @return 业务处理结果
    * @author qingfeng
    */
   @Override
   public Integer getCode() {
       return code;
   }

   /**
    * 功能描述:
    * 〈获取异常消息格式化参数〉
    * @return 业务处理结果
    * @author qingfeng
    */
   @Override
   public Object[] getArgs() {
       return args;
   }

   /**
    * 功能描述:
    * 〈获取异常描述〉
    * @return 业务处理结果
    * @author qingfeng
    */
   @Override
   public String getDesc() {
       return desc;
   }

}