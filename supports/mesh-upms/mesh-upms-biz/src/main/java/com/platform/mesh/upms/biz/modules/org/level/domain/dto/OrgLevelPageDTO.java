package com.platform.mesh.upms.biz.modules.org.level.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 组织层级列表分页查询DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="组织层级列表分页查询DTO")
public class OrgLevelPageDTO extends PageDTO {
    /**
     * id
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 根层级ID:用于公司/顶层组织类型ID
     */
    @Schema(description = "根层级ID:用于公司/顶层组织类型ID")
    private Long rootId;

    /**
     * 父id
     */
    @Schema(description = "父ID")
    private Long parentId;
    /**
     * 层级标识
     */
    @Schema(description = "层级标识")
    private Integer levelFlag;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

}
