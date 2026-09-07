package com.platform.mesh.crm.biz.modules.crm.preproduct.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.domain.po.CrmPreProductData;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.service.ICrmPreProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系展示产品
 * @author 蝉鸣
 */
@Service
public class CrmPreProductServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreProductServiceManual.class);


    @Autowired
    private ICrmPreProductDataService crmPreProductDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preProductDataList preProductDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreProductData> preProductDataList) {
        if(CollUtil.isEmpty(preProductDataList)){
            return;
        }
        CrmPreProductData data = CollUtil.getFirst(preProductDataList);
        //删除旧数据
        crmPreProductDataService.lambdaUpdate().eq(CrmPreProductData::getDataId,data.getDataId()).remove();
        //批量新增信息
        crmPreProductDataService.saveBatch(preProductDataList);
    }

}