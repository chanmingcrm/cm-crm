package com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 组织层级岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="组织层级岗位DTO")
public class OrgLevelPostRelAddDTO extends BaseDTO {

    /**
     * 层级根ID
     */
    @Schema(description="层级根ID")
    private Long rootId;

    /**
     * 层级ID
     */
    @Schema(description="层级ID")
    private Long levelId;

    /**
    * 名称
    */
    @Schema(description="名称")
    private List<Long> postIds;

    /**
     * 决策岗位
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "决策岗位")
    private Integer leadFlag = YesOrNoEnum.NO.getValue();

}

