package com.platform.mesh.crm.biz.modules.crm.precompetitor.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.domain.po.CrmPreCompetitorData;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.service.ICrmPreCompetitorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系竞品分析
 * @author 蝉鸣
 */
@Service
public class CrmPreCompetitorServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreCompetitorServiceManual.class);

    @Autowired
    private ICrmPreCompetitorDataService crmPreCompetitorDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preCompetitorData preCompetitorData
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreCompetitorData> preCompetitorData) {
        if(CollUtil.isEmpty(preCompetitorData)){
            return;
        }
        //批量新增信息
        crmPreCompetitorDataService.saveBatch(preCompetitorData);
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
        List<CrmPreCompetitorData> preCompetitorData = crmPreCompetitorDataService.lambdaQuery().eq(CrmPreCompetitorData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreCompetitorData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preCompetitorData)) {
            return;
        }
        AppUtil.editDbData(preCompetitorData, dataEditSimpDTO);
        if(CollUtil.isEmpty(preCompetitorData)){
            return;
        }
        crmPreCompetitorDataService.updateBatchById(preCompetitorData);
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
        crmPreCompetitorDataService.lambdaUpdate()
                .set(CrmPreCompetitorData::getScopeUserId, scopeUserId)
                .set(CrmPreCompetitorData::getScopeOrgId, scopeOrgId)
                .in(CrmPreCompetitorData::getDataId, dataIds)
                .update();
    }
}