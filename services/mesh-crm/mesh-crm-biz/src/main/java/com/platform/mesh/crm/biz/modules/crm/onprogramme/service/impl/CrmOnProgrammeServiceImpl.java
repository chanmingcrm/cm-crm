package com.platform.mesh.crm.biz.modules.crm.onprogramme.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.domain.po.CrmOnProgramme;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.mapper.CrmOnProgrammeMapper;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.service.ICrmOnProgrammeService;
import com.platform.mesh.crm.biz.modules.crm.onprogramme.service.manual.CrmOnProgrammeServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.domain.po.CrmOnProgrammeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系方案输出
 * @author 蝉鸣
 */
@Service
public class CrmOnProgrammeServiceImpl extends AppServiceAbstract<CrmOnProgrammeMapper, CrmOnProgramme> implements ICrmOnProgrammeService  {

    @Autowired
    private CrmOnProgrammeServiceManual crmOnProgrammeServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系方案输出〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnProgrammeData> crmOnProgrammeDataList = BeanUtil.copyToList(dataList, CrmOnProgrammeData.class);
        //批量保存data表数据
        crmOnProgrammeServiceManual.addDbDataBatch(crmOnProgrammeDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系方案输出〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmOnProgrammeServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
    }

    /**
     * 功能描述:
     * 〈转移Data数据权限必须重写〉
     * @param dataIds dataIds
     * @param scopeUserId scopeUserId
     * @param scopeOrgId scopeOrgId
     * @author 蝉鸣
     */
    @Override
    public  void transDbDataBatch(List<Long> dataIds,Long scopeUserId,Long scopeOrgId){
        //修改DB
        this.lambdaUpdate()
                .set(CrmOnProgramme::getScopeUserId,scopeUserId)
                .set(CrmOnProgramme::getScopeOrgId,scopeOrgId)
                .in(CrmOnProgramme::getId,dataIds)
                .update();
        //修改DB Data
        crmOnProgrammeServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}