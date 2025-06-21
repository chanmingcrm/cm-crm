package com.platform.mesh.tmp.biz.modules.task.label.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.label.domain.dto.TaskLabelDTO;
import com.platform.mesh.tmp.biz.modules.task.label.domain.vo.TaskLabelVO;
import com.platform.mesh.tmp.biz.modules.task.label.exception.TaskLabelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.label.mapper.TaskLabelMapper;
import com.platform.mesh.tmp.biz.modules.task.label.service.ITaskLabelService;
import com.platform.mesh.tmp.biz.modules.task.label.service.manual.TaskLabelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.label.domain.po.TaskLabel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务标签
 * @author 蝉鸣
 */
@Service
public class TaskLabelServiceImpl extends ServiceImpl<TaskLabelMapper, TaskLabel> implements ITaskLabelService  {

    @Autowired
    private TaskLabelServiceManual taskLabelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param labelId labelId  
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelVO getLabelInfoById(Long labelId) {
        TaskLabel taskLabel = this.getById(labelId);
        return taskLabelServiceManual.getLabelInfoById(taskLabel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelVO addLabel(TaskLabelDTO labelDTO) {
        TaskLabel taskLabel = BeanUtil.copyProperties(labelDTO, TaskLabel.class);
        this.save(taskLabel);
        return BeanUtil.copyProperties(taskLabel, TaskLabelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link TaskLabelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelVO editLabel(TaskLabelDTO labelDTO) {
        if(ObjectUtil.isEmpty(labelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskLabelDTO::getId);
            throw TaskLabelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskLabel taskLabel = BeanUtil.copyProperties(labelDTO, TaskLabel.class);
        this.updateById(taskLabel);
        return BeanUtil.copyProperties(taskLabel, TaskLabelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param labelId labelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteLabel(Long labelId) {
        
        return this.removeById(labelId);
    }
}