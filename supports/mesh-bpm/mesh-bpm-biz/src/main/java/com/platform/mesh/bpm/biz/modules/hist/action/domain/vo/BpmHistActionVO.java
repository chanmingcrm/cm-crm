package com.platform.mesh.bpm.biz.modules.hist.action.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 动作VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="历史动作VO")
public class BpmHistActionVO extends BaseVO {

    /**
     * id
     */
    @Schema(description = "")
    private Long id;

    /**
     * 父id
     */
    @Schema(description = "")
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

    /**
     * 子组织
     */
    @Schema(description = "子组织")
    private List<BpmHistActionVO> children;
}
