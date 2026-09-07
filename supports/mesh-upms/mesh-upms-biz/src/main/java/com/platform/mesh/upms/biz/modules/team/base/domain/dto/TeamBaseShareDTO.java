package com.platform.mesh.upms.biz.modules.team.base.domain.dto;

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
@Schema(description="团队数据关系DTO")
public class TeamBaseShareDTO extends BaseDTO {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long teamId;

    /**
     * 团队数据关系
     */
    @Schema(description="团队数据关系")
    private List<Long> moduleIds;
}
