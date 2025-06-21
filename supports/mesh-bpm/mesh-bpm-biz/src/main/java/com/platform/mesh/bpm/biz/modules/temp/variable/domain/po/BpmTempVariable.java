package com.platform.mesh.bpm.biz.modules.temp.variable.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 变量DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_temp_variable")
public class BpmTempVariable extends BaseVO {

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
     * 变量Hash
     */
    private String variableHash;

    /**
     * 变量类型
     */
    private String variableName;

    /**
     * 变量类型
     */
    private Integer variableType;

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

