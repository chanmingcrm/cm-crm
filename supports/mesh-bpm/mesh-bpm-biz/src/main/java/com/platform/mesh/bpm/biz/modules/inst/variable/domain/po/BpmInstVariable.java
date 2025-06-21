package com.platform.mesh.bpm.biz.modules.inst.variable.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @description 变量DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_inst_variable")
public class BpmInstVariable extends BasePO {

    @Serial
    private static final long serialVersionUID = -51636545350904711L;
    
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 变量父ID
     */
    private Long parentId;

    /**
     * 流程模板ID
     */
    private Long tempProcessId;

    /**
     * 流程实例ID
     */
    private Long instProcessId;

    /**
     * 流程模板线ID
     */
    private Long tempLineId;

    /**
     * 流程实例线ID
     */
    private Long instLineId;

    /**
     * 流程模板变量ID
     */
    private Long tempVariableId;

    /**
     * 变量类型
     */
    private Integer variableType;

    /**
     * 变量类型
     */
    private String variableName;

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

