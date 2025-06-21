package com.platform.mesh.crm.biz.modules.crm.precontacts.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.service.ICrmPreContactsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系联系人
 * @author 蝉鸣
 */
@Service
public class CrmPreContactsServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmPreContactsServiceManual.class);

    @Autowired
    private ICrmPreContactsDataService crmPreContactsDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preContactsDataList preContactsDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreContactsData> preContactsDataList) {
        if(CollUtil.isEmpty(preContactsDataList)){
            return;
        }
        //批量新增信息
        crmPreContactsDataService.saveBatch(preContactsDataList);
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量修改〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<CrmPreContactsData> preContactsDataList = crmPreContactsDataService.lambdaQuery().eq(CrmPreContactsData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreContactsData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preContactsDataList)) {
            return;
        }
        AppUtil.editDbData(preContactsDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preContactsDataList)){
            return;
        }
        crmPreContactsDataService.updateBatchById(preContactsDataList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public void transDbDataBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        if(CollUtil.isEmpty(dataIds) || ObjectUtil.isEmpty(scopeUserId) || ObjectUtil.isEmpty(scopeOrgId)) {
            return;
        }
        crmPreContactsDataService.lambdaUpdate()
                .set(CrmPreContactsData::getScopeUserId, scopeUserId)
                .set(CrmPreContactsData::getScopeOrgId, scopeOrgId)
                .in(CrmPreContactsData::getDataId, dataIds)
                .update();
    }
}