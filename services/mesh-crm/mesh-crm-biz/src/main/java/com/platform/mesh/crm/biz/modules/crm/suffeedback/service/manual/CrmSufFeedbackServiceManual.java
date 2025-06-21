package com.platform.mesh.crm.biz.modules.crm.suffeedback.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.domain.po.CrmSufFeedbackData;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.service.ICrmSufFeedbackDataService;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系市场反馈
 * @author 蝉鸣
 */
@Service
public class CrmSufFeedbackServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufFeedbackServiceManual.class);


    @Autowired
    private ICrmSufFeedbackDataService crmSufFeedbackDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufFeedbackDataList sufFeedbackDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufFeedbackData> sufFeedbackDataList) {
        if(CollUtil.isEmpty(sufFeedbackDataList)){
            return;
        }
        //批量新增信息
        crmSufFeedbackDataService.saveBatch(sufFeedbackDataList);
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
        List<CrmSufFeedbackData> sufFeedbackDataList = crmSufFeedbackDataService.lambdaQuery().eq(CrmSufFeedbackData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmSufFeedbackData::getDataId, dataId).list();
        if(CollUtil.isEmpty(sufFeedbackDataList)) {
            return;
        }
        AppUtil.editDbData(sufFeedbackDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(sufFeedbackDataList)){
            return;
        }
        crmSufFeedbackDataService.updateBatchById(sufFeedbackDataList);
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
        crmSufFeedbackDataService.lambdaUpdate()
                .set(CrmSufFeedbackData::getScopeUserId, scopeUserId)
                .set(CrmSufFeedbackData::getScopeOrgId, scopeOrgId)
                .in(CrmSufFeedbackData::getDataId, dataIds)
                .update();
    }
}