package com.platform.mesh.bpm.biz.modules.hist.varvalue.domain.vo;


import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 变量值VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="变量值VO")
public class BpmHistVarValueVO extends BaseVO {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private Long instProcessId;

    /**
     * 流程实例节点ID
     */
    @Schema(description = "流程实例节点ID")
    private Long instNodeId;

    /**
     * 流程实例变量名称
     */
    @Schema(description = "流程实例变量名称")
    private String variableName;

    /**
     * 流程实例变量值
     */
    @Schema(description = "流程实例变量值")
    private String variableValue;
}
