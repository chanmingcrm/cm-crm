package com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.domain.po.CrmSufDeliverData;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.service.ICrmSufDeliverDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系标的交付
 * @author 蝉鸣
 */
@Service
public class CrmSufDeliverServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufDeliverServiceManual.class);


    @Autowired
    private ICrmSufDeliverDataService crmSufDeliverDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufDeliverDataList sufDeliverDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufDeliverData> sufDeliverDataList) {
        if(CollUtil.isEmpty(sufDeliverDataList)){
            return;
        }
        //批量新增信息
        crmSufDeliverDataService.saveBatch(sufDeliverDataList);
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
        List<CrmSufDeliverData> sufDeliverDataList = crmSufDeliverDataService.lambdaQuery().eq(CrmSufDeliverData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmSufDeliverData::getDataId, dataId).list();
        if(CollUtil.isEmpty(sufDeliverDataList)) {
            return;
        }
        AppUtil.editDbData(sufDeliverDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(sufDeliverDataList)){
            return;
        }
        crmSufDeliverDataService.updateBatchById(sufDeliverDataList);
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
        crmSufDeliverDataService.lambdaUpdate()
                .set(CrmSufDeliverData::getScopeUserId, scopeUserId)
                .set(CrmSufDeliverData::getScopeOrgId, scopeOrgId)
                .in(CrmSufDeliverData::getDataId, dataIds)
                .update();
    }

}