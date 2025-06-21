package com.platform.mesh.tmp.biz.modules.task.allgroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.dto.TaskAllGroupDTO;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.vo.TaskAllGroupVO;
import com.platform.mesh.tmp.biz.modules.task.allgroup.exception.TaskAllGroupExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.allgroup.mapper.TaskAllGroupMapper;
import com.platform.mesh.tmp.biz.modules.task.allgroup.service.ITaskAllGroupService;
import com.platform.mesh.tmp.biz.modules.task.allgroup.service.manual.TaskAllGroupServiceManual;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.po.TaskAllGroup;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务分组
 * @author 蝉鸣
 */
@Service
public class TaskAllGroupServiceImpl extends ServiceImpl<TaskAllGroupMapper, TaskAllGroup> implements ITaskAllGroupService  {

    @Autowired
    private TaskAllGroupServiceManual taskAllGroupServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupId allGroupId  
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupVO getAllGroupInfoById(Long allGroupId) {
        TaskAllGroup taskAllGroup = this.getById(allGroupId);
        return taskAllGroupServiceManual.getAllGroupInfoById(taskAllGroup);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupVO addAllGroup(TaskAllGroupDTO allGroupDTO) {
        TaskAllGroup taskAllGroup = BeanUtil.copyProperties(allGroupDTO, TaskAllGroup.class);
        this.save(taskAllGroup);
        return BeanUtil.copyProperties(taskAllGroup, TaskAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link TaskAllGroupVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupVO editAllGroup(TaskAllGroupDTO allGroupDTO) {
        if(ObjectUtil.isEmpty(allGroupDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskAllGroupDTO::getId);
            throw TaskAllGroupExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskAllGroup taskAllGroup = BeanUtil.copyProperties(allGroupDTO, TaskAllGroup.class);
        this.updateById(taskAllGroup);
        return BeanUtil.copyProperties(taskAllGroup, TaskAllGroupVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroup(Long allGroupId) {
        
        return this.removeById(allGroupId);
    }
}