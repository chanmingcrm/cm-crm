package com.platform.mesh.ai.biz.chat.exception;

import com.platform.mesh.core.enums.base.BaseExceptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
* @description AI对话异常枚举
* @author 蝉鸣
*/
@Schema(description = "AI对话异常枚举",enumAsRef = true)
public enum AiChatExceptionEnum implements BaseExceptionEnum<AiChatExceptionEnum, String>  {

    /**
    * 异常信息
    */
   ADD_NO_ARGS("ai-chat",500, null,  "知识库参数为空"),
   ADD_NO_INVALID("ai-chat",501, null,  "知识库参数异常"),
   ADD_NO_SESSION("ai-chat",502, null,  "未找到有效会话"),
   ADD_NO_MODEL("ai-chat",502, null,  "未找到有效模型"),
   ADD_NO_MODEL_SERVICE("ai-chat",502, null,  "未找到有效模型服务"),
   ADD_NO_MODEL_CHAT("ai-chat",502, null,  "未找到有效模型实例"),
   OCR_FILE_INFO_ERROR("ai-chat",503, null, "获取文件信息失败"),
   OCR_FILE_NOT_FOUND("ai-chat",504, null, "部分文件不存在或不可访问"),
   OCR_FILE_NO_PERMISSION("ai-chat",505, null, "无权访问文件"),
   OCR_FILE_TYPE_NOT_SUPPORTED("ai-chat",506, null, "不支持的文件类型"),
   OCR_DOCUMENT_EMPTY("ai-chat",507, null, "文档中未解析到文字"),
   OCR_FILE_ID_INVALID("ai-chat",508, null, "文件ID不能为空或重复"),
   OCR_FILE_COUNT_INVALID("ai-chat",509, null, "文件数量不符合要求"),
   OCR_FILE_URL_INVALID("ai-chat",510, null, "文件地址无效"),
   OCR_FILE_TOO_LARGE("ai-chat",511, null, "文件超过大小限制"),
   OCR_FILE_READ_ERROR("ai-chat",512, null, "读取文件失败"),
   OCR_FILE_MIME_INVALID("ai-chat",513, null, "文件扩展名与内容类型不一致"),
   OCR_RESULT_FORMAT_INVALID("ai-chat",514, null, "OCR返回结果不是有效JSON"),
   CHAT_NO_CONTENT("ai-chat",515, null, "AI未返回有效内容，请稍后重试"),
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


   AiChatExceptionEnum(String module, Integer code, Object[] args, String desc) {
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
