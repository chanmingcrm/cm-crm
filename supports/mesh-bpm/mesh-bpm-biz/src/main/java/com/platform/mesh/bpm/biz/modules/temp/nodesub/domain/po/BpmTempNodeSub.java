package com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @description 流程节点子项信息DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_node_sub")
public class BpmTempNodeSub extends BasePO {

    @Serial
    private static final long serialVersionUID = -51636545350904711L;
    
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程模板Hash
     */
    private String tempProcessHash;

    /**
     * 节点模板ID
     */
    private Long tempNodeId;

    /**
     * 节点模板Hash
     */
    private String tempNodeHash;

    /**
     * 节点标识
     */
    private Integer nodeFlag;

    /**
     * 节点子模板流程ID
     */
    private Long tempChildProcessId;

    /**
     * 节点子模板流程Hash
     */
    private String tempChildProcessHash;

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

