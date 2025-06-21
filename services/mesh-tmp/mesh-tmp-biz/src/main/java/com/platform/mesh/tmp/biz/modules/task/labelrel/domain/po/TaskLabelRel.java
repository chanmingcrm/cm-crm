package com.platform.mesh.tmp.biz.modules.task.labelrel.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务标签关系DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_label_rel", autoResultMap = true)
public class TaskLabelRel extends BasePO {


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


}