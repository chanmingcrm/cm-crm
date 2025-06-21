package com.platform.mesh.tmp.biz.modules.task.priority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.dto.TaskPriorityDTO;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.po.TaskPriority;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.vo.TaskPriorityVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 任务优先级信息
 * @author 蝉鸣
 */
public interface ITaskPriorityService extends IService<TaskPriority> {


    /**
     * 功能描述:
     * 〈获取当前任务优先级信息〉
     * @param priorityId priorityId
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    TaskPriorityVO getPriorityInfoById(Long priorityId);

    /**
     * 功能描述:
     * 〈新增任务优先级〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    TaskPriorityVO addPriority(TaskPriorityDTO priorityDTO);

    /**
     * 功能描述:
     * 〈修改任务优先级〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    TaskPriorityVO editPriority(TaskPriorityDTO priorityDTO);

    /**
     * 功能描述:
     * 〈删除任务优先级〉
     * @param priorityId priorityId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deletePriority(Long priorityId);
}