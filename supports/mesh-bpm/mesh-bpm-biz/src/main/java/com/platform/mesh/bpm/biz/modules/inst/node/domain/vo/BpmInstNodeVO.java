package com.platform.mesh.bpm.biz.modules.inst.node.domain.vo;


import com.platform.mesh.bpm.biz.modules.data.noderel.domain.vo.BpmDataFormNodeRelVO;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeInEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
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
@Schema(description="流程节点信息VO")
public class BpmInstNodeVO extends BaseVO {

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
     * 节点图标
     */
    @Schema(description = "节点图标")
    private String nodeSvg;

    /**
     * 节点主题
     */
    @Schema(description = "节点主题")
    private String nodeTheme;

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

    /**
     * 审批数据ID
     */
    @Schema(description = "审批数据ID")
    private List<Long> auditDataIds;

    /**
     * 子节点运行标识
     */
    @SchemaEnum(value = NodeRunEnum.class, description = "子节点运行标识")
    private Integer childRunFlag;

    /**
     * 子节点pass标识
     */
    @SchemaEnum(value = NodePassEnum.class, description = "子节点pass标识")
    private Integer childPassFlag;

    /**
     * 流程表单关系
     */
    @Schema(description = "流程表单关系")
    private BpmDataFormNodeRelVO nodeFormRelVO;

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private List<BpmInstProcessDesignVO> processDesignVOs;
}
