package com.platform.mesh.tmp.biz.modules.task.label.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.label.domain.vo.TaskLabelVO;
import com.platform.mesh.tmp.biz.modules.task.label.domain.po.TaskLabel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务标签
 * @author 蝉鸣
 */
@Service
public class TaskLabelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskLabel taskLabel 
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    public TaskLabelVO getLabelInfoById(TaskLabel taskLabel) {
        TaskLabelVO taskLabelVO = new TaskLabelVO();
        if(ObjectUtil.isEmpty(taskLabelVO)){
            return taskLabelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskLabel, taskLabelVO);
        return taskLabelVO;
    }

}