package com.platform.mesh.upms.biz.modules.team.memberrel.domain.vo;


import com.platform.mesh.core.application.domain.vo.TreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 团队成员关系VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队VO")
public class TeamMemberRelVO extends TreeVO<TeamMemberRelVO> {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long teamId;

    /**
     * 团队名称
     */
    @Schema(description="团队名称")
    private String teamName;

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
     * 角色名称
     */
    @Schema(description="角色名称")
    private String roleName;


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
     * 创建时间
     */
    @Schema(description="创建时间")
    private LocalDateTime createTime;

}
