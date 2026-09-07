package com.platform.mesh.crm.biz.modules.crm.sufopinion.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.service.ICrmSufOpinionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系意见评价
 * @author 蝉鸣
 */
@Service
public class CrmSufOpinionServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufOpinionServiceManual.class);


    @Autowired
    private ICrmSufOpinionDataService crmSufOpinionDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufOpinionDataList sufOpinionDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufOpinionData> sufOpinionDataList) {
        if(CollUtil.isEmpty(sufOpinionDataList)){
            return;
        }
        CrmSufOpinionData data = CollUtil.getFirst(sufOpinionDataList);
        //删除旧数据
        crmSufOpinionDataService.lambdaUpdate().eq(CrmSufOpinionData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmSufOpinionDataService.saveBatch(sufOpinionDataList);
    }

}