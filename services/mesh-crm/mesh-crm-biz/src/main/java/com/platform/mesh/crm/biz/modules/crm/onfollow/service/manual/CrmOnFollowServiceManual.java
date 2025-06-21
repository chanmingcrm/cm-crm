package com.platform.mesh.crm.biz.modules.crm.onfollow.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.service.ICrmOnFollowDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系跟进拜访
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnFollowServiceManual.class);

    @Autowired
    private ICrmOnFollowDataService crmOnFollowDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onFollowDataList onFollowDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnFollowData> onFollowDataList) {
        if(CollUtil.isEmpty(onFollowDataList)){
            return;
        }
        //批量新增信息
        crmOnFollowDataService.saveBatch(onFollowDataList);
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
        List<CrmOnFollowData> onFollowDataList = crmOnFollowDataService.lambdaQuery().eq(CrmOnFollowData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnFollowData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onFollowDataList)) {
            return;
        }
        AppUtil.editDbData(onFollowDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onFollowDataList)){
            return;
        }
        crmOnFollowDataService.updateBatchById(onFollowDataList);
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
        crmOnFollowDataService.lambdaUpdate()
                .set(CrmOnFollowData::getScopeUserId, scopeUserId)
                .set(CrmOnFollowData::getScopeOrgId, scopeOrgId)
                .in(CrmOnFollowData::getDataId, dataIds)
                .update();
    }

}