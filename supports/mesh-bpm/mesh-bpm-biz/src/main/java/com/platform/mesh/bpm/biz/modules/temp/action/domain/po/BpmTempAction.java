package com.platform.mesh.bpm.biz.modules.temp.action.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 动作DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_action")
public class BpmTempAction extends BasePO {

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
     * 流程模板节点ID
     */
    private Long tempNodeId;

    /**
     * 流程模板节点Hash
     */
    private String tempNodeHash;

    /**
     * 动作类型
     */
    private Integer actionType;

    /**
     * 动作Hash
     */
    private String actionHash;

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

