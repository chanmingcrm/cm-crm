package com.platform.mesh.upms.biz.modules.team.base.domain.vo;


import com.platform.mesh.core.application.domain.vo.TreeVO;
import com.platform.mesh.core.enums.data.DataFlagEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 团队VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="团队VO")
public class TeamBaseVO extends TreeVO<TeamBaseVO> {

    /**
     * 团队ID
     */
    @Schema(description="团队ID")
    private Long id;

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
     * 数据标识
     */
    @SchemaEnum(value = DataFlagEnum.class, description="数据标识")
    private Long dataFlag;

    /**
     * 团队名称
     */
    @Schema(description="团队名称")
    private String teamName;

}
