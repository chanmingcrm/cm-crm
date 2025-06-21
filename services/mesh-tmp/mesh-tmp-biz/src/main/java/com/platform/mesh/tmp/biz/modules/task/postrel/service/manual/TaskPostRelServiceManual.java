package com.platform.mesh.tmp.biz.modules.task.postrel.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.vo.TaskPostRelVO;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.po.TaskPostRel;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务工职关系
 * @author 蝉鸣
 */
@Service
public class TaskPostRelServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskPostRel taskPostRel 
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    public TaskPostRelVO getPostRelInfoById(TaskPostRel taskPostRel) {
        TaskPostRelVO taskPostRelVO = new TaskPostRelVO();
        if(ObjectUtil.isEmpty(taskPostRelVO)){
            return taskPostRelVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskPostRel, taskPostRelVO);
        return taskPostRelVO;
    }

}