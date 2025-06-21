package com.platform.mesh.crm.biz.modules.crm.precustomer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.crm.biz.modules.crm.precustomer.mapper.CrmPreCustomerMapper;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.ICrmPreCustomerService;
import com.platform.mesh.crm.biz.modules.crm.precustomer.service.manual.CrmPreCustomerServiceManual;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
@Service
public class CrmPreCustomerServiceImpl extends AppServiceAbstract<CrmPreCustomerMapper, CrmPreCustomer> implements ICrmPreCustomerService  {

    @Autowired
    private CrmPreCustomerServiceManual crmPreCustomerServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系客户对象〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreCustomerData> crmPreCustomerDataList = BeanUtil.copyToList(dataList, CrmPreCustomerData.class);
        //批量保存data表数据
        crmPreCustomerServiceManual.addDbDataBatch(crmPreCustomerDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系客户对象〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreCustomerServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreCustomer::getScopeUserId,scopeUserId)
                .set(CrmPreCustomer::getScopeOrgId,scopeOrgId)
                .in(CrmPreCustomer::getId,dataIds)
                .update();
        //修改DB Data
        crmPreCustomerServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}