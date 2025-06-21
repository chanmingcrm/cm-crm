package com.platform.mesh.upms.biz.modules.org.level.domain.vo;


import com.platform.mesh.core.application.domain.vo.TreeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 组织层级VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="组织层级VO")
public class OrgLevelVO extends TreeVO<OrgLevelVO> {


    /**
     * 根层级ID:用于公司/顶层组织类型ID
     */
    @Schema(description = "根层级ID:用于公司/顶层组织类型ID")
    private Long rootId;

    /**
    * 层级标识
    */
    @Schema(description = "层级标识")
    private Integer levelFlag;
    /**
    * 名称
    */
    @Schema(description = "名称")
    private String levelName;
}
