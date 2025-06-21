package com.platform.mesh.bpm.biz.modules.temp.process.domain.dto;


import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.dto.BpmTempVarReferDTO;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.dto.BpmTempActionDTO;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.dto.BpmTempEventDTO;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.dto.BpmTempLineDTO;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.dto.BpmTempNodeDTO;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.dto.BpmTempVariableDTO;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程过程信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程过程信息添加DTO")
public class BpmTempProcessDesignDTO extends BaseDTO {

    @Schema(description = "流程过程")
    private BpmTempProcessEditDTO processDTO;

    /**
     * 流程节点
     */
    @Schema(description = "流程节点")
    private List<BpmTempNodeDTO> nodeDTOs;

    /**
     * 流程线
     */
    @Schema(description = "流程线")
    private List<BpmTempLineDTO> lineDTOs;

    /**
     * 流程动作
     */
    @Schema(description = "流程动作")
    private List<BpmTempActionDTO> actionDTOs;

    /**
     * 流程事件
     */
    @Schema(description = "流程事件")
    private List<BpmTempEventDTO> eventDTOs;

    /**
     * 流程变量
     */
    @Schema(description = "流程变量")
    private List<BpmTempVariableDTO> variableDTOs;

    /**
     * 流程变量参照
     */
    @Schema(description = "流程变量参照")
    private List<BpmTempVarReferDTO> varReferDTOs;
}
