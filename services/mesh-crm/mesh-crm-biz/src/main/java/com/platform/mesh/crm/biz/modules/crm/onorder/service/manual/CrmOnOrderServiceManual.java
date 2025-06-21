package com.platform.mesh.crm.biz.modules.crm.onorder.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.service.ICrmOnContractDataService;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.service.ICrmOnOrderDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系订单
 * @author 蝉鸣
 */
@Service
public class CrmOnOrderServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmOnOrderServiceManual.class);

    @Autowired
    private ICrmOnOrderDataService crmOnOrderDataService;



    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onBusinessDataList onBusinessDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnOrderData> onBusinessDataList) {
        if(CollUtil.isEmpty(onBusinessDataList)){
            return;
        }
        //批量新增信息
        crmOnOrderDataService.saveBatch(onBusinessDataList);
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
        List<CrmOnOrderData> onOrderDataList = crmOnOrderDataService.lambdaQuery().eq(CrmOnOrderData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnOrderData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onOrderDataList)) {
            return;
        }
        AppUtil.editDbData(onOrderDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onOrderDataList)){
            return;
        }
        crmOnOrderDataService.updateBatchById(onOrderDataList);
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
        crmOnOrderDataService.lambdaUpdate()
                .set(CrmOnOrderData::getScopeUserId, scopeUserId)
                .set(CrmOnOrderData::getScopeOrgId, scopeOrgId)
                .in(CrmOnOrderData::getDataId, dataIds)
                .update();
    }
}