package com.platform.mesh.tmp.biz.modules.task.labelrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.vo.TaskLabelRelVO;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.po.TaskLabelRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务标签关系
 * @author 蝉鸣
 */
@Service
public class TaskLabelRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskLabelRel taskLabelRel 
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    public TaskLabelRelVO getLabelRelInfoById(TaskLabelRel taskLabelRel) {
        TaskLabelRelVO taskLabelRelVO = new TaskLabelRelVO();
        if(ObjectUtil.isEmpty(taskLabelRelVO)){
            return taskLabelRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskLabelRel, taskLabelRelVO);
        return taskLabelRelVO;
    }

}