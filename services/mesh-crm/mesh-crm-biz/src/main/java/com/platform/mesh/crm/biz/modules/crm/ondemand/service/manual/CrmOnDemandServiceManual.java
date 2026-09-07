package com.platform.mesh.crm.biz.modules.crm.ondemand.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.domain.po.CrmOnDemandData;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.service.ICrmOnDemandDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系需求整理
 * @author 蝉鸣
 */
@Service
public class CrmOnDemandServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnDemandServiceManual.class);

    @Autowired
    private ICrmOnDemandDataService crmOnDemandDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onDemandDataList onDemandDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnDemandData> onDemandDataList) {
        if(CollUtil.isEmpty(onDemandDataList)){
            return;
        }
        CrmOnDemandData data = CollUtil.getFirst(onDemandDataList);
        //删除旧数据
        crmOnDemandDataService.lambdaUpdate().eq(CrmOnDemandData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmOnDemandDataService.saveBatch(onDemandDataList);
    }

}