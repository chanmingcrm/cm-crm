package com.platform.mesh.bpm.biz.modules.inst.node.domain.vo;


import com.platform.mesh.bpm.biz.modules.inst.variable.domain.vo.BpmInstVariableVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description BpmInstNodeVarVO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点变量信息VO")
public class BpmInstNodeLineVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 节点线上所需变量
     */
    @Schema(description = "节点线上所需变量")
    private List<BpmInstVariableVO> nodeLineVarVOS;
}
