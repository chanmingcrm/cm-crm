package com.platform.mesh.tmp.biz.modules.task.userrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.dto.TaskUserRelDTO;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.vo.TaskUserRelVO;
import com.platform.mesh.tmp.biz.modules.task.userrel.exception.TaskUserRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.userrel.mapper.TaskUserRelMapper;
import com.platform.mesh.tmp.biz.modules.task.userrel.service.ITaskUserRelService;
import com.platform.mesh.tmp.biz.modules.task.userrel.service.manual.TaskUserRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.po.TaskUserRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务人员
 * @author 蝉鸣
 */
@Service
public class TaskUserRelServiceImpl extends ServiceImpl<TaskUserRelMapper, TaskUserRel> implements ITaskUserRelService  {

    @Autowired
    private TaskUserRelServiceManual taskUserRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param userRelId userRelId  
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelVO getUserRelInfoById(Long userRelId) {
        TaskUserRel taskUserRel = this.getById(userRelId);
        return taskUserRelServiceManual.getUserRelInfoById(taskUserRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelVO addUserRel(TaskUserRelDTO userRelDTO) {
        TaskUserRel taskUserRel = BeanUtil.copyProperties(userRelDTO, TaskUserRel.class);
        this.save(taskUserRel);
        return BeanUtil.copyProperties(taskUserRel, TaskUserRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link TaskUserRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelVO editUserRel(TaskUserRelDTO userRelDTO) {
        if(ObjectUtil.isEmpty(userRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskUserRelDTO::getId);
            throw TaskUserRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskUserRel taskUserRel = BeanUtil.copyProperties(userRelDTO, TaskUserRel.class);
        this.updateById(taskUserRel);
        return BeanUtil.copyProperties(taskUserRel, TaskUserRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param userRelId userRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteUserRel(Long userRelId) {
        
        return this.removeById(userRelId);
    }
}