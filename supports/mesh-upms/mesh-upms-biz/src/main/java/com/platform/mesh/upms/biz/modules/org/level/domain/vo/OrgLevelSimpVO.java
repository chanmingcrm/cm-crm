package com.platform.mesh.upms.biz.modules.org.level.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
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
public class OrgLevelSimpVO extends BaseVO {

    /**
    * 层级标识
    */
    @Schema(description = "层级标识")
    private Long id;
    /**
    * 名称
    */
    @Schema(description = "名称")
    private String levelName;
}
