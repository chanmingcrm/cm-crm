package com.platform.mesh.crm.biz.modules.crm.preproposal.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.domain.po.CrmPreProposalData;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.service.ICrmPreProposalDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系提案报价
 * @author 蝉鸣
 */
@Service
public class CrmPreProposalServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreProposalServiceManual.class);


    @Autowired
    private ICrmPreProposalDataService crmPreProposalDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preProposalDataList preProposalDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreProposalData> preProposalDataList) {
        if(CollUtil.isEmpty(preProposalDataList)){
            return;
        }
        //批量新增信息
        crmPreProposalDataService.saveBatch(preProposalDataList);
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
        List<CrmPreProposalData> preProposalDataList = crmPreProposalDataService.lambdaQuery().eq(CrmPreProposalData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreProposalData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preProposalDataList)) {
            return;
        }
        AppUtil.editDbData(preProposalDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preProposalDataList)){
            return;
        }
        crmPreProposalDataService.updateBatchById(preProposalDataList);
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
        crmPreProposalDataService.lambdaUpdate()
                .set(CrmPreProposalData::getScopeUserId, scopeUserId)
                .set(CrmPreProposalData::getScopeOrgId, scopeOrgId)
                .in(CrmPreProposalData::getDataId, dataIds)
                .update();
    }
}