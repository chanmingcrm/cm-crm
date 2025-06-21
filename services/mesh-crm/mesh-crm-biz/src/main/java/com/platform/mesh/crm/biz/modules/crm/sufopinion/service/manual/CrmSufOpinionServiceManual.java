package com.platform.mesh.crm.biz.modules.crm.sufopinion.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.service.ICrmSufOpinionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系意见评价
 * @author 蝉鸣
 */
@Service
public class CrmSufOpinionServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmSufOpinionServiceManual.class);


    @Autowired
    private ICrmSufOpinionDataService crmSufOpinionDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param sufOpinionDataList sufOpinionDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmSufOpinionData> sufOpinionDataList) {
        if(CollUtil.isEmpty(sufOpinionDataList)){
            return;
        }
        //批量新增信息
        crmSufOpinionDataService.saveBatch(sufOpinionDataList);
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
        List<CrmSufOpinionData> sufOpinionDataList = crmSufOpinionDataService.lambdaQuery().eq(CrmSufOpinionData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmSufOpinionData::getDataId, dataId).list();
        if(CollUtil.isEmpty(sufOpinionDataList)) {
            return;
        }
        AppUtil.editDbData(sufOpinionDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(sufOpinionDataList)){
            return;
        }
        crmSufOpinionDataService.updateBatchById(sufOpinionDataList);
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
        crmSufOpinionDataService.lambdaUpdate()
                .set(CrmSufOpinionData::getScopeUserId, scopeUserId)
                .set(CrmSufOpinionData::getScopeOrgId, scopeOrgId)
                .in(CrmSufOpinionData::getDataId, dataIds)
                .update();
    }
}