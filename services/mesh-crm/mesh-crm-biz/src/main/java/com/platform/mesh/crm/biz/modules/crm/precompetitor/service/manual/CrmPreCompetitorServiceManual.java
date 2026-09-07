package com.platform.mesh.crm.biz.modules.crm.precompetitor.service.manual;

import cn.hutool.core.collection.CollUtil;
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
     * @param preCompetitorDataList preCompetitorDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreCompetitorData> preCompetitorDataList) {
        if(CollUtil.isEmpty(preCompetitorDataList)){
            return;
        }
        CrmPreCompetitorData data = CollUtil.getFirst(preCompetitorDataList);
        //删除旧数据
        crmPreCompetitorDataService.lambdaUpdate().eq(CrmPreCompetitorData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreCompetitorDataService.saveBatch(preCompetitorDataList);
    }

}