package com.platform.mesh.crm.biz.modules.crm.onprogramme.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.domain.po.CrmOnProgrammeData;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.service.ICrmOnProgrammeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系方案输出
 * @author 蝉鸣
 */
@Service
public class CrmOnProgrammeServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmOnProgrammeServiceManual.class);

    @Autowired
    private ICrmOnProgrammeDataService crmOnProgrammeDataService;


    /**
     * 功能描述:
     * 〈DB Data 数据批量保存〉
     * @param onProgrammeDataList onProgrammeDataList
     * @author 蝉鸣
     */
    public void addDbDataBatch(List<CrmOnProgrammeData> onProgrammeDataList) {
        if(CollUtil.isEmpty(onProgrammeDataList)){
            return;
        }
        //批量新增信息
        crmOnProgrammeDataService.saveBatch(onProgrammeDataList);
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
        List<CrmOnProgrammeData> onProgrammeDataList = crmOnProgrammeDataService.lambdaQuery().eq(CrmOnProgrammeData::getModuleId, dataEditSimpDTO.getModuleId())
                .eq(CrmOnProgrammeData::getDataId, dataId).list();
        if(CollUtil.isEmpty(onProgrammeDataList)) {
            return;
        }
        AppUtil.editDbData(onProgrammeDataList, dataEditSimpDTO);
        if(CollUtil.isEmpty(onProgrammeDataList)){
            return;
        }
        crmOnProgrammeDataService.updateBatchById(onProgrammeDataList);
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
        crmOnProgrammeDataService.lambdaUpdate()
                .set(CrmOnProgrammeData::getScopeUserId, scopeUserId)
                .set(CrmOnProgrammeData::getScopeOrgId, scopeOrgId)
                .in(CrmOnProgrammeData::getDataId, dataIds)
                .update();
    }
}