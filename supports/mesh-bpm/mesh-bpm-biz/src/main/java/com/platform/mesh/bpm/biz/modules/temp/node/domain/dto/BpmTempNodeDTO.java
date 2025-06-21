package com.platform.mesh.bpm.biz.modules.temp.node.domain.dto;


import com.platform.mesh.bpm.biz.data.noderel.domain.dto.BpmDataFormNodeRelDTO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.core.application.domain.dto.BaseDTO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @description 流程节点信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息DTO")
public class BpmTempNodeDTO extends BaseDTO {

    /**
     * 流程模板Id
     */
    @Schema(description = "流程模板Id")
    private Long tempProcessId;

    /**
     * 节点Id
     */
    @Schema(description = "节点Id")
    private Long nodeId;

    /**
     * 节点Hash
     */
    @Schema(description = "节点Hash")
    private String nodeHash;

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
     * 节点x坐标
     */
    @Schema(description = "节点Hash")
    private Integer nodeX;

    /**
     * 节点y坐标
     */
    @Schema(description = "节点Hash")
    private Integer nodeY;

    /**
     * 节点宽度
     */
    @Schema(description = "节点Hash")
    private Integer nodeWidth;

    /**
     * 节点高度
     */
    @Schema(description = "节点Hash")
    private Integer nodeHeight;

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
     * 文本x坐标
     */
    @Schema(description = "文本x坐标")
    private Integer textX;

    /**
     * 文本y坐标
     */
    @Schema(description = "文本y坐标")
    private Integer textY;

    /**
     * 节点文本描述
     */
    @Schema(description = "节点文本描述")
    private String textDesc;

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
    private String auditDataIds;

    /**
     * 流程表单关系
     */
    @Schema(description = "流程表单关系")
    private BpmDataFormNodeRelDTO nodeFormRelDTO;

    /**
     * 流程子项
     */
    @Schema(description = "流程子项")
    private List<BpmTempProcessDesignDTO> processAddDTOs;

}
