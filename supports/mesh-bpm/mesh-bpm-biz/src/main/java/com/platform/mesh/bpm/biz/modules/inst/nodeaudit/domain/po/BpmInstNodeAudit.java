package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 流程节点审批信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_inst_node_audit")
public class BpmInstNodeAudit extends BasePO {

    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程实例ID
     */
    private Long instProcessId;

    /**
     * 节点模板ID
     */
    private Long tempNodeId;

    /**
     * 节点实例ID
     */
    private Long instNodeId;

    /**
     * 节点标识
     */
    private Integer nodeFlag;

    /**
     * 审批Pass标识
     */
    private Integer auditPass;

    /**
     * 审批类型
     */
    private Integer auditFlag;

    /**
     * 审批顺序
     */
    private Integer auditOrder;

    /**
     * 审批数据类型
     */
    private Integer auditDataType;

    /**
     * 审批数据ID
     */
    private Long auditDataId;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}

