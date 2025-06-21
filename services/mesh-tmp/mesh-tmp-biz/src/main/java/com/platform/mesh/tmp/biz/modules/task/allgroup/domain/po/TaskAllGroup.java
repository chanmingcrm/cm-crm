package com.platform.mesh.tmp.biz.modules.task.allgroup.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 任务分组DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_all_group", autoResultMap = true)
public class TaskAllGroup extends BasePO {


    /**
    * ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;


    /**
    * 状态标识
    */
    private String groupMac;


    /**
    * 状态名称
    */
    private String groupName;


    /**
    * 状态排序
    */
    private Integer groupSort;


}