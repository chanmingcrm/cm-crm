package com.platform.mesh.crm.biz.modules.crm.prematerials.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.domain.po.CrmPreMaterialsData;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.service.ICrmPreMaterialsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系活动物料
 * @author 蝉鸣
 */
@Service
public class CrmPreMaterialsServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreMaterialsServiceManual.class);


    @Autowired
    private ICrmPreMaterialsDataService crmPreMaterialsDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preMaterialsDataList preMaterialsDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreMaterialsData> preMaterialsDataList) {
        if(CollUtil.isEmpty(preMaterialsDataList)){
            return;
        }
        CrmPreMaterialsData data = CollUtil.getFirst(preMaterialsDataList);
        //删除旧数据
        crmPreMaterialsDataService.lambdaUpdate().eq(CrmPreMaterialsData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreMaterialsDataService.saveBatch(preMaterialsDataList);
    }

}