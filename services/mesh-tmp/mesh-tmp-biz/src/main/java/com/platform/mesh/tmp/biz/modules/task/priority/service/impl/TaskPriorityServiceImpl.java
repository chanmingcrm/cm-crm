package com.platform.mesh.tmp.biz.modules.task.priority.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.dto.TaskPriorityDTO;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.vo.TaskPriorityVO;
import com.platform.mesh.tmp.biz.modules.task.priority.exception.TaskPriorityExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.priority.mapper.TaskPriorityMapper;
import com.platform.mesh.tmp.biz.modules.task.priority.service.ITaskPriorityService;
import com.platform.mesh.tmp.biz.modules.task.priority.service.manual.TaskPriorityServiceManual;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.po.TaskPriority;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务优先级
 * @author 蝉鸣
 */
@Service
public class TaskPriorityServiceImpl extends ServiceImpl<TaskPriorityMapper, TaskPriority> implements ITaskPriorityService  {

    @Autowired
    private TaskPriorityServiceManual taskPriorityServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param priorityId priorityId  
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPriorityVO getPriorityInfoById(Long priorityId) {
        TaskPriority taskPriority = this.getById(priorityId);
        return taskPriorityServiceManual.getPriorityInfoById(taskPriority);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPriorityVO addPriority(TaskPriorityDTO priorityDTO) {
        TaskPriority taskPriority = BeanUtil.copyProperties(priorityDTO, TaskPriority.class);
        this.save(taskPriority);
        return BeanUtil.copyProperties(taskPriority, TaskPriorityVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link TaskPriorityVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPriorityVO editPriority(TaskPriorityDTO priorityDTO) {
        if(ObjectUtil.isEmpty(priorityDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskPriorityDTO::getId);
            throw TaskPriorityExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskPriority taskPriority = BeanUtil.copyProperties(priorityDTO, TaskPriority.class);
        this.updateById(taskPriority);
        return BeanUtil.copyProperties(taskPriority, TaskPriorityVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param priorityId priorityId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deletePriority(Long priorityId) {
        
        return this.removeById(priorityId);
    }
}