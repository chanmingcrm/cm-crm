package com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI会话历史DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话历史DTO")
public class AiSessionHisDTO extends PageDTO {

    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private Long sessionId;
}