package com.platform.mesh.bpm.biz.modules.inst.line.domain.po;



import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 流程线信息
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_inst_line")
public class BpmInstLine extends BasePO {

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
     * 流程模板线入节点ID
     */
    private Long tempInNodeId;

    /**
     * 流程模板线出节点ID
     */
    private Long tempOutNodeId;

    /**
     * 线模板ID
     */
    private Long tempLineId;

    /**
     * 流程实例线入节点ID
     */
    private Long instInNodeId;

    /**
     * 流程实例线出节点ID
     */
    private Long instOutNodeId;

    /**
     * 流程实例入线Pass
     */
    private Integer instInLinePass;

    /**
     * 流程实例出线Pass
     */
    private Integer instOutLinePass;

    /**
     * 线类型
     */
    private Integer lineType;

    /**
     * 线图标
     */
    private String lineSvg;

    /**
     * 线主题
     */
    private String lineTheme;

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

