package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.vo;


import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * @description 节点审批VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="流程节点信息VO")
public class BpmInstNodeAuditVO extends BaseVO {


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
    @SchemaEnum(value = NodeAuditDataTypeEnum.class, description = "审批数据类型")
    private Integer auditDataType;

    /**
     * 审批数据ID
     */
    @Schema(description = "审批数据ID")
    private Long auditDataId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
