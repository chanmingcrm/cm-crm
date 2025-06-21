package com.platform.mesh.tmp.biz.modules.task.datarel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.vo.TaskDataRelVO;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.po.TaskDataRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务数据关联
 * @author 蝉鸣
 */
@Service
public class TaskDataRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskDataRel taskDataRel 
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    public TaskDataRelVO getDataRelInfoById(TaskDataRel taskDataRel) {
        TaskDataRelVO taskDataRelVO = new TaskDataRelVO();
        if(ObjectUtil.isEmpty(taskDataRelVO)){
            return taskDataRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskDataRel, taskDataRelVO);
        return taskDataRelVO;
    }

}