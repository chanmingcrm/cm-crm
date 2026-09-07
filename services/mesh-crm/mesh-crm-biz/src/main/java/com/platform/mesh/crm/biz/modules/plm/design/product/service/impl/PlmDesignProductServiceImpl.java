package com.platform.mesh.crm.biz.modules.plm.design.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.enums.comp.FormTypeEnum;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.crm.api.modules.crm.constants.CrmConst;
import com.platform.mesh.crm.api.modules.plm.constant.PlmConst;
import com.platform.mesh.crm.biz.modules.plm.design.product.domain.po.PlmDesignProduct;
import com.platform.mesh.crm.biz.modules.plm.design.product.mapper.PlmDesignProductMapper;
import com.platform.mesh.crm.biz.modules.plm.design.product.service.IPlmDesignProductService;
import com.platform.mesh.crm.biz.modules.plm.design.product.service.manual.PlmDesignProductServiceManual;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.domain.po.PlmDesignProductData;
import com.platform.mesh.crm.biz.modules.plm.product.base.domain.po.PlmProduct;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 产品设计
 * @author 蝉鸣
 */
@Service
public class PlmDesignProductServiceImpl extends AppServiceAbstract<PlmDesignProductMapper, PlmDesignProduct> implements IPlmDesignProductService  {

    @Autowired
    private PlmDesignProductServiceManual plmDesignProductServiceManual;


    /**
     * 功能描述:
     * 〈新增产品设计〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmDesignProductData> plmDesignProductDataList = BeanUtil.copyToList(dataList, PlmDesignProductData.class);
        //批量保存data表数据
        plmDesignProductServiceManual.addDbDataBatch(plmDesignProductDataList);
    }
    
       /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbScopeBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(PlmDesignProduct::getScopeUserId,scopeUserId)
                .set(PlmDesignProduct::getScopeOrgId,scopeOrgId)
                .in(PlmDesignProduct::getId,dataIds)
                .update();
    }


    /**
     * 功能描述:
     * 〈自动初始化财务管理应收数据〉
     * @param dataPO dataPO
     * @param dataAddDTO dataAddDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(PlmDesignProduct dataPO, DataAddSimpDTO dataAddDTO) {
        Map<String, Object> docData = dataAddDTO.getDocData();
        //获取产品关联数据
        Long productId = AppUtil.getSingleColumnIdValue(CrmConst.PRODUCT, docData);
        dataPO.setProductId(productId);
        //获取产品大类关联数据
        Long categoryId = AppUtil.getSingleColumnIdValue(PlmConst.CATEGORY, docData);
        dataPO.setCategoryId(categoryId);
        //获取物料关联数据
        Long materialId = AppUtil.getSingleColumnIdValue(PlmConst.MATERIAL, docData);
        dataPO.setMaterialId(materialId);
        //获取供应商关联数据
        Long supplierId = AppUtil.getSingleColumnIdValue(CrmConst.SUPPLIER, docData);
        dataPO.setSupplierId(supplierId);
        //更新信息
        this.updateById(dataPO);
        //更新产品数据
        plmDesignProductServiceManual.updateProduct(dataPO);
    }

    /**
     * 功能描述:
     * 〈新增产品明细〉
     * @param dataPO dataPO
     * @param docMap docMap
     * @author 蝉鸣
     */
    @Override
    public void addProductDesign(PlmProduct dataPO, Map<String, Object> docMap) {
        Object object = docMap.get(CrmConst.PRODUCT_DESIGN);
        if(ObjectUtil.isEmpty(object)){
            return;
        }
        //获得module_id,
        AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleIdBySchema(SqlUtil.getTableName(PlmDesignProduct.class, TableName.class));
        if(ObjectUtil.isEmpty(appModuleBaseBO)){
            return;
        }
        List<AppFormColumnBO> columnBOS = this.getAppServiceManual().getFormColumnInfo(appModuleBaseBO.getId(), FormTypeEnum.FORM_ADD.getValue());
        if(CollUtil.isEmpty(columnBOS)){
            return;
        }
        List<Long> ids = this.lambdaQuery().eq(PlmDesignProduct::getProductId, dataPO.getId()).list().stream().map(PlmDesignProduct::getId).toList();
        //删除DB旧数据
        this.lambdaUpdate().eq(PlmDesignProduct::getProductId,dataPO.getId()).remove();
        //删除ES旧数据
        this.getAppServiceManual().deleteEsData(appModuleBaseBO.getModuleIndex(),ids);
        //保存Db
        int num = NumberConst.NUM_0;
        for (Object productDesign : JSONUtil.parseArray(object)) {
            num++;
            Map<String, Object> productMap = AppUtil.beanToMap(productDesign);
            productMap.put(AppUtil.getJsonName(CrmConst.PRODUCT),AppUtil.getColumnValue(CrmConst.PRODUCT,docMap));
            PlmDesignProduct design = BeanUtil.copyProperties(productDesign, PlmDesignProduct.class);
            design.setModuleId(appModuleBaseBO.getId());
            design.setProductId(dataPO.getId());
            design.setSupplierId(AppUtil.getSingleColumnIdValue(CrmConst.SUPPLIER,productMap));
            design.setDelFlag(YesOrNoEnum.YES.getValue());
            design.setDataMac(StrUtil.toString(num));
            if(ObjectUtil.isEmpty(design.getId())){
                //保存Db
                this.save(design);
                //保存Es
                this.getAppServiceManual().addEsData(appModuleBaseBO.getModuleIndex(),design,productMap);
            }else{
                //保存Db
                this.updateById(design);
                //修改Es
                this.getAppServiceManual().editEsData(appModuleBaseBO.getModuleIndex(),design,productMap);
            }
            //保存DbData
            List<PlmDesignProductData> dbDataSimp = getAppServiceManual().getDbDataSimp(appModuleBaseBO, design.getId(), PlmDesignProductData.class, columnBOS, productMap);
            //批量保存data表数据
            plmDesignProductServiceManual.addDbDataBatch(dbDataSimp);
        }
    }

    /**
     * 功能描述:
     * 〈获取产品明细〉
     * @param productId productId
     * @author 蝉鸣
     */
    @Override
    public List<Object> getProductDesignList(Long productId) {
        //1通过SQL形式，2通过ES形式 参考OMS 订单
        List<PlmDesignProduct> designProducts = this.lambdaQuery().eq(PlmDesignProduct::getProductId, productId).list();
        if(CollUtil.isEmpty(designProducts)){
            return new ArrayList<>();
        }
        List<Long> ids = designProducts.stream().map(PlmDesignProduct::getId).toList();
        List<PlmDesignProductData> designProductData = plmDesignProductServiceManual.getProductDesignData(ids);
        List<Object> result = CollUtil.newArrayList();
        for (PlmDesignProduct product : designProducts) {
            Map<String, Object> toMap = AppUtil.beanToMap(product);
            Map<String, Object> dataMap = designProductData.stream()
                    .filter(p -> p.getDataId().equals(product.getId()))
                    .collect(Collectors.toMap(PlmDesignProductData::getColumnMac, PlmDesignProductData::getDataValue));
            toMap.putAll(dataMap);
            result.add(toMap);
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈删除产品明细〉
     * @param productIds productIds
     * @author 蝉鸣
     */
    @Override
    public void delProductDesignList(List<Long> productIds) {
        if(CollUtil.isEmpty(productIds)){
            return;
        }
        List<PlmDesignProduct> designProducts = this.lambdaQuery().eq(PlmDesignProduct::getProductId, productIds).list();
        if(CollUtil.isEmpty(designProducts)){
            return;
        }
        PlmDesignProduct designProduct = CollUtil.getFirst(designProducts);
        //获得module_id,
        AppModuleBaseBO appModuleBaseBO = this.getAppServiceManual().getModuleInfo(designProduct.getModuleId());
        if(ObjectUtil.isEmpty(appModuleBaseBO)){
            return;
        }
        List<Long> ids = designProducts.stream().map(PlmDesignProduct::getId).toList();
        //删除产品明细Data
        //删除Es数据
        this.getAppServiceManual().deleteEsData(appModuleBaseBO.getModuleIndex(),ids);
        //删除data数据
        plmDesignProductServiceManual.delProductDesignData(ids);
        //删除db数据
        this.lambdaUpdate().in(PlmDesignProduct::getId, ids).remove();
    }
}