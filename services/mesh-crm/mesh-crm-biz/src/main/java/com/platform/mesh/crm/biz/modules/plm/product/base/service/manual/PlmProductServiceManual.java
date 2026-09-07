package com.platform.mesh.crm.biz.modules.plm.product.base.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.plm.design.product.service.IPlmDesignProductService;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.po.PlmProduct;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.domain.po.PlmProductData;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.service.IPlmProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 供应链产品
 * @author 蝉鸣
 */
@Service
public class PlmProductServiceManual {

    private final static Logger log = LoggerFactory.getLogger(PlmProductServiceManual.class);

    @Autowired
    private IPlmProductDataService plmProductDataService;

    @Autowired
    private IPlmDesignProductService plmDesignProductService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param productDataList productDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmProductData> productDataList) {
        if(CollUtil.isEmpty(productDataList)){
            return;
        }
        PlmProductData data = CollUtil.getFirst(productDataList);
        //删除旧数据
        plmProductDataService.lambdaUpdate().eq(PlmProductData::getDataId,data.getDataId()).remove();
        //批量新增信息
        plmProductDataService.saveBatch(productDataList);
    }

    /**
     * 功能描述:
     * 〈保存产品下的产品明细数据〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    public void saveProductDesignList(PlmProduct dataPO, DataAddSimpDTO dataAddDTO) {
        //将当前信息冗余
        JSONArray array = JSONUtil.createArray();
        JSONObject product = JSONUtil.createObj();
        product.set(StrConst.ID,dataPO.getId());
        product.set(StrConst.NAME,dataPO.getDataName());
        array.add(product);
        dataAddDTO.getDocData().put(AppUtil.getJsonName(CrmConst.PRODUCT),array);
        plmDesignProductService.addProductDesign(dataPO,dataAddDTO.getDocData());
        //移除子表数据，不再保存当前数据中
        dataAddDTO.getDocData().remove(CrmConst.PRODUCT_DESIGN);
    }

    /**
     * 功能描述:
     * 〈获取产品下的产品明细数据〉
     * @param productId productId
     * @author 蝉鸣
     */
    public List<Object> getProductDesignList(Long productId) {
        return plmDesignProductService.getProductDesignList(productId);
    }

    /**
     * 功能描述:
     * 〈删除产品下的产品明细数据〉
     * @param productIds productIds
     * @author 蝉鸣
     */
    public void delProductDesignList(List<Long> productIds) {
        plmDesignProductService.delProductDesignList(productIds);
    }

}