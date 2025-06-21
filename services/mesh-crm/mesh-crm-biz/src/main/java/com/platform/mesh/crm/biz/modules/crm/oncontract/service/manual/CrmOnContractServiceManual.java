package com.platform.mesh.crm.biz.modules.crm.oncontract.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.service.ICrmOnContractDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系合同签订
 * @author 蝉鸣
 */
@Service
public class CrmOnContractServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnContractServiceManual.class);

    @Autowired
    private ICrmOnContractDataService crmOnContractDataService;



    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onBusinessDataList onBusinessDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnContractData> onBusinessDataList) {
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        //批量新增信息
        crmOnContractDataService.saveBatch(onBusinessDataList);
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
        List<CrmOnContractData> onBusinessDataList = crmOnContractDataService.lambdaQuery().eq(CrmOnContractData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnContractData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onBusinessDataList)) {
            return;
        }
        AppUtil.editDbData(onBusinessDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        crmOnContractDataService.updateBatchById(onBusinessDataList);
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
        crmOnContractDataService.lambdaUpdate()
                .set(CrmOnContractData::getScopeUserId, scopeUserId)
                .set(CrmOnContractData::getScopeOrgId, scopeOrgId)
                .in(CrmOnContractData::getDataId, dataIds)
                .update();
    }
}