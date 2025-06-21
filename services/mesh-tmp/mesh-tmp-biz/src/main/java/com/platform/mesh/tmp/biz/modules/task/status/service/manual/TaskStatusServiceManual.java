package com.platform.mesh.tmp.biz.modules.task.status.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.status.domain.vo.TaskStatusVO;
import com.platform.mesh.tmp.biz.modules.task.status.domain.po.TaskStatus;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务状态
 * @author 蝉鸣
 */
@Service
public class TaskStatusServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskStatus taskStatus 
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    public TaskStatusVO getStatusInfoById(TaskStatus taskStatus) {
        TaskStatusVO taskStatusVO = new TaskStatusVO();
        if(ObjectUtil.isEmpty(taskStatusVO)){
            return taskStatusVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskStatus, taskStatusVO);
        return taskStatusVO;
    }

}