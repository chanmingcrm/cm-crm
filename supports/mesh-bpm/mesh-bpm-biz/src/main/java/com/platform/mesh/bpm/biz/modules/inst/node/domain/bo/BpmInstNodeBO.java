package com.platform.mesh.bpm.biz.modules.inst.node.domain.bo;


import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeInEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @description 实例节点BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息BO")
public class BpmInstNodeBO extends BaseBO {

    /**
     * ID
     */
    @Schema(description = "ID")
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
     * 流程实例名称
     */
    @Schema(description = "流程实例名称")
    private String instProcessName;

    /**
     * 模块ID
     */
    @Schema(description = "模块ID")
    private Long moduleId;

    /**
     * 流程模板节点ID
     */
    @Schema(description = "数据ID")
    private Long dataId;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称")
    private String dataName;

    /**
     * 流程模板节点ID
     */
    @Schema(description = "流程模板节点ID")
    private Long tempNodeId;

    /**
     * 节点名称
     */
    @Schema(description = "节点名称")
    private String nodeName;

    /**
     * 节点标识
     */
    @SchemaEnum(value = NodeTypeEnum.class, description = "节点标识")
    private Integer nodeFlag;

    /**
     * 运行标识
     */
    @SchemaEnum(value = NodeRunEnum.class, description = "运行标识")
    private Integer runFlag;

    /**
     * 进入节点标识
     */
    @SchemaEnum(value = InstNodeInEnum.class, description = "进入节点标识")
    private Integer inFlag;

    /**
     * 流出节点标识
     */
    @SchemaEnum(value = InstNodeOutEnum.class, description = "流出节点标识")
    private Integer outFlag;

    /**
     * pass标识
     */
    @SchemaEnum(value = YesOrNoEnum.class, description = "pass标识")
    private Integer passFlag;

    /**
     * 审批类型
     */
    @SchemaEnum(value = NodeAuditFlagEnum.class, description = "审批类型")
    private Integer auditFlag;

    /**
     * 审批数据类型
     */
    @SchemaEnum(value = NodeAuditDataTypeEnum.class, description = "审批数据类型")
    private Integer auditDataType;
}
