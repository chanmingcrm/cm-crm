package com.platform.mesh.tmp.biz.modules.task.userrelhis.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.vo.TaskUserRelHisVO;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.po.TaskUserRelHis;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务人员历史
 * @author 蝉鸣
 */
@Service
public class TaskUserRelHisServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskUserRelHis taskUserRelHis 
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    public TaskUserRelHisVO getUserRelHisInfoById(TaskUserRelHis taskUserRelHis) {
        TaskUserRelHisVO taskUserRelHisVO = new TaskUserRelHisVO();
        if(ObjectUtil.isEmpty(taskUserRelHisVO)){
            return taskUserRelHisVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskUserRelHis, taskUserRelHisVO);
        return taskUserRelHisVO;
    }

}