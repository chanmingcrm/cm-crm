package com.platform.mesh.tmp.biz.modules.task.priority.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.vo.TaskPriorityVO;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.po.TaskPriority;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务优先级
 * @author 蝉鸣
 */
@Service
public class TaskPriorityServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskPriority taskPriority 
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    public TaskPriorityVO getPriorityInfoById(TaskPriority taskPriority) {
        TaskPriorityVO taskPriorityVO = new TaskPriorityVO();
        if(ObjectUtil.isEmpty(taskPriorityVO)){
            return taskPriorityVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskPriority, taskPriorityVO);
        return taskPriorityVO;
    }

}