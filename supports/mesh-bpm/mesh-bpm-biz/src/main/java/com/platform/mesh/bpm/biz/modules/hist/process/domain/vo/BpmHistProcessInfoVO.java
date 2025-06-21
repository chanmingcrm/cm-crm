package com.platform.mesh.bpm.biz.modules.hist.process.domain.vo;


import com.platform.mesh.bpm.biz.modules.hist.action.domain.vo.BpmHistActionVO;
import com.platform.mesh.bpm.biz.modules.hist.event.domain.vo.BpmHistEventVO;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import com.platform.mesh.bpm.biz.modules.hist.varvalue.domain.vo.BpmHistVarValueVO;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 历史流程过程信息VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="历史流程过程信息VO")
public class BpmHistProcessInfoVO extends BaseVO {

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private Long instProcessId;

    /**
     * 流程节点
     */
    @Schema(description = "流程节点")
    private List<BpmHistNodeVO> nodeVOs;

    /**
     * 流程动作
     */
    @Schema(description = "流程动作")
    private List<BpmHistActionVO> actionVOs;

    /**
     * 流程事件
     */
    @Schema(description = "流程事件")
    private List<BpmHistEventVO> eventVOs;

    /**
     * 流程变量值
     */
    @Schema(description = "流程变量值")
    private List<BpmHistVarValueVO> varValueVOs;
}
