package com.platform.mesh.tmp.biz.modules.task.allgroup.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.vo.TaskAllGroupVO;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.po.TaskAllGroup;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务分组
 * @author 蝉鸣
 */
@Service
public class TaskAllGroupServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskAllGroup taskAllGroup 
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    public TaskAllGroupVO getAllGroupInfoById(TaskAllGroup taskAllGroup) {
        TaskAllGroupVO taskAllGroupVO = new TaskAllGroupVO();
        if(ObjectUtil.isEmpty(taskAllGroupVO)){
            return taskAllGroupVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskAllGroup, taskAllGroupVO);
        return taskAllGroupVO;
    }

}