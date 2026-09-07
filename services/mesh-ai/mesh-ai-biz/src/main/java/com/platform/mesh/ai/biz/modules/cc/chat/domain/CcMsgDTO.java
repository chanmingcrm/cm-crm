package com.platform.mesh.ai.biz.modules.cc.chat.domain;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 客服消息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="客服消息DTO")
public class CcMsgDTO extends BaseDTO {

    /**
     * 发送路由
     */
    @Schema(description = "发送路由")
    private String fromRouter;

    /**
     * 目标路由
     */
    @Schema(description = "目标路由")
    private String toRouter;

    /**
     * 消息
     */
    @Schema(description = "消息")
    private String content;

    /**
     * 用户Hash
     */
    @Schema(description = "用户Hash")
    private String userHash;

}
