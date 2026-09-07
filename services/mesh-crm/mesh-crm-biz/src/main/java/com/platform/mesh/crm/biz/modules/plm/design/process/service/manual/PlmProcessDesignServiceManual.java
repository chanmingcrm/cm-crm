package com.platform.mesh.crm.biz.modules.plm.design.process.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.domain.po.PlmProcessDesignData;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.service.IPlmProcessDesignDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service，Manual供Service引入，避免循环依赖
 * @description 工序设计
 * @author 蝉鸣
 */
@Service
public class PlmProcessDesignServiceManual {

    @Autowired
    private IPlmProcessDesignDataService plmProcessdesignDataService;

    /**
     * 功能描述:
     * 【DB Data 数据批量保存】
     * @param dataList dataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<PlmProcessDesignData> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        PlmProcessDesignData data = CollUtil.getFirst(dataList);
        plmProcessdesignDataService.lambdaUpdate().eq(PlmProcessDesignData::getDataId, data.getDataId()).remove();
        plmProcessdesignDataService.saveBatch(dataList);
    }
}
