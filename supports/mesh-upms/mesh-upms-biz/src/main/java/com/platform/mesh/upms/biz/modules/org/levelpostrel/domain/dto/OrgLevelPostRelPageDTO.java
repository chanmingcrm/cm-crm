package com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
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
@Schema(description="组织层级岗位DTO")
public class OrgLevelPostRelPageDTO extends PageDTO {


    /**
    * 层级ID
    */
    @Schema(description="层级ID")
    private Long levelId;

    /**
    * 岗位ID
    */
    @Schema(description="岗位ID")
    private Long postId;

}

