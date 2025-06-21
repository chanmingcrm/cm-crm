package com.platform.mesh.crm.biz.modules.crm.oncontract.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.domain.po.CrmOnContract;
import com.platform.mesh.crm.biz.modules.crm.oncontract.mapper.CrmOnContractMapper;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.ICrmOnContractService;
import com.platform.mesh.crm.biz.modules.crm.oncontract.service.manual.CrmOnContractServiceManual;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系合同签订
 * @author 蝉鸣
 */
@Service
public class CrmOnContractServiceImpl extends AppServiceAbstract<CrmOnContractMapper, CrmOnContract> implements ICrmOnContractService  {

    @Autowired
    private CrmOnContractServiceManual crmOnContractServiceManual;



    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnContractData> crmOnContractData = BeanUtil.copyToList(dataList, CrmOnContractData.class);
        //批量保存data表数据
        crmOnContractServiceManual.addDbDataBatch(crmOnContractData);
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
        crmOnContractServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmOnContract::getScopeUserId,scopeUserId)
                .set(CrmOnContract::getScopeOrgId,scopeOrgId)
                .in(CrmOnContract::getId,dataIds)
                .update();
        //修改DB Data
        crmOnContractServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}