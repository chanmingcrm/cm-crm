package com.platform.mesh.tmp.biz.modules.work.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.tmp.biz.modules.work.log.domain.po.WorkLog;
import com.platform.mesh.tmp.biz.modules.work.log.mapper.WorkLogMapper;
import com.platform.mesh.tmp.biz.modules.work.log.service.IWorkLogService;
import com.platform.mesh.tmp.biz.modules.work.log.service.manual.WorkLogServiceManual;
import com.platform.mesh.tmp.biz.modules.work.logdata.domain.po.WorkLogData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 工作日志
 * @author 蝉鸣
 */
@Service
public class WorkLogServiceImpl extends AppServiceAbstract<WorkLogMapper, WorkLog> implements IWorkLogService {

    @Autowired
    private WorkLogServiceManual workLogServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<WorkLogData> workLogDataData = BeanUtil.copyToList(dataList, WorkLogData.class);
        //批量保存data表数据
        workLogServiceManual.addDbDataBatch(workLogDataData);
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
        workLogServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(WorkLog::getScopeUserId,scopeUserId)
                .set(WorkLog::getScopeOrgId,scopeOrgId)
                .in(WorkLog::getId,dataIds)
                .update();
        //修改DB Data
        workLogServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}