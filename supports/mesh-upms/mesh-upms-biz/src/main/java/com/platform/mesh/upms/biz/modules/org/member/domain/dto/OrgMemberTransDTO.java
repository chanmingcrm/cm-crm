package com.platform.mesh.upms.biz.modules.org.member.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 转移DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转移DTO")
public class OrgMemberTransDTO extends BaseDTO {

    /**
     * 来源人员ID
     */
    @Schema(description = "来源人员ID")
    private Long sourceUserId;

    /**
     * 目标ID
     */
    @Schema(description = "目标人员ID")
    private Long targetUserId;

    /**
     * 目标名称
     */
    @Schema(description = "目标名称")
    private String targetMemberName;

    /**
     * 目标组织ID
     */
    @Schema(description = "目标组织ID")
    private Long targetLevelId;

    /**
     * 目标组织名称
     */
    @Schema(description = "目标组织名称")
    private String targetLevelName;
}
