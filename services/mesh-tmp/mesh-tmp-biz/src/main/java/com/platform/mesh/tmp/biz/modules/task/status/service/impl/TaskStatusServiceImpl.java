package com.platform.mesh.tmp.biz.modules.task.status.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.status.domain.dto.TaskStatusDTO;
import com.platform.mesh.tmp.biz.modules.task.status.domain.vo.TaskStatusVO;
import com.platform.mesh.tmp.biz.modules.task.status.exception.TaskStatusExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.status.mapper.TaskStatusMapper;
import com.platform.mesh.tmp.biz.modules.task.status.service.ITaskStatusService;
import com.platform.mesh.tmp.biz.modules.task.status.service.manual.TaskStatusServiceManual;
import com.platform.mesh.tmp.biz.modules.task.status.domain.po.TaskStatus;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务状态
 * @author 蝉鸣
 */
@Service
public class TaskStatusServiceImpl extends ServiceImpl<TaskStatusMapper, TaskStatus> implements ITaskStatusService  {

    @Autowired
    private TaskStatusServiceManual taskStatusServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param statusId statusId  
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusVO getStatusInfoById(Long statusId) {
        TaskStatus taskStatus = this.getById(statusId);
        return taskStatusServiceManual.getStatusInfoById(taskStatus);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusVO addStatus(TaskStatusDTO statusDTO) {
        TaskStatus taskStatus = BeanUtil.copyProperties(statusDTO, TaskStatus.class);
        this.save(taskStatus);
        return BeanUtil.copyProperties(taskStatus, TaskStatusVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link TaskStatusVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusVO editStatus(TaskStatusDTO statusDTO) {
        if(ObjectUtil.isEmpty(statusDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskStatusDTO::getId);
            throw TaskStatusExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskStatus taskStatus = BeanUtil.copyProperties(statusDTO, TaskStatus.class);
        this.updateById(taskStatus);
        return BeanUtil.copyProperties(taskStatus, TaskStatusVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param statusId statusId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteStatus(Long statusId) {
        
        return this.removeById(statusId);
    }
}