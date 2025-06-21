package com.platform.mesh.tmp.biz.modules.task.postrelhis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.dto.TaskPostRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.vo.TaskPostRelHisVO;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.exception.TaskPostRelHisExceptionEnum;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.mapper.TaskPostRelHisMapper;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.service.ITaskPostRelHisService;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.service.manual.TaskPostRelHisServiceManual;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.po.TaskPostRelHis;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务工职关系历史
 * @author 蝉鸣
 */
@Service
public class TaskPostRelHisServiceImpl extends ServiceImpl<TaskPostRelHisMapper, TaskPostRelHis> implements ITaskPostRelHisService  {

    @Autowired
    private TaskPostRelHisServiceManual taskPostRelHisServiceManual;

    
    /**
     * 功能描述: 
     * 〈获取当前信息〉
     * @param postRelHisId postRelHisId  
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelHisVO getPostRelHisInfoById(Long postRelHisId) {
        TaskPostRelHis taskPostRelHis = this.getById(postRelHisId);
        return taskPostRelHisServiceManual.getPostRelHisInfoById(taskPostRelHis);
    }

    /**
     * 功能描述:
     * 〈新增〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelHisVO addPostRelHis(TaskPostRelHisDTO postRelHisDTO) {
        TaskPostRelHis taskPostRelHis = BeanUtil.copyProperties(postRelHisDTO, TaskPostRelHis.class);
        this.save(taskPostRelHis);
        return BeanUtil.copyProperties(taskPostRelHis, TaskPostRelHisVO.class);
    }

    /**
     * 功能描述:
     * 〈修改〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link TaskPostRelHisVO}
     * @author 蝉鸣
     */
    @Override
    public TaskPostRelHisVO editPostRelHis(TaskPostRelHisDTO postRelHisDTO) {
        if(ObjectUtil.isEmpty(postRelHisDTO.getId())){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(TaskPostRelHisDTO::getId);
            throw TaskPostRelHisExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        TaskPostRelHis taskPostRelHis = BeanUtil.copyProperties(postRelHisDTO, TaskPostRelHis.class);
        this.updateById(taskPostRelHis);
        return BeanUtil.copyProperties(taskPostRelHis, TaskPostRelHisVO.class);
    }

    /**
     * 功能描述:
     * 〈删除〉
     * @param postRelHisId postRelHisId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deletePostRelHis(Long postRelHisId) {
        
        return this.removeById(postRelHisId);
    }
}