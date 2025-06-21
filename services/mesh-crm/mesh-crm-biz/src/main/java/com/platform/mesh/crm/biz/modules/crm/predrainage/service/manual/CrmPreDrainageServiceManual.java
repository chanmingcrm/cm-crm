package com.platform.mesh.crm.biz.modules.crm.predrainage.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.service.ICrmPreDrainageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系活动引流
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreDrainageServiceManual.class);


    @Autowired
    private ICrmPreDrainageDataService crmPreDrainageDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preDrainageDataList preDrainageDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreDrainageData> preDrainageDataList) {
        if(CollUtil.isEmpty(preDrainageDataList)){
            return;
        }
        //批量新增信息
        crmPreDrainageDataService.saveBatch(preDrainageDataList);
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
        List<CrmPreDrainageData> preDrainageDataList = crmPreDrainageDataService.lambdaQuery().eq(CrmPreDrainageData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreDrainageData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preDrainageDataList)) {
            return;
        }
        AppUtil.editDbData(preDrainageDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preDrainageDataList)){
            return;
        }
        crmPreDrainageDataService.updateBatchById(preDrainageDataList);
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
        crmPreDrainageDataService.lambdaUpdate()
                .set(CrmPreDrainageData::getScopeUserId, scopeUserId)
                .set(CrmPreDrainageData::getScopeOrgId, scopeOrgId)
                .in(CrmPreDrainageData::getDataId, dataIds)
                .update();
    }
}