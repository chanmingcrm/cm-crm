package com.platform.mesh.ai.biz.modules.ai.session.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.constants.NumberConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description AI会话DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话DTO")
public class AiSessionPageDTO extends PageDTO {

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private Long agentId = NumberConst.NUM_0.longValue();

}