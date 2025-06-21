package com.platform.mesh.tmp.biz.modules.task.base.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 任务DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "task_base", autoResultMap = true)
public class TaskBase extends AppPO {


    /**
     * 父任务ID
     */
    private Long parentId;
    
    
    /**
    * 预计开始时间
    */
    private LocalDateTime estStartTime;
    
    
    /**
    * 预计结束时间
    */
    private LocalDateTime estEndTime;


    /**
    * 实际开始时间
    */
    private LocalDateTime actStartTime;


    /**
    * 实际结束时间
    */
    private LocalDateTime actEndTime;

}