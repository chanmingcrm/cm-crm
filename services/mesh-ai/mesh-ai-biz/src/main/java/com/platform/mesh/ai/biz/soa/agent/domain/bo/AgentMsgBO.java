package com.platform.mesh.ai.biz.soa.agent.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
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
@Schema(description ="AI智能体会话BO")
public class AgentMsgBO extends BaseBO {


    /**
     * 消息
     */
    @Schema(description = "消息")
    private List<Message> messages;

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
     * 用户Hash
     */
    @Schema(description = "用户Hash")
    private String userHash;

}