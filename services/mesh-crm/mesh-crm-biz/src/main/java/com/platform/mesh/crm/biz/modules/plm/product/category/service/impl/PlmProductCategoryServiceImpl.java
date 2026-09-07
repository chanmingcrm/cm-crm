package com.platform.mesh.crm.biz.modules.plm.product.category.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.product.category.domain.po.PlmProductCategory;
import com.platform.mesh.crm.biz.modules.plm.product.category.mapper.PlmProductCategoryMapper;
import com.platform.mesh.crm.biz.modules.plm.product.category.service.IPlmProductCategoryService;
import com.platform.mesh.crm.biz.modules.plm.product.category.service.manual.PlmProductCategoryServiceManual;
import com.platform.mesh.crm.biz.modules.plm.product.categorydata.domain.po.PlmProductCategoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，封装转换方法在Manual中进行
 * @description 产品大类
 * @author 蝉鸣
 */
@Service
public class PlmProductCategoryServiceImpl extends AppServiceAbstract<PlmProductCategoryMapper, PlmProductCategory> implements IPlmProductCategoryService {

    @Autowired
    private PlmProductCategoryServiceManual plmProductbigcategoryServiceManual;

    /**
     * 功能描述:
     * 【新增产品大类动态字段数据】
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmProductCategoryData> dataPOList = BeanUtil.copyToList(dataList, PlmProductCategoryData.class);
        plmProductbigcategoryServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 【转移产品大类数据权限必须重写】
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(PlmProductCategory::getScopeUserId, scopeUserId)
                .set(PlmProductCategory::getScopeOrgId, scopeOrgId)
                .in(PlmProductCategory::getId, dataIds)
                .update();
    }
}
