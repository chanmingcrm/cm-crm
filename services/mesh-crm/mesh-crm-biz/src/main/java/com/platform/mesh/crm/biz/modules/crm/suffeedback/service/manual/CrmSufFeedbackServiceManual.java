package com.platform.mesh.crm.biz.modules.crm.suffeedback.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.domain.po.CrmSufFeedbackData;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.service.ICrmSufFeedbackDataService;
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
        CrmSufFeedbackData data = CollUtil.getFirst(sufFeedbackDataList);
        //删除旧数据
        crmSufFeedbackDataService.lambdaUpdate().eq(CrmSufFeedbackData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmSufFeedbackDataService.saveBatch(sufFeedbackDataList);
    }

}