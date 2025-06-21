package com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.po;


import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 变量值DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_varrefer")
public class BpmTempVarRefer extends BasePO {

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
     * 流程模板Hash
     */
    private String tempProcessHash;

    /**
     * 流程模板线ID
     */
    private Long tempLineId;

    /**
     * 流程模板线Hash
     */
    private String tempLineHash;

    /**
     * 流程模板变量ID
     */
    private Long tempVariableId;

    /**
     * 流程模板变量Hash
     */
    private String tempVariableHash;

    /**
     * 变量逻辑类型
     */
    private Integer variableType;
    /**
     * 变量逻辑关系
     */
    private Integer variableRef;

    /**
     * 流程实例变量参照值
     */
    private String variableRefer;

    /**
     * 流程实例变量
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

