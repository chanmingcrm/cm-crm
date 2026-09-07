package com.platform.mesh.upms.api.modules.org.member.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 转移BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="转移BO")
public class OrgMemberTransBO extends BaseBO {

    /**
     * 来源ID
     */
    @Schema(description = "来源ID")
    private Long sourceUserId;

    /**
     * 目标用户ID
     */
    @Schema(description = "目标用户ID")
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
