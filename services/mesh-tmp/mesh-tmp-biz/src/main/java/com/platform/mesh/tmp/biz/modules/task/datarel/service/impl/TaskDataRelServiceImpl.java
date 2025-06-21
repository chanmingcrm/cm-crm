package com.platform.mesh.tmp.biz.modules.task.datarel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.dto.TaskDataRelDTO;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.vo.TaskDataRelVO;
import com.platform.mesh.tmp.biz.modules.task.datarel.exception.TaskDataRelExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.datarel.mapper.TaskDataRelMapper;
import com.platform.mesh.tmp.biz.modules.task.datarel.service.ITaskDataRelService;
import com.platform.mesh.tmp.biz.modules.task.datarel.service.manual.TaskDataRelServiceManual;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.po.TaskDataRel;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务数据关联
 * @author 蝉鸣
 */
@Service
public class TaskDataRelServiceImpl extends ServiceImpl<TaskDataRelMapper, TaskDataRel> implements ITaskDataRelService  {

    @Autowired
    private TaskDataRelServiceManual taskDataRelServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param dataRelId dataRelId  
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskDataRelVO getDataRelInfoById(Long dataRelId) {
        TaskDataRel taskDataRel = this.getById(dataRelId);
        return taskDataRelServiceManual.getDataRelInfoById(taskDataRel);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskDataRelVO addDataRel(TaskDataRelDTO dataRelDTO) {
        TaskDataRel taskDataRel = BeanUtil.copyProperties(dataRelDTO, TaskDataRel.class);
        this.save(taskDataRel);
        return BeanUtil.copyProperties(taskDataRel, TaskDataRelVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link TaskDataRelVO}
     * @author 蝉鸣
     */
    @Override
    public TaskDataRelVO editDataRel(TaskDataRelDTO dataRelDTO) {
        if(ObjectUtil.isEmpty(dataRelDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskDataRelDTO::getId);
            throw TaskDataRelExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskDataRel taskDataRel = BeanUtil.copyProperties(dataRelDTO, TaskDataRel.class);
        this.updateById(taskDataRel);
        return BeanUtil.copyProperties(taskDataRel, TaskDataRelVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteDataRel(Long dataRelId) {
        
        return this.removeById(dataRelId);
    }
}