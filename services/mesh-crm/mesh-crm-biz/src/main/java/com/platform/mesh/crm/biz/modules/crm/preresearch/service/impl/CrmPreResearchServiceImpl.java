package com.platform.mesh.crm.biz.modules.crm.preresearch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preresearch.domain.po.CrmPreResearch;
import com.platform.mesh.crm.biz.modules.crm.preresearch.mapper.CrmPreResearchMapper;
import com.platform.mesh.crm.biz.modules.crm.preresearch.service.ICrmPreResearchService;
import com.platform.mesh.crm.biz.modules.crm.preresearch.service.manual.CrmPreResearchServiceManual;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.domain.po.CrmPreResearchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系市场调研
 * @author 蝉鸣
 */
@Service
public class CrmPreResearchServiceImpl extends AppServiceAbstract<CrmPreResearchMapper, CrmPreResearch> implements ICrmPreResearchService  {

    @Autowired
    private CrmPreResearchServiceManual crmPreResearchServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系市场调研〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreResearchData> crmPreResearchDataList = BeanUtil.copyToList(dataList, CrmPreResearchData.class);
        //批量保存data表数据
        crmPreResearchServiceManual.addDbDataBatch(crmPreResearchDataList);
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
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmPreResearch::getScopeUserId,scopeUserId)
                .set(CrmPreResearch::getScopeOrgId,scopeOrgId)
                .in(CrmPreResearch::getId,dataIds)
                .update();
    }
}