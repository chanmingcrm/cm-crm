package com.platform.mesh.tmp.biz.modules.task.userrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.vo.TaskUserRelVO;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.po.TaskUserRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务人员
 * @author 蝉鸣
 */
@Service
public class TaskUserRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskUserRel taskUserRel 
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    public TaskUserRelVO getUserRelInfoById(TaskUserRel taskUserRel) {
        TaskUserRelVO taskUserRelVO = new TaskUserRelVO();
        if(ObjectUtil.isEmpty(taskUserRelVO)){
            return taskUserRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskUserRel, taskUserRelVO);
        return taskUserRelVO;
    }

}