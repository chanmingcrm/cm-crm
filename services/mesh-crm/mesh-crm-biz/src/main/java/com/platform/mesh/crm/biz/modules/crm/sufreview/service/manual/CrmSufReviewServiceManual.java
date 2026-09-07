package com.platform.mesh.crm.biz.modules.crm.sufreview.service.manual;

import cn.hutool.core.collection.CollUtil;
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
        CrmSufReviewData data = CollUtil.getFirst(sufReviewDataList);
        //删除旧数据
        crmSufReviewDataService.lambdaUpdate().eq(CrmSufReviewData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmSufReviewDataService.saveBatch(sufReviewDataList);
    }

}