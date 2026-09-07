package com.platform.mesh.crm.biz.modules.plm.product.material.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.product.materialdata.domain.po.PlmProductMaterialData;
import com.platform.mesh.crm.biz.modules.plm.product.materialdata.service.IPlmProductMaterialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service，Manual供Service引入，避免循环依赖
 * @description 产品物料
 * @author 蝉鸣
 */
@Service
public class PlmProductMaterialServiceManual {

    @Autowired
    private IPlmProductMaterialDataService plmProductmaterialDataService;

    /**
     * 功能描述:
     * 【DB Data 数据批量保存】
     * @param dataList dataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmProductMaterialData> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        PlmProductMaterialData data = CollUtil.getFirst(dataList);
        plmProductmaterialDataService.lambdaUpdate().eq(PlmProductMaterialData::getDataId, data.getDataId()).remove();
        plmProductmaterialDataService.saveBatch(dataList);
    }
}
