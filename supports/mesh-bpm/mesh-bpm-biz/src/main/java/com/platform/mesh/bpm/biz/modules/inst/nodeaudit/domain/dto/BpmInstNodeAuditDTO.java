package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto;


import com.platform.mesh.core.application.domain.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 流程节点审批信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息DTO")
public class BpmInstNodeAuditDTO extends BaseDTO {

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
     * 节点模板ID
     */
    @Schema(description = "节点模板ID")
    private Long tempNodeId;

    /**
     * 节点实例ID
     */
    @Schema(description = "节点实例ID")
    private Long instNodeId;

    /**
     * 节点标识
     */
    @Schema(description = "节点标识")
    private Integer nodeFlag;

    /**
     * 审批Pass标识
     */
    @Schema(description = "审批Pass标识")
    private Integer auditPass;

    /**
     * 审批类型
     */
    @Schema(description = "审批类型")
    private Integer auditFlag;

    /**
     * 审批顺序
     */
    @Schema(description = "审批顺序")
    private Integer auditOrder;

    /**
     * 审批数据类型
     */
    @Schema(description = "审批数据类型")
    private Integer auditDataType;

    /**
     * 审批数据ID
     */
    @Schema(description = "审批数据ID")
    private Long auditDataId;
}

