package com.platform.mesh.crm.biz.modules.crm.ondemand.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.domain.po.CrmOnDemandData;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.service.ICrmOnDemandDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系需求整理
 * @author 蝉鸣
 */
@Service
public class CrmOnDemandServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnDemandServiceManual.class);

    @Autowired
    private ICrmOnDemandDataService crmOnDemandDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onDemandDataList onDemandDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnDemandData> onDemandDataList) {
        if(CollUtil.isEmpty(onDemandDataList)){
            return;
        }
        //批量新增信息
        crmOnDemandDataService.saveBatch(onDemandDataList);
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
        List<CrmOnDemandData> onDemandDataList = crmOnDemandDataService.lambdaQuery().eq(CrmOnDemandData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnDemandData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onDemandDataList)) {
            return;
        }
        AppUtil.editDbData(onDemandDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onDemandDataList)){
            return;
        }
        crmOnDemandDataService.updateBatchById(onDemandDataList);
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
        crmOnDemandDataService.lambdaUpdate()
                .set(CrmOnDemandData::getScopeUserId, scopeUserId)
                .set(CrmOnDemandData::getScopeOrgId, scopeOrgId)
                .in(CrmOnDemandData::getDataId, dataIds)
                .update();
    }
}