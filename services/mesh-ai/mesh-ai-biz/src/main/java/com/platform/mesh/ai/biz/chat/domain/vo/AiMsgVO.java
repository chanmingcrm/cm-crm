package com.platform.mesh.ai.biz.chat.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI会话VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话VO")
public class AiMsgVO extends BaseVO {


    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private String sessionId;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private Long msgId;

    /**
     * 提问内容
     */
    @Schema(description = "提问内容")
    private String content;

}