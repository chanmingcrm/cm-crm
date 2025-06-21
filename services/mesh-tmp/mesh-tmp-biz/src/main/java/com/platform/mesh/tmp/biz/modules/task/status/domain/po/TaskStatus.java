package com.platform.mesh.tmp.biz.modules.task.status.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务状态DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_status", autoResultMap = true)
public class TaskStatus extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 模块ID
    */
    private Long moduleId;


    /**
    * 数据ID
    */
    private Long dataId;


    /**
    * 状态标识
    */
    private String statusMac;


    /**
    * 状态名称
    */
    private String statusName;


    /**
    * 状态排序
    */
    private Integer statusSort;


    /**
    * 初始标识
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