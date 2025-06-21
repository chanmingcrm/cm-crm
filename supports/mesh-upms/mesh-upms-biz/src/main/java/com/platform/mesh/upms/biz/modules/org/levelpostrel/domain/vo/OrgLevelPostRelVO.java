package com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo;

import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 组织层级岗位DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="组织层级岗位VO")
public class OrgLevelPostRelVO extends BaseDTO {

    /**
     * ID
     */
    @Schema(description="ID")
    private Long id;

    /**
    * 岗位ID
    */
    @Schema(description="岗位ID")
    private Long postId;

    /**
    * 岗位名称
    */
    @Schema(description="岗位名称")
    private String postName;

    /**
     * 层级ID
     */
    @Schema(description="层级ID")
    private Long levelId;

    /**
     * 层级名称
     */
    @Schema(description="层级名称")
    private String levelName;

}

