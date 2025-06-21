package com.platform.mesh.tmp.biz.modules.task.postrelhis.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.vo.TaskPostRelHisVO;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.po.TaskPostRelHis;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务工职关系历史
 * @author 蝉鸣
 */
@Service
public class TaskPostRelHisServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskPostRelHis taskPostRelHis 
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    public TaskPostRelHisVO getPostRelHisInfoById(TaskPostRelHis taskPostRelHis) {
        TaskPostRelHisVO taskPostRelHisVO = new TaskPostRelHisVO();
        if(ObjectUtil.isEmpty(taskPostRelHisVO)){
            return taskPostRelHisVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskPostRelHis, taskPostRelHisVO);
        return taskPostRelHisVO;
    }

}