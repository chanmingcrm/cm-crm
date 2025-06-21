package com.platform.mesh.bpm.biz.modules.temp.process.domain.vo;


import com.platform.mesh.bpm.biz.modules.temp.action.domain.vo.BpmTempActionVO;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.vo.BpmTempEventVO;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.vo.BpmTempLineVO;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.vo.BpmTempNodeVO;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.vo.BpmTempVariableVO;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.vo.BpmTempVarReferVO;
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
public class BpmTempProcessDesignVO extends BaseVO {

    @Schema(description = "流程过程")
    private BpmTempProcessVO processVO;

    /**
     * 流程节点
     */
    @Schema(description = "流程节点")
    private List<BpmTempNodeVO> nodeVOs;

    /**
     * 流程线
     */
    @Schema(description = "流程线")
    private List<BpmTempLineVO> lineVOs;

    /**
     * 流程动作
     */
    @Schema(description = "流程动作")
    private List<BpmTempActionVO> actionVOs;

    /**
     * 流程事件
     */
    @Schema(description = "流程事件")
    private List<BpmTempEventVO> eventVOs;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private List<BpmTempVariableVO> variableVOs;

    /**
     * 流程变量参照
     */
    @Schema(description = "流程变量参照")
    private List<BpmTempVarReferVO> varReferVOs;
}
