package com.platform.mesh.crm.biz.modules.crm.predrainage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.predrainage.domain.po.CrmPreDrainage;
import com.platform.mesh.crm.biz.modules.crm.predrainage.mapper.CrmPreDrainageMapper;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.ICrmPreDrainageService;
import com.platform.mesh.crm.biz.modules.crm.predrainage.service.manual.CrmPreDrainageServiceManual;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动引流
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageServiceImpl extends AppServiceAbstract<CrmPreDrainageMapper, CrmPreDrainage> implements ICrmPreDrainageService  {

    @Autowired
    private CrmPreDrainageServiceManual crmPreDrainageServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系活动引流〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreDrainageData> crmPreDrainageDataList = BeanUtil.copyToList(dataList, CrmPreDrainageData.class);
        //批量保存data表数据
        crmPreDrainageServiceManual.addDbDataBatch(crmPreDrainageDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系活动引流〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreDrainageServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreDrainage::getScopeUserId,scopeUserId)
                .set(CrmPreDrainage::getScopeOrgId,scopeOrgId)
                .in(CrmPreDrainage::getId,dataIds)
                .update();
        //修改DB Data
        crmPreDrainageServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}