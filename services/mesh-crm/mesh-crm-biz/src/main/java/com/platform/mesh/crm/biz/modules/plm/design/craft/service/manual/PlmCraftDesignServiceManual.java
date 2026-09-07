package com.platform.mesh.crm.biz.modules.plm.design.craft.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.design.craftdata.domain.po.PlmCraftDesignData;
import com.platform.mesh.crm.biz.modules.plm.design.craftdata.service.IPlmCraftDesignDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service，Manual供Service引入，避免循环依赖
 * @description 工艺设计
 * @author 蝉鸣
 */
@Service
public class PlmCraftDesignServiceManual {

    @Autowired
    private IPlmCraftDesignDataService plmCraftdesignDataService;

    /**
     * 功能描述:
     * 【DB Data 数据批量保存】
     * @param dataList dataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmCraftDesignData> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        PlmCraftDesignData data = CollUtil.getFirst(dataList);
        plmCraftdesignDataService.lambdaUpdate().eq(PlmCraftDesignData::getDataId, data.getDataId()).remove();
        plmCraftdesignDataService.saveBatch(dataList);
    }
}
