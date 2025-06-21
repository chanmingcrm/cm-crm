package com.platform.mesh.tmp.biz.modules.task.statusrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.vo.TaskStatusRelVO;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.po.TaskStatusRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务状态关系
 * @author 蝉鸣
 */
@Service
public class TaskStatusRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskStatusRel taskStatusRel 
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    public TaskStatusRelVO getStatusRelInfoById(TaskStatusRel taskStatusRel) {
        TaskStatusRelVO taskStatusRelVO = new TaskStatusRelVO();
        if(ObjectUtil.isEmpty(taskStatusRelVO)){
            return taskStatusRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskStatusRel, taskStatusRelVO);
        return taskStatusRelVO;
    }

}