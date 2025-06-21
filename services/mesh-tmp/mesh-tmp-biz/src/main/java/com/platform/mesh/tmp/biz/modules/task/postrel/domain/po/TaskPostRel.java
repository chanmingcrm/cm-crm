package com.platform.mesh.tmp.biz.modules.task.postrel.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务工职关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_post_rel", autoResultMap = true)
public class TaskPostRel extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 任务ID
    */
    private Long taskId;


    /**
    * 人员ID
    */
    private Long userId;


    /**
    * 岗位ID
    */
    private Long postId;


    /**
    * 岗位标识
    */
    private String postMac;


    /**
    * 岗位名称
    */
    private String postName;


    /**
    * 岗位排序
    */
    private Integer postSort;


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