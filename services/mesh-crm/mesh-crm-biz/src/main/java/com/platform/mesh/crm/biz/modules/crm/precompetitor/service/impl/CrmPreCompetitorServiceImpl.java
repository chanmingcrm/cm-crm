package com.platform.mesh.crm.biz.modules.crm.precompetitor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.domain.po.CrmPreCompetitor;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.mapper.CrmPreCompetitorMapper;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.service.ICrmPreCompetitorService;
import com.platform.mesh.crm.biz.modules.crm.precompetitor.service.manual.CrmPreCompetitorServiceManual;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.domain.po.CrmPreCompetitorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系竞品分析
 * @author 蝉鸣
 */
@Service
public class CrmPreCompetitorServiceImpl extends AppServiceAbstract<CrmPreCompetitorMapper, CrmPreCompetitor> implements ICrmPreCompetitorService  {

    @Autowired
    private CrmPreCompetitorServiceManual crmPreCompetitorServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系竞品分析〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreCompetitorData> crmOnProgrammeDataList = BeanUtil.copyToList(dataList, CrmPreCompetitorData.class);
        //批量保存data表数据
        crmPreCompetitorServiceManual.addDbDataBatch(crmOnProgrammeDataList);
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
                .set(CrmPreCompetitor::getScopeUserId,scopeUserId)
                .set(CrmPreCompetitor::getScopeOrgId,scopeOrgId)
                .in(CrmPreCompetitor::getId,dataIds)
                .update();
    }
}