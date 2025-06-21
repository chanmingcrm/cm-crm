package com.platform.mesh.tmp.biz.modules.task.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.tmp.biz.modules.task.base.domain.po.TaskBase;
import com.platform.mesh.tmp.biz.modules.task.base.mapper.TaskBaseMapper;
import com.platform.mesh.tmp.biz.modules.task.base.service.ITaskBaseService;
import com.platform.mesh.tmp.biz.modules.task.base.service.manual.TaskBaseServiceManual;
import com.platform.mesh.tmp.biz.modules.task.basedata.domain.po.TaskBaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务
 * @author 蝉鸣
 */
@Service
public class TaskBaseServiceImpl extends AppServiceAbstract<TaskBaseMapper, TaskBase> implements ITaskBaseService {

    @Autowired
    private TaskBaseServiceManual taskBaseServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<TaskBaseData> taskBaseDataList = BeanUtil.copyToList(dataList, TaskBaseData.class);
        //批量保存data表数据
        taskBaseServiceManual.addDbDataBatch(taskBaseDataList);
    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        taskBaseServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbDataBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(TaskBase::getScopeUserId,scopeUserId)
                .set(TaskBase::getScopeOrgId,scopeOrgId)
                .in(TaskBase::getId,dataIds)
                .update();
        //修改DB Data
        taskBaseServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}