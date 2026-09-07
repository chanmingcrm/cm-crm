package com.platform.mesh.crm.biz.modules.plm.design.structure.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.design.structure.domain.po.PlmStructUreDesign;
import com.platform.mesh.crm.biz.modules.plm.design.structure.mapper.PlmStructUreDesignMapper;
import com.platform.mesh.crm.biz.modules.plm.design.structure.service.IPlmStructUreDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.structure.service.manual.PlmStructUreDesignServiceManual;
import com.platform.mesh.crm.biz.modules.plm.design.structuredata.domain.po.PlmStructUreDesignData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，封装转换方法在Manual中进行
 * @description 结构设计
 * @author 蝉鸣
 */
@Service
public class PlmStructUreDesignServiceImpl extends AppServiceAbstract<PlmStructUreDesignMapper, PlmStructUreDesign> implements IPlmStructUreDesignService {

    @Autowired
    private PlmStructUreDesignServiceManual plmStructuredesignServiceManual;

    /**
     * 功能描述:
     * 【新增结构设计动态字段数据】
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmStructUreDesignData> dataPOList = BeanUtil.copyToList(dataList, PlmStructUreDesignData.class);
        plmStructuredesignServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 【转移结构设计数据权限必须重写】
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(PlmStructUreDesign::getScopeUserId, scopeUserId)
                .set(PlmStructUreDesign::getScopeOrgId, scopeOrgId)
                .in(PlmStructUreDesign::getId, dataIds)
                .update();
    }
}
