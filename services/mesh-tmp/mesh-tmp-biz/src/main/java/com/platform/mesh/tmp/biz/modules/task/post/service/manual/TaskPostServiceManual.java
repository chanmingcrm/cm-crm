package com.platform.mesh.tmp.biz.modules.task.post.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.tmp.biz.modules.task.post.domain.vo.TaskPostVO;
import com.platform.mesh.tmp.biz.modules.task.post.domain.po.TaskPost;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 任务工职
 * @author 蝉鸣
 */
@Service
public class TaskPostServiceManual{

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param taskPost taskPost 
     * @return 正常返回:{@link TaskPostVO}
     * @author 蝉鸣
     */
    public TaskPostVO getPostInfoById(TaskPost taskPost) {
        TaskPostVO taskPostVO = new TaskPostVO();
        if(ObjectUtil.isEmpty(taskPostVO)){
            return taskPostVO;
        }
        //转换VO
        BeanUtil.copyProperties(taskPost, taskPostVO);
        return taskPostVO;
    }

}