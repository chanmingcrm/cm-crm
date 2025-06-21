package com.platform.mesh.crm.biz.modules.crm.preresearch.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.domain.po.CrmPreResearchData;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.service.ICrmPreResearchDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系市场调研
 * @author 蝉鸣
 */
@Service
public class CrmPreResearchServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreResearchServiceManual.class);


    @Autowired
    private ICrmPreResearchDataService crmPreResearchDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preResearchDataList preResearchDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreResearchData> preResearchDataList) {
        if(CollUtil.isEmpty(preResearchDataList)){
            return;
        }
        //批量新增信息
        crmPreResearchDataService.saveBatch(preResearchDataList);
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
        List<CrmPreResearchData> preResearchDataList = crmPreResearchDataService.lambdaQuery().eq(CrmPreResearchData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreResearchData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preResearchDataList)) {
            return;
        }
        AppUtil.editDbData(preResearchDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preResearchDataList)){
            return;
        }
        crmPreResearchDataService.updateBatchById(preResearchDataList);
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
        crmPreResearchDataService.lambdaUpdate()
                .set(CrmPreResearchData::getScopeUserId, scopeUserId)
                .set(CrmPreResearchData::getScopeOrgId, scopeOrgId)
                .in(CrmPreResearchData::getDataId, dataIds)
                .update();
    }

}