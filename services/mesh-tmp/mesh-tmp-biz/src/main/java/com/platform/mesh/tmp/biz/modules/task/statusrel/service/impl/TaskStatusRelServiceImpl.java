package com.platform.mesh.tmp.biz.modules.task.statusrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.dto.TaskStatusRelDTO;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.vo.TaskStatusRelVO;
import com.platform.mesh.tmp.biz.modules.task.statusrel.exception.TaskStatusRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.statusrel.mapper.TaskStatusRelMapper;
import com.platform.mesh.tmp.biz.modules.task.statusrel.service.ITaskStatusRelService;
import com.platform.mesh.tmp.biz.modules.task.statusrel.service.manual.TaskStatusRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.po.TaskStatusRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务状态关系
 * @author 蝉鸣
 */
@Service
public class TaskStatusRelServiceImpl extends ServiceImpl<TaskStatusRelMapper, TaskStatusRel> implements ITaskStatusRelService  {

    @Autowired
    private TaskStatusRelServiceManual taskStatusRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param statusRelId statusRelId  
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusRelVO getStatusRelInfoById(Long statusRelId) {
        TaskStatusRel taskStatusRel = this.getById(statusRelId);
        return taskStatusRelServiceManual.getStatusRelInfoById(taskStatusRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusRelVO addStatusRel(TaskStatusRelDTO statusRelDTO) {
        TaskStatusRel taskStatusRel = BeanUtil.copyProperties(statusRelDTO, TaskStatusRel.class);
        this.save(taskStatusRel);
        return BeanUtil.copyProperties(taskStatusRel, TaskStatusRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link TaskStatusRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskStatusRelVO editStatusRel(TaskStatusRelDTO statusRelDTO) {
        if(ObjectUtil.isEmpty(statusRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskStatusRelDTO::getId);
            throw TaskStatusRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskStatusRel taskStatusRel = BeanUtil.copyProperties(statusRelDTO, TaskStatusRel.class);
        this.updateById(taskStatusRel);
        return BeanUtil.copyProperties(taskStatusRel, TaskStatusRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param statusRelId statusRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteStatusRel(Long statusRelId) {
        
        return this.removeById(statusRelId);
    }
}