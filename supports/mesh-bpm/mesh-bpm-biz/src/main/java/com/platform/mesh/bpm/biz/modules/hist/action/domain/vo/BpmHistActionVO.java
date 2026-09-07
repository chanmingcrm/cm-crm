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
    @Schema(description = "id")
    private Long id;

    /**
     * 流程模板ID
     */
    @Schema(description = "流程模板ID")
    private Long tempProcessId;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private Long instProcessId;

    /**
     * 流程模板节点ID
     */
    @Schema(description = "流程模板节点ID")
    private Long tempNodeId;

    /**
     * 流程实例节点ID
     */
    @Schema(description = "流程实例节点ID")
    private Long instNodeId;

    /**
     * 流程模板动作ID
     */
    @Schema(description = "流程模板动作ID")
    private Long tempActionId;

    /**
     * 动作类型
     */
    @Schema(description = "动作类型")
    private Integer actionType;
}
