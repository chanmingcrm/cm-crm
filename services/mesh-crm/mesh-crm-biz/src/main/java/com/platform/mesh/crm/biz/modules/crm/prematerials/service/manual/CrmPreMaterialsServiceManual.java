package com.platform.mesh.crm.biz.modules.crm.prematerials.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.domain.po.CrmPreMaterialsData;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.service.ICrmPreMaterialsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系活动物料
 * @author 蝉鸣
 */
@Service
public class CrmPreMaterialsServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreMaterialsServiceManual.class);


    @Autowired
    private ICrmPreMaterialsDataService crmPreMaterialsDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preMaterialsDataList preMaterialsDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreMaterialsData> preMaterialsDataList) {
        if(CollUtil.isEmpty(preMaterialsDataList)){
            return;
        }
        //批量新增信息
        crmPreMaterialsDataService.saveBatch(preMaterialsDataList);
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
        List<CrmPreMaterialsData> preMaterialsDataList = crmPreMaterialsDataService.lambdaQuery().eq(CrmPreMaterialsData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreMaterialsData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preMaterialsDataList)) {
            return;
        }
        AppUtil.editDbData(preMaterialsDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preMaterialsDataList)){
            return;
        }
        crmPreMaterialsDataService.updateBatchById(preMaterialsDataList);
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
        crmPreMaterialsDataService.lambdaUpdate()
                .set(CrmPreMaterialsData::getScopeUserId, scopeUserId)
                .set(CrmPreMaterialsData::getScopeOrgId, scopeOrgId)
                .in(CrmPreMaterialsData::getDataId, dataIds)
                .update();
    }

}