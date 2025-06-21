package com.platform.mesh.bpm.biz.modules.inst.process.domain.vo;


import com.platform.mesh.bpm.biz.modules.inst.action.domain.vo.BpmInstActionVO;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.vo.BpmInstEventVO;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.vo.BpmInstLineVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.vo.BpmInstVariableVO;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.vo.BpmInstVarReferVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 模板流程过程信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="模板流程过程信息VO")
public class BpmInstProcessDesignVO extends BaseVO {

    @Schema(description = "流程过程")
    private BpmInstProcessVO processVO;

    /**
     * 流程节点
     */
    @Schema(description = "流程节点")
    private List<BpmInstNodeVO> nodeVOs;

    /**
     * 流程线
     */
    @Schema(description = "流程线")
    private List<BpmInstLineVO> lineVOs;

    /**
     * 流程动作
     */
    @Schema(description = "流程动作")
    private List<BpmInstActionVO> actionVOs;

    /**
     * 流程事件
     */
    @Schema(description = "流程事件")
    private List<BpmInstEventVO> eventVOs;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private List<BpmInstVariableVO> variableVOs;

    /**
     * 流程变量参照
     */
    @Schema(description = "流程变量参照")
    private List<BpmInstVarReferVO> varReferVOs;
}
