package com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.domain.po.CrmSufDeliver;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.mapper.CrmSufDeliverMapper;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.ICrmSufDeliverService;
import com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.manual.CrmSufDeliverServiceManual;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.domain.po.CrmSufDeliverData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系标的交付
 * @author 蝉鸣
 */
@Service
public class CrmSufDeliverServiceImpl extends AppServiceAbstract<CrmSufDeliverMapper, CrmSufDeliver> implements ICrmSufDeliverService  {

    @Autowired
    private CrmSufDeliverServiceManual crmSufDeliverServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系标的交付〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmSufDeliverData> crmSufDeliverDataList = BeanUtil.copyToList(dataList, CrmSufDeliverData.class);
        //批量保存data表数据
        crmSufDeliverServiceManual.addDbDataBatch(crmSufDeliverDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系标的交付〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmSufDeliverServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmSufDeliver::getScopeUserId,scopeUserId)
                .set(CrmSufDeliver::getScopeOrgId,scopeOrgId)
                .in(CrmSufDeliver::getId,dataIds)
                .update();
        //修改DB Data
        crmSufDeliverServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}