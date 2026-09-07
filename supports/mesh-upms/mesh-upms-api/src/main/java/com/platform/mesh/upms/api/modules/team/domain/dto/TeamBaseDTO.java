package com.platform.mesh.upms.api.modules.team.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 团队DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队DTO")
public class TeamBaseDTO extends BaseDTO {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long id;

    /**
     * 团队名称
     */
    @Schema(description="团队名称")
    private String teamName;

    /**
     * 团队数据关系
     */
    @Schema(description="团队数据关系")
    private TeamLinkDTO linkDTO;

    /**
     * 团队成员关系
     */
    @Schema(description="团队成员关系")
    private List<TeamMemberRelDTO> memberRelDTOS;
}
