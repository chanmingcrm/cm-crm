package com.platform.mesh.crm.biz.modules.plm.design.craft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.design.craft.domain.po.PlmCraftDesign;
import com.platform.mesh.crm.biz.modules.plm.design.craft.mapper.PlmCraftDesignMapper;
import com.platform.mesh.crm.biz.modules.plm.design.craft.service.IPlmCraftDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.craft.service.manual.PlmCraftDesignServiceManual;
import com.platform.mesh.crm.biz.modules.plm.design.craftdata.domain.po.PlmCraftDesignData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，封装转换方法在Manual中进行
 * @description 工艺设计
 * @author 蝉鸣
 */
@Service
public class PlmCraftDesignServiceImpl extends AppServiceAbstract<PlmCraftDesignMapper, PlmCraftDesign> implements IPlmCraftDesignService {

    @Autowired
    private PlmCraftDesignServiceManual plmCraftdesignServiceManual;

    /**
     * 功能描述:
     * 【新增工艺设计动态字段数据】
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmCraftDesignData> dataPOList = BeanUtil.copyToList(dataList, PlmCraftDesignData.class);
        plmCraftdesignServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 【转移工艺设计数据权限必须重写】
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(PlmCraftDesign::getScopeUserId, scopeUserId)
                .set(PlmCraftDesign::getScopeOrgId, scopeOrgId)
                .in(PlmCraftDesign::getId, dataIds)
                .update();
    }
}
