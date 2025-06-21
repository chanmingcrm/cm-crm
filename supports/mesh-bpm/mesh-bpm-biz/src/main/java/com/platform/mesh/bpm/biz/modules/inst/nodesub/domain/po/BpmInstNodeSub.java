package com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po;



import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 流程节点子项信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_inst_node_sub")
public class BpmInstNodeSub extends BasePO {

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
     * 初始化标识
     */
    private Integer initFlag;

    /**
     * 节点子模板流程ID
     */
    private Long tempChildProcessId;

    /**
     * 节点子实例流程ID
     */
    private Long instChildProcessId;

    /**
     * 子流程运行标识
     */
    private Integer childProcessRunFlag;

    /**
     * 子流程通过标识
     */
    private Integer childProcessPassFlag;

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

