package com.platform.mesh.netty.server.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description AI客服流式消息数据
 * @author Codex
 */
@Data
@Accessors(chain = true)
@Schema(description = "AI客服流式消息数据")
public class CcAiStreamVO {

    @Schema(description = "流ID")
    private String streamId;

    @Schema(description = "分段序号")
    private Integer sequence;

    @Schema(description = "文本分段")
    private String delta;

    @Schema(description = "结束状态")
    private String status;

    @Schema(description = "状态提示")
    private String message;
}
