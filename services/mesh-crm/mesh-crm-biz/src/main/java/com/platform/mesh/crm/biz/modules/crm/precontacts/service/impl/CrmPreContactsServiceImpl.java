package com.platform.mesh.crm.biz.modules.crm.precontacts.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precontacts.domain.po.CrmPreContacts;
import com.platform.mesh.crm.biz.modules.crm.precontacts.mapper.CrmPreContactsMapper;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.ICrmPreContactsService;
import com.platform.mesh.crm.biz.modules.crm.precontacts.service.manual.CrmPreContactsServiceManual;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系联系人
 * @author 蝉鸣
 */
@Service
public class CrmPreContactsServiceImpl extends AppServiceAbstract<CrmPreContactsMapper, CrmPreContacts> implements ICrmPreContactsService  {

    @Autowired
    private CrmPreContactsServiceManual crmPreContactsServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系联系人〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreContactsData> crmPreContactsDataList = BeanUtil.copyToList(dataList, CrmPreContactsData.class);
        //批量保存data表数据
        crmPreContactsServiceManual.addDbDataBatch(crmPreContactsDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系联系人〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreContactsServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreContacts::getScopeUserId,scopeUserId)
                .set(CrmPreContacts::getScopeOrgId,scopeOrgId)
                .in(CrmPreContacts::getId,dataIds)
                .update();
        //修改DB Data
        crmPreContactsServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}