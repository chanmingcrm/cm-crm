package com.platform.mesh.crm.biz.modules.crm.oninvoice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.mapper.CrmOnInvoiceMapper;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.ICrmOnInvoiceService;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.service.manual.CrmOnInvoiceServiceManual;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系发票回执
 * @author 蝉鸣
 */
@Service
public class CrmOnInvoiceServiceImpl extends AppServiceAbstract<CrmOnInvoiceMapper, CrmOnInvoice> implements ICrmOnInvoiceService  {

    @Autowired
    private CrmOnInvoiceServiceManual crmOnInvoiceServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系发票回执〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnInvoiceData> crmOnInvoiceDataList = BeanUtil.copyToList(dataList, CrmOnInvoiceData.class);
        //批量保存data表数据
        crmOnInvoiceServiceManual.addDbDataBatch(crmOnInvoiceDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系发票回执〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmOnInvoiceServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmOnInvoice::getScopeUserId,scopeUserId)
                .set(CrmOnInvoice::getScopeOrgId,scopeOrgId)
                .in(CrmOnInvoice::getId,dataIds)
                .update();
        //修改DB Data
        crmOnInvoiceServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}