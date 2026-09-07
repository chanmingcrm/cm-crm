package com.platform.mesh.crm.biz.modules.plm.design.product.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.design.product.domain.po.PlmDesignProduct;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.domain.po.PlmDesignProductData;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.service.IPlmDesignProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入不整洁
 * @description 产品设计
 * @author 蝉鸣
 */
@Service
public class PlmDesignProductServiceManual{

    private final static Logger log = LoggerFactory.getLogger(PlmDesignProductServiceManual.class);

    @Autowired
    private IPlmDesignProductDataService plmDesignProductDataService;

    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param plmDesignProductDataList plmDesignProductDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmDesignProductData> plmDesignProductDataList) {
        if(CollUtil.isEmpty(plmDesignProductDataList)){
            return;
        }
        PlmDesignProductData data = CollUtil.getFirst(plmDesignProductDataList);
        //删除旧数据
        plmDesignProductDataService.lambdaUpdate().eq(PlmDesignProductData::getDataId,data.getDataId()).remove();
        //批量新增信息
        plmDesignProductDataService.saveBatch(plmDesignProductDataList);
    }

    /**
     * 功能描述:
     * 〈获取产品明细〉
     * @param productIds productIds
     * @author 蝉鸣
     */
    public List<PlmDesignProductData> getProductDesignData(List<Long> productIds) {
        return plmDesignProductDataService.lambdaQuery().in(PlmDesignProductData::getDataId, productIds).list();
    }

    /**
     * 功能描述:
     * 〈删除产品明细〉
     * @param productIds productIds
     * @author 蝉鸣
     */
    public void delProductDesignData(List<Long> productIds) {
        plmDesignProductDataService.lambdaUpdate().in(PlmDesignProductData::getDataId, productIds).remove();
    }

    /**
     * 功能描述:
     * 〈更新产品明细〉
     * @param dataPO dataPO
     * @author 蝉鸣
     */
    public void updateProduct(PlmDesignProduct dataPO) {
    }
}