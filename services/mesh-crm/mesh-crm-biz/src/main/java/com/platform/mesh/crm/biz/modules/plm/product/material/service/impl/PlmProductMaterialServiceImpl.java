package com.platform.mesh.crm.biz.modules.plm.product.material.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.product.material.domain.po.PlmProductMaterial;
import com.platform.mesh.crm.biz.modules.plm.product.material.mapper.PlmProductMaterialMapper;
import com.platform.mesh.crm.biz.modules.plm.product.material.service.IPlmProductMaterialService;
import com.platform.mesh.crm.biz.modules.plm.product.material.service.manual.PlmProductMaterialServiceManual;
import com.platform.mesh.crm.biz.modules.plm.product.materialdata.domain.po.PlmProductMaterialData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，封装转换方法在Manual中进行
 * @description 产品物料
 * @author 蝉鸣
 */
@Service
public class PlmProductMaterialServiceImpl extends AppServiceAbstract<PlmProductMaterialMapper, PlmProductMaterial> implements IPlmProductMaterialService {

    @Autowired
    private PlmProductMaterialServiceManual plmProductmaterialServiceManual;

    /**
     * 功能描述:
     * 【新增产品物料动态字段数据】
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmProductMaterialData> dataPOList = BeanUtil.copyToList(dataList, PlmProductMaterialData.class);
        plmProductmaterialServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 【转移产品物料数据权限必须重写】
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(PlmProductMaterial::getScopeUserId, scopeUserId)
                .set(PlmProductMaterial::getScopeOrgId, scopeOrgId)
                .in(PlmProductMaterial::getId, dataIds)
                .update();
    }
}
