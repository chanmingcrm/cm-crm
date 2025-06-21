package com.platform.mesh.crm.biz.modules.crm.oninvoice.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.service.ICrmOnInvoiceDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系发票回执
 * @author 蝉鸣
 */
@Service
public class CrmOnInvoiceServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnInvoiceServiceManual.class);

    @Autowired
    private ICrmOnInvoiceDataService crmOnInvoiceDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onInvoiceDataList onInvoiceDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnInvoiceData> onInvoiceDataList) {
        if(CollUtil.isEmpty(onInvoiceDataList)){
            return;
        }
        //批量新增信息
        crmOnInvoiceDataService.saveBatch(onInvoiceDataList);
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
        List<CrmOnInvoiceData> onInvoiceDataList = crmOnInvoiceDataService.lambdaQuery().eq(CrmOnInvoiceData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnInvoiceData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onInvoiceDataList)) {
            return;
        }
        AppUtil.editDbData(onInvoiceDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onInvoiceDataList)){
            return;
        }
        crmOnInvoiceDataService.updateBatchById(onInvoiceDataList);
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
        crmOnInvoiceDataService.lambdaUpdate()
                .set(CrmOnInvoiceData::getScopeUserId, scopeUserId)
                .set(CrmOnInvoiceData::getScopeOrgId, scopeOrgId)
                .in(CrmOnInvoiceData::getDataId, dataIds)
                .update();
    }
}