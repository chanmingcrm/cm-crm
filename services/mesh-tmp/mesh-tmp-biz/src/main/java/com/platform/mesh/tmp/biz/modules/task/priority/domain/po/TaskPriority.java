package com.platform.mesh.tmp.biz.modules.task.priority.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务优先级DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_priority", autoResultMap = true)
public class TaskPriority extends BasePO {


    /**
    * 任务ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 优先级标识
    */
    private String priorityMac;


    /**
    * 优先级名称
    */
    private String priorityName;


    /**
    * 优先级排序
    */
    private Integer prioritySort;


    /**
    * 优先级状态PriorityFlagEnum
    */
    private Integer priorityFlag;


    /**
    * 优先级初始状态
    */
    private Integer iniFlag;


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