package com.platform.mesh.tmp.biz.modules.task.allgrouprel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.dto.TaskAllGroupRelDTO;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.vo.TaskAllGroupRelVO;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.exception.TaskAllGroupRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.mapper.TaskAllGroupRelMapper;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.service.ITaskAllGroupRelService;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.service.manual.TaskAllGroupRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.po.TaskAllGroupRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务分组关系
 * @author 蝉鸣
 */
@Service
public class TaskAllGroupRelServiceImpl extends ServiceImpl<TaskAllGroupRelMapper, TaskAllGroupRel> implements ITaskAllGroupRelService  {

    @Autowired
    private TaskAllGroupRelServiceManual taskAllGroupRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param allGroupRelId allGroupRelId  
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupRelVO getAllGroupRelInfoById(Long allGroupRelId) {
        TaskAllGroupRel taskAllGroupRel = this.getById(allGroupRelId);
        return taskAllGroupRelServiceManual.getAllGroupRelInfoById(taskAllGroupRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupRelVO addAllGroupRel(TaskAllGroupRelDTO allGroupRelDTO) {
        TaskAllGroupRel taskAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, TaskAllGroupRel.class);
        this.save(taskAllGroupRel);
        return BeanUtil.copyProperties(taskAllGroupRel, TaskAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link TaskAllGroupRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskAllGroupRelVO editAllGroupRel(TaskAllGroupRelDTO allGroupRelDTO) {
        if(ObjectUtil.isEmpty(allGroupRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskAllGroupRelDTO::getId);
            throw TaskAllGroupRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskAllGroupRel taskAllGroupRel = BeanUtil.copyProperties(allGroupRelDTO, TaskAllGroupRel.class);
        this.updateById(taskAllGroupRel);
        return BeanUtil.copyProperties(taskAllGroupRel, TaskAllGroupRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteAllGroupRel(Long allGroupRelId) {
        
        return this.removeById(allGroupRelId);
    }
}