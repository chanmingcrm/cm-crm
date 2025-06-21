package com.platform.mesh.crm.biz.modules.crm.preproduct.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.domain.po.CrmPreProductData;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.service.ICrmPreProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系展示产品
 * @author 蝉鸣
 */
@Service
public class CrmPreProductServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreProductServiceManual.class);


    @Autowired
    private ICrmPreProductDataService crmPreProductDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param preProductDataList preProductDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmPreProductData> preProductDataList) {
        if(CollUtil.isEmpty(preProductDataList)){
            return;
        }
        //批量新增信息
        crmPreProductDataService.saveBatch(preProductDataList);
    }

    /**
     * 功能描述:
     * 〈DB Data 数据批量修改〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //查询已经存在的新增数据
        List<CrmPreProductData> preProductDataList = crmPreProductDataService.lambdaQuery().eq(CrmPreProductData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmPreProductData::getDataId, dataId).list();
        if(CollUtil.isEmpty(preProductDataList)) {
            return;
        }
        AppUtil.editDbData(preProductDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(preProductDataList)){
            return;
        }
        crmPreProductDataService.updateBatchById(preProductDataList);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    public void transDbDataBatch(List<Long> dataIds, Long scopeUserId, Long scopeOrgId) {
        if(CollUtil.isEmpty(dataIds) || ObjectUtil.isEmpty(scopeUserId) || ObjectUtil.isEmpty(scopeOrgId)) {
            return;
        }
        crmPreProductDataService.lambdaUpdate()
                .set(CrmPreProductData::getScopeUserId, scopeUserId)
                .set(CrmPreProductData::getScopeOrgId, scopeOrgId)
                .in(CrmPreProductData::getDataId, dataIds)
                .update();
    }
}