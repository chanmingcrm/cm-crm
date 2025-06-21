package com.platform.mesh.tmp.biz.modules.task.labelrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.dto.TaskLabelRelDTO;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.vo.TaskLabelRelVO;
import com.platform.mesh.tmp.biz.modules.task.labelrel.exception.TaskLabelRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.labelrel.mapper.TaskLabelRelMapper;
import com.platform.mesh.tmp.biz.modules.task.labelrel.service.ITaskLabelRelService;
import com.platform.mesh.tmp.biz.modules.task.labelrel.service.manual.TaskLabelRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.po.TaskLabelRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务标签关系
 * @author 蝉鸣
 */
@Service
public class TaskLabelRelServiceImpl extends ServiceImpl<TaskLabelRelMapper, TaskLabelRel> implements ITaskLabelRelService  {

    @Autowired
    private TaskLabelRelServiceManual taskLabelRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param labelRelId labelRelId  
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelRelVO getLabelRelInfoById(Long labelRelId) {
        TaskLabelRel taskLabelRel = this.getById(labelRelId);
        return taskLabelRelServiceManual.getLabelRelInfoById(taskLabelRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelRelVO addLabelRel(TaskLabelRelDTO labelRelDTO) {
        TaskLabelRel taskLabelRel = BeanUtil.copyProperties(labelRelDTO, TaskLabelRel.class);
        this.save(taskLabelRel);
        return BeanUtil.copyProperties(taskLabelRel, TaskLabelRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link TaskLabelRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskLabelRelVO editLabelRel(TaskLabelRelDTO labelRelDTO) {
        if(ObjectUtil.isEmpty(labelRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskLabelRelDTO::getId);
            throw TaskLabelRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskLabelRel taskLabelRel = BeanUtil.copyProperties(labelRelDTO, TaskLabelRel.class);
        this.updateById(taskLabelRel);
        return BeanUtil.copyProperties(taskLabelRel, TaskLabelRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param labelRelId labelRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteLabelRel(Long labelRelId) {
        
        return this.removeById(labelRelId);
    }
}