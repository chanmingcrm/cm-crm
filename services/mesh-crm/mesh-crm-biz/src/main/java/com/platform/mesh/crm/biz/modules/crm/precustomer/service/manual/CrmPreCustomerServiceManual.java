package com.platform.mesh.crm.biz.modules.crm.precustomer.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.service.ICrmPreCustomerDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
@Service
public class CrmPreCustomerServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreCustomerServiceManual.class);


    @Autowired
    private ICrmPreCustomerDataService crmPreCustomerDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preCustomerDataList preCustomerDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreCustomerData> preCustomerDataList) {
        if(CollUtil.isEmpty(preCustomerDataList)){
            return;
        }
        //批量新增信息
        crmPreCustomerDataService.saveBatch(preCustomerDataList);
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
        List<CrmPreCustomerData> preCustomerDataList = crmPreCustomerDataService.lambdaQuery().eq(CrmPreCustomerData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreCustomerData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preCustomerDataList)) {
            return;
        }
        AppUtil.editDbData(preCustomerDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preCustomerDataList)){
            return;
        }
        crmPreCustomerDataService.updateBatchById(preCustomerDataList);
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
        crmPreCustomerDataService.lambdaUpdate()
                .set(CrmPreCustomerData::getScopeUserId, scopeUserId)
                .set(CrmPreCustomerData::getScopeOrgId, scopeOrgId)
                .in(CrmPreCustomerData::getDataId, dataIds)
                .update();
    }
}