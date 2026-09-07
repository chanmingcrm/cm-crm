package com.platform.mesh.crm.biz.modules.plm.product.category.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.product.categorydata.domain.po.PlmProductCategoryData;
import com.platform.mesh.crm.biz.modules.plm.product.categorydata.service.IPlmProductCategoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service，Manual供Service引入，避免循环依赖
 * @description 产品大类
 * @author 蝉鸣
 */
@Service
public class PlmProductCategoryServiceManual {

    @Autowired
    private IPlmProductCategoryDataService plmProductbigcategoryDataService;

    /**
     * 功能描述:
     * 【DB Data 数据批量保存】
     * @param dataList dataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmProductCategoryData> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        PlmProductCategoryData data = CollUtil.getFirst(dataList);
        plmProductbigcategoryDataService.lambdaUpdate().eq(PlmProductCategoryData::getDataId, data.getDataId()).remove();
        plmProductbigcategoryDataService.saveBatch(dataList);
    }
}
