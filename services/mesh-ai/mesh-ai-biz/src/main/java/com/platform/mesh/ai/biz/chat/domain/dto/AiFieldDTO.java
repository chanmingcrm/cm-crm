package com.platform.mesh.ai.biz.chat.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description AI会话DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="AI会话DTO")
public class AiFieldDTO extends AiAppDTO {


    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private Long agentId;

    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    private List<Long> fileIds;

}
