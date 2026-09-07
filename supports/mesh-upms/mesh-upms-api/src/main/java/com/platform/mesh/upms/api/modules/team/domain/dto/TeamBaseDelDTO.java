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
@Schema(description="团队删除DTO")
public class TeamBaseDelDTO extends BaseDTO {

    /**
     * 模块ID
     */
    @Schema(description="模块ID")
    private Long moduleId;

    /**
     * 数据ID
     */
    @Schema(description="数据ID")
    private Long dataId;

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long teamId;

    /**
     * 团队成员Id
     */
    @Schema(description="团队成员Id")
    private List<Long> memberIds;
}
