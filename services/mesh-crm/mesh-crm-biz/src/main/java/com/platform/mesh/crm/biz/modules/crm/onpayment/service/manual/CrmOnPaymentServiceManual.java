package com.platform.mesh.crm.biz.modules.crm.onpayment.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.service.ICrmOnPaymentDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系款项记录
 * @author 蝉鸣
 */
@Service
public class CrmOnPaymentServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnPaymentServiceManual.class);

    
    @Autowired
    private ICrmOnPaymentDataService crmOnPaymentDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onPaymentDataList onPaymentDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnPaymentData> onPaymentDataList) {
        if(CollUtil.isEmpty(onPaymentDataList)){
            return;
        }
        //批量新增信息
        crmOnPaymentDataService.saveBatch(onPaymentDataList);
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
        List<CrmOnPaymentData> onPaymentDataList = crmOnPaymentDataService.lambdaQuery().eq(CrmOnPaymentData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnPaymentData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onPaymentDataList)) {
            return;
        }
        AppUtil.editDbData(onPaymentDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onPaymentDataList)){
            return;
        }
        crmOnPaymentDataService.updateBatchById(onPaymentDataList);
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
        crmOnPaymentDataService.lambdaUpdate()
                .set(CrmOnPaymentData::getScopeUserId, scopeUserId)
                .set(CrmOnPaymentData::getScopeOrgId, scopeOrgId)
                .in(CrmOnPaymentData::getDataId, dataIds)
                .update();
    }
}