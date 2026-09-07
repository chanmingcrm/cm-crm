package com.platform.mesh.upms.api.modules.team.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 团队成员关系BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队成员关系BO")
public class TeamMemberRelBO extends BaseBO {


    /**
     * 用户USER_ID
     */
    @Schema(description="用户USER_ID")
    private Long id;

    /**
     * 成员名称
     */
    @Schema(description="成员名称")
    private String name;

    /**
     * 成员ID
     */
    @Schema(description="成员ID")
    private Long memberId;

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

}
