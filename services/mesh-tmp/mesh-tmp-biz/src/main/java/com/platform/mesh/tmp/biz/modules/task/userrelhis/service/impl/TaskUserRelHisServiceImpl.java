package com.platform.mesh.tmp.biz.modules.task.userrelhis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.dto.TaskUserRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.vo.TaskUserRelHisVO;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.exception.TaskUserRelHisExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.mapper.TaskUserRelHisMapper;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.service.ITaskUserRelHisService;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.service.manual.TaskUserRelHisServiceManual;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.po.TaskUserRelHis;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务人员历史
 * @author 蝉鸣
 */
@Service
public class TaskUserRelHisServiceImpl extends ServiceImpl<TaskUserRelHisMapper, TaskUserRelHis> implements ITaskUserRelHisService  {

    @Autowired
    private TaskUserRelHisServiceManual taskUserRelHisServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param userRelHisId userRelHisId  
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelHisVO getUserRelHisInfoById(Long userRelHisId) {
        TaskUserRelHis taskUserRelHis = this.getById(userRelHisId);
        return taskUserRelHisServiceManual.getUserRelHisInfoById(taskUserRelHis);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelHisVO addUserRelHis(TaskUserRelHisDTO userRelHisDTO) {
        TaskUserRelHis taskUserRelHis = BeanUtil.copyProperties(userRelHisDTO, TaskUserRelHis.class);
        this.save(taskUserRelHis);
        return BeanUtil.copyProperties(taskUserRelHis, TaskUserRelHisVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link TaskUserRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskUserRelHisVO editUserRelHis(TaskUserRelHisDTO userRelHisDTO) {
        if(ObjectUtil.isEmpty(userRelHisDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskUserRelHisDTO::getId);
            throw TaskUserRelHisExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskUserRelHis taskUserRelHis = BeanUtil.copyProperties(userRelHisDTO, TaskUserRelHis.class);
        this.updateById(taskUserRelHis);
        return BeanUtil.copyProperties(taskUserRelHis, TaskUserRelHisVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param userRelHisId userRelHisId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteUserRelHis(Long userRelHisId) {
        
        return this.removeById(userRelHisId);
    }
}