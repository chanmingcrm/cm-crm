package com.platform.mesh.crm.biz.modules.crm.onorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oninvoice.domain.po.CrmOnInvoice;
import com.platform.mesh.crm.biz.modules.crm.onorder.domain.po.CrmOnOrder;
import com.platform.mesh.crm.biz.modules.crm.onorder.mapper.CrmOnOrderMapper;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.ICrmOnOrderService;
import com.platform.mesh.crm.biz.modules.crm.onorder.service.manual.CrmOnOrderServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系订单
 * @author 蝉鸣
 */
@Service
public class CrmOnOrderServiceImpl extends AppServiceAbstract<CrmOnOrderMapper, CrmOnOrder> implements ICrmOnOrderService {

    @Autowired
    private CrmOnOrderServiceManual crmOnOrderServiceManual;



    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnOrderData> crmOnOrderData = BeanUtil.copyToList(dataList, CrmOnOrderData.class);
        //批量保存data表数据
        crmOnOrderServiceManual.addDbDataBatch(crmOnOrderData);
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
        crmOnOrderServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmOnOrder::getScopeUserId,scopeUserId)
                .set(CrmOnOrder::getScopeOrgId,scopeOrgId)
                .in(CrmOnOrder::getId,dataIds)
                .update();
        //修改DB Data
        crmOnOrderServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}