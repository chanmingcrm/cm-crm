package com.platform.mesh.crm.biz.modules.crm.sufopinion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.domain.po.CrmSufOpinion;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.mapper.CrmSufOpinionMapper;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.service.ICrmSufOpinionService;
import com.platform.mesh.crm.biz.modules.crm.sufopinion.service.manual.CrmSufOpinionServiceManual;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系意见评价
 * @author 蝉鸣
 */
@Service
public class CrmSufOpinionServiceImpl extends AppServiceAbstract<CrmSufOpinionMapper, CrmSufOpinion> implements ICrmSufOpinionService  {

    @Autowired
    private CrmSufOpinionServiceManual crmSufOpinionServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系意见评价〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmSufOpinionData> crmSufOpinionDataList = BeanUtil.copyToList(dataList, CrmSufOpinionData.class);
        //批量保存data表数据
        crmSufOpinionServiceManual.addDbDataBatch(crmSufOpinionDataList);
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
                .set(CrmSufOpinion::getScopeUserId,scopeUserId)
                .set(CrmSufOpinion::getScopeOrgId,scopeOrgId)
                .in(CrmSufOpinion::getId,dataIds)
                .update();
    }
}