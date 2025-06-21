package com.platform.mesh.tmp.biz.modules.work.log.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.tmp.biz.modules.work.logdata.domain.po.WorkLogData;
import com.platform.mesh.tmp.biz.modules.work.logdata.service.IWorkLogDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 工作日志
 * @author 蝉鸣
 */
@Service
public class WorkLogServiceManual {

    private final static Logger log = LoggerFactory.getLogger(WorkLogServiceManual.class);

    @Autowired
    private IWorkLogDataService workLogDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param workLogDataList workLogDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<WorkLogData> workLogDataList) {
        if(CollUtil.isEmpty(workLogDataList)){
            return;
        }
        //批量新增信息
        workLogDataService.saveBatch(workLogDataList);
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量修改〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<WorkLogData> workLogDataList = workLogDataService.lambdaQuery().eq(WorkLogData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(WorkLogData::getDataId, dataId).list();
        if(CollUtil.isEmpty(workLogDataList)) {
            return;
        }
        AppUtil.editDbData(workLogDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(workLogDataList)){
            return;
        }
        workLogDataService.updateBatchById(workLogDataList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public void transDbDataBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        if(CollUtil.isEmpty(dataIds) || ObjectUtil.isEmpty(scopeUserId) || ObjectUtil.isEmpty(scopeOrgId)) {
            return;
        }
        workLogDataService.lambdaUpdate()
                .set(WorkLogData::getScopeUserId, scopeUserId)
                .set(WorkLogData::getScopeOrgId, scopeOrgId)
                .in(WorkLogData::getDataId, dataIds)
                .update();
    }

}