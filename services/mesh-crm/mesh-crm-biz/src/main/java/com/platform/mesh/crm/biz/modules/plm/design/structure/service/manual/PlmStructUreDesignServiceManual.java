package com.platform.mesh.crm.biz.modules.plm.design.structure.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.design.structuredata.domain.po.PlmStructUreDesignData;
import com.platform.mesh.crm.biz.modules.plm.design.structuredata.service.IPlmStructUreDesignDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service，Manual供Service引入，避免循环依赖
 * @description 结构设计
 * @author 蝉鸣
 */
@Service
public class PlmStructUreDesignServiceManual {

    @Autowired
    private IPlmStructUreDesignDataService plmStructuredesignDataService;

    /**
     * 功能描述:
     * 【DB Data 数据批量保存】
     * @param dataList dataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmStructUreDesignData> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        PlmStructUreDesignData data = CollUtil.getFirst(dataList);
        plmStructuredesignDataService.lambdaUpdate().eq(PlmStructUreDesignData::getDataId, data.getDataId()).remove();
        plmStructuredesignDataService.saveBatch(dataList);
    }
}
