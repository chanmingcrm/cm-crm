package com.platform.mesh.upms.biz.modules.team.base.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 团队DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队分页查询DTO")
public class TeamBasePageDTO extends PageDTO {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long teamId;

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
     * 开放标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description="开放标识")
    private Integer openFlag;

    /**
     * 人员ID
     */
    @Schema(description="人员ID",hidden = true)
    private Long userId;

}
