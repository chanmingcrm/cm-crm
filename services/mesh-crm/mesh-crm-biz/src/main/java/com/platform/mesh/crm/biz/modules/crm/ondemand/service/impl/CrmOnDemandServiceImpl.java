package com.platform.mesh.crm.biz.modules.crm.ondemand.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.ondemand.domain.po.CrmOnDemand;
import com.platform.mesh.crm.biz.modules.crm.ondemand.mapper.CrmOnDemandMapper;
import com.platform.mesh.crm.biz.modules.crm.ondemand.service.ICrmOnDemandService;
import com.platform.mesh.crm.biz.modules.crm.ondemand.service.manual.CrmOnDemandServiceManual;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.domain.po.CrmOnDemandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系需求整理
 * @author 蝉鸣
 */
@Service
public class CrmOnDemandServiceImpl extends AppServiceAbstract<CrmOnDemandMapper, CrmOnDemand> implements ICrmOnDemandService  {

    @Autowired
    private CrmOnDemandServiceManual crmOnDemandServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系需求整理〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnDemandData> crmOnDemandDataList = BeanUtil.copyToList(dataList, CrmOnDemandData.class);
        //批量保存data表数据
        crmOnDemandServiceManual.addDbDataBatch(crmOnDemandDataList);
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
                .set(CrmOnDemand::getScopeUserId,scopeUserId)
                .set(CrmOnDemand::getScopeOrgId,scopeOrgId)
                .in(CrmOnDemand::getId,dataIds)
                .update();
    }
}