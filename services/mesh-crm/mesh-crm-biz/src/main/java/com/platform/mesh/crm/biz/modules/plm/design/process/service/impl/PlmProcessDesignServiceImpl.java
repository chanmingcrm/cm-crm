package com.platform.mesh.crm.biz.modules.plm.design.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataAddSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.api.modules.plm.constant.PlmConst;
import com.platform.mesh.crm.biz.modules.plm.design.process.domain.po.PlmProcessDesign;
import com.platform.mesh.crm.biz.modules.plm.design.process.mapper.PlmProcessDesignMapper;
import com.platform.mesh.crm.biz.modules.plm.design.process.service.IPlmProcessDesignService;
import com.platform.mesh.crm.biz.modules.plm.design.process.service.manual.PlmProcessDesignServiceManual;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.domain.po.PlmProcessDesignData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，封装转换方法在Manual中进行
 * @description 工序设计
 * @author 蝉鸣
 */
@Service
public class PlmProcessDesignServiceImpl extends AppServiceAbstract<PlmProcessDesignMapper, PlmProcessDesign> implements IPlmProcessDesignService {

    @Autowired
    private PlmProcessDesignServiceManual plmProcessdesignServiceManual;

    /**
     * 功能描述:
     * 【新增工序设计动态字段数据】
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<PlmProcessDesignData> dataPOList = BeanUtil.copyToList(dataList, PlmProcessDesignData.class);
        plmProcessdesignServiceManual.addDbDataBatch(dataPOList);
    }

    /**
     * 功能描述:
     * 〈新增其他逻辑〉
     * @param plmProcessDesign plmProcessDesign
     * @param dataAddSimpDTO dataAddSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void addOtherAction(PlmProcessDesign plmProcessDesign, DataAddSimpDTO dataAddSimpDTO) {
        Map<String, Object> docData = dataAddSimpDTO.getDocData();
        //获取父关联数据
        Long parentId = AppUtil.getSingleColumnIdValue(PlmConst.PARENT, docData);
        plmProcessDesign.setParentId(parentId);
        this.updateById(plmProcessDesign);
    }

    /**
     * 功能描述:
     * 【转移工序设计数据权限必须重写】
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public void transDbScopeBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        this.lambdaUpdate()
                .set(PlmProcessDesign::getScopeUserId, scopeUserId)
                .set(PlmProcessDesign::getScopeOrgId, scopeOrgId)
                .in(PlmProcessDesign::getId, dataIds)
                .update();
    }
}
