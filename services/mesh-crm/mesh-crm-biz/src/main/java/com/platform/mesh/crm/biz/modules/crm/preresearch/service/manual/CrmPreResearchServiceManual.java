package com.platform.mesh.crm.biz.modules.crm.preresearch.service.manual;

import cn.hutool.core.collection.CollUtil;
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
        CrmPreResearchData data = CollUtil.getFirst(preResearchDataList);
        //删除旧数据
        crmPreResearchDataService.lambdaUpdate().eq(CrmPreResearchData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreResearchDataService.saveBatch(preResearchDataList);
    }

}