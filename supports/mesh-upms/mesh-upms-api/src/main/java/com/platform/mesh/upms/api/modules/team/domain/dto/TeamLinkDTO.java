package com.platform.mesh.upms.api.modules.team.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 团队数据关系信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队数据关系DTO")
public class TeamLinkDTO extends BaseDTO {

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

}
