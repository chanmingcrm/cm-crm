package com.platform.mesh.crm.biz.modules.crm.sufdeliver.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.domain.po.CrmSufDeliverData;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.service.ICrmSufDeliverDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系标的交付
 * @author 蝉鸣
 */
@Service
public class CrmSufDeliverServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufDeliverServiceManual.class);


    @Autowired
    private ICrmSufDeliverDataService crmSufDeliverDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufDeliverDataList sufDeliverDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufDeliverData> sufDeliverDataList) {
        if(CollUtil.isEmpty(sufDeliverDataList)){
            return;
        }
        CrmSufDeliverData data = CollUtil.getFirst(sufDeliverDataList);
        //删除旧数据
        crmSufDeliverDataService.lambdaUpdate().eq(CrmSufDeliverData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmSufDeliverDataService.saveBatch(sufDeliverDataList);
    }

}