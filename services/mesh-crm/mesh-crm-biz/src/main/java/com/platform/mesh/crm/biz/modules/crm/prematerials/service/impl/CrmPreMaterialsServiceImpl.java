package com.platform.mesh.crm.biz.modules.crm.prematerials.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.prematerials.domain.po.CrmPreMaterials;
import com.platform.mesh.crm.biz.modules.crm.prematerials.mapper.CrmPreMaterialsMapper;
import com.platform.mesh.crm.biz.modules.crm.prematerials.service.ICrmPreMaterialsService;
import com.platform.mesh.crm.biz.modules.crm.prematerials.service.manual.CrmPreMaterialsServiceManual;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.domain.po.CrmPreMaterialsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动物料
 * @author 蝉鸣
 */
@Service
public class CrmPreMaterialsServiceImpl extends AppServiceAbstract<CrmPreMaterialsMapper, CrmPreMaterials> implements ICrmPreMaterialsService  {

    @Autowired
    private CrmPreMaterialsServiceManual crmPreMaterialsServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系活动物料〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreMaterialsData> crmPreMaterialsDataList = BeanUtil.copyToList(dataList, CrmPreMaterialsData.class);
        //批量保存data表数据
        crmPreMaterialsServiceManual.addDbDataBatch(crmPreMaterialsDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系活动物料〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreMaterialsServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreMaterials::getScopeUserId,scopeUserId)
                .set(CrmPreMaterials::getScopeOrgId,scopeOrgId)
                .in(CrmPreMaterials::getId,dataIds)
                .update();
        //修改DB Data
        crmPreMaterialsServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}