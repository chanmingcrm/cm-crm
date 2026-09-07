package com.platform.mesh.crm.biz.modules.plm.design.product.service;

import com.platform.mesh.app.api.modules.app.service.IAppService;
import com.platform.mesh.crm.biz.modules.plm.design.product.domain.po.PlmDesignProduct;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.po.PlmProduct;

import java.util.List;
import java.util.Map;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 产品设计信息
 * @author 蝉鸣
 */
public interface IPlmDesignProductService extends IAppService<PlmDesignProduct> {

    /**
     * 功能描述:
     * 〈新增产品明细〉
     * @param dataPO dataPO
     * @param docData docData
     * @author 蝉鸣
     */
    void addProductDesign(PlmProduct dataPO, Map<String, Object> docData);

    /**
     * 功能描述:
     * 〈获取产品明细〉
     * @param productId productId
     * @author 蝉鸣
     */
    List<Object> getProductDesignList(Long productId);

    /**
     * 功能描述:
     * 〈删除产品明细〉
     * @param productIds productIds
     * @author 蝉鸣
     */
    void delProductDesignList(List<Long> productIds);
}