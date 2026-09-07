package com.platform.mesh.upms.api.modules.team.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 团队成员关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队DTO")
public class TeamMemberRelDTO extends BaseDTO {


    /**
     * 人员ID
     */
    @Schema(description="人员ID")
    private Long userId;

    /**
     * 成员ID
     */
    @Schema(description="成员ID")
    private Long memberId;

    /**
     * 成员名称
     */
    @Schema(description="成员名称")
    private String memberName;


    /**
     * 开始时间
     */
    @Schema(description="开始时间")
    private LocalDateTime startTime;


    /**
     * 结束时间
     */
    @Schema(description="结束时间")
    private LocalDateTime endTime;


    /**
     * 角色ID
     */
    @Schema(description="角色ID")
    private List<Long> roleIds;

}
