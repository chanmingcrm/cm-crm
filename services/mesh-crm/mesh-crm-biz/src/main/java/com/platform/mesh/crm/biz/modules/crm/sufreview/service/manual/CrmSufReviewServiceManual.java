package com.platform.mesh.crm.biz.modules.crm.sufreview.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.domain.po.CrmSufReviewData;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.service.ICrmSufReviewDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系复盘总结
 * @author 蝉鸣
 */
@Service
public class CrmSufReviewServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufReviewServiceManual.class);


    @Autowired
    private ICrmSufReviewDataService crmSufReviewDataService;

    
    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufReviewDataList sufReviewDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufReviewData> sufReviewDataList) {
        if(CollUtil.isEmpty(sufReviewDataList)){
            return;
        }
        //批量新增信息
        crmSufReviewDataService.saveBatch(sufReviewDataList);
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
        List<CrmSufReviewData> sufReviewDataList = crmSufReviewDataService.lambdaQuery().eq(CrmSufReviewData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmSufReviewData::getDataId, dataId).list();
        if(CollUtil.isEmpty(sufReviewDataList)) {
            return;
        }
        AppUtil.editDbData(sufReviewDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(sufReviewDataList)){
            return;
        }
        crmSufReviewDataService.updateBatchById(sufReviewDataList);
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
        crmSufReviewDataService.lambdaUpdate()
                .set(CrmSufReviewData::getScopeUserId, scopeUserId)
                .set(CrmSufReviewData::getScopeOrgId, scopeOrgId)
                .in(CrmSufReviewData::getDataId, dataIds)
                .update();
    }
}