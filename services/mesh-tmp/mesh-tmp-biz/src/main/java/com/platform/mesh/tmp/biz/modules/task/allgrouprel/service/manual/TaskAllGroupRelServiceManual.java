package com.platform.mesh.tmp.biz.modules.task.allgrouprel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.vo.TaskAllGroupRelVO;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.po.TaskAllGroupRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务分组关系
 * @author 蝉鸣
 */
@Service
public class TaskAllGroupRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskAllGroupRel taskAllGroupRel 
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    public TaskAllGroupRelVO getAllGroupRelInfoById(TaskAllGroupRel taskAllGroupRel) {
        TaskAllGroupRelVO taskAllGroupRelVO = new TaskAllGroupRelVO();
        if(ObjectUtil.isEmpty(taskAllGroupRelVO)){
            return taskAllGroupRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskAllGroupRel, taskAllGroupRelVO);
        return taskAllGroupRelVO;
    }

}