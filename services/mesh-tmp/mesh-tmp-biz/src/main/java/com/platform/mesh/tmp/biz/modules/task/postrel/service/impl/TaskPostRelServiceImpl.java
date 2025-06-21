package com.platform.mesh.tmp.biz.modules.task.postrel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.dto.TaskPostRelDTO;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.vo.TaskPostRelVO;
import com.platform.mesh.tmp.biz.modules.task.postrel.exception.TaskPostRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.postrel.mapper.TaskPostRelMapper;
import com.platform.mesh.tmp.biz.modules.task.postrel.service.ITaskPostRelService;
import com.platform.mesh.tmp.biz.modules.task.postrel.service.manual.TaskPostRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.po.TaskPostRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务工职关系
 * @author 蝉鸣
 */
@Service
public class TaskPostRelServiceImpl extends ServiceImpl<TaskPostRelMapper, TaskPostRel> implements ITaskPostRelService  {

    @Autowired
    private TaskPostRelServiceManual taskPostRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param postRelId postRelId  
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelVO getPostRelInfoById(Long postRelId) {
        TaskPostRel taskPostRel = this.getById(postRelId);
        return taskPostRelServiceManual.getPostRelInfoById(taskPostRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelVO addPostRel(TaskPostRelDTO postRelDTO) {
        TaskPostRel taskPostRel = BeanUtil.copyProperties(postRelDTO, TaskPostRel.class);
        this.save(taskPostRel);
        return BeanUtil.copyProperties(taskPostRel, TaskPostRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link TaskPostRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelVO editPostRel(TaskPostRelDTO postRelDTO) {
        if(ObjectUtil.isEmpty(postRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskPostRelDTO::getId);
            throw TaskPostRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskPostRel taskPostRel = BeanUtil.copyProperties(postRelDTO, TaskPostRel.class);
        this.updateById(taskPostRel);
        return BeanUtil.copyProperties(taskPostRel, TaskPostRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param postRelId postRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deletePostRel(Long postRelId) {
        
        return this.removeById(postRelId);
    }
}