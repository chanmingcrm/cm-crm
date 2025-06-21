package com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
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
public class OrgLevelPostRelEditDTO extends BaseDTO {


    /**
    * 层级标识
    */
    @Schema(description="层级标识")
    private Long targetLevelId;

    /**
     * 层级标识
     */
    @Schema(description="层级标识")
    private Long originLevelId;

    /**
    * 名称
    */
    @Schema(description="名称")
    private List<Long> postIds;

}

