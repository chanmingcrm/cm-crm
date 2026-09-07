package com.platform.mesh.ai.biz.chat.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * @description AI会话DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话DTO")
public class AiMsgDTO extends BaseDTO {


    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private Long sessionId;

    /**
     * 提示词
     */
    @Schema(description = "提示词")
    private String prompt;

    /**
     * 提问内容
     */
    @Schema(description = "提问内容")
    private String content;

    /**
     * 是否携带上下文记忆
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否携带上下文记忆")
    private Integer contextFlag;

    /**
     * 是否联网
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "是否联网")
    private Integer netFlag;

    /**
     * 知识库ID
     */
    @Schema(description = "知识库ID")
    private List<Long> knowledgeIds;

    /**
     * 消息信息
     */
    @Schema(description = "消息信息")
    private List<Message> messages;



}