package com.platform.mesh.crm.biz.modules.crm.preproduct.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preproduct.domain.po.CrmPreProduct;
import com.platform.mesh.crm.biz.modules.crm.preproduct.mapper.CrmPreProductMapper;
import com.platform.mesh.crm.biz.modules.crm.preproduct.service.ICrmPreProductService;
import com.platform.mesh.crm.biz.modules.crm.preproduct.service.manual.CrmPreProductServiceManual;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.domain.po.CrmPreProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系展示产品
 * @author 蝉鸣
 */
@Service
public class CrmPreProductServiceImpl extends AppServiceAbstract<CrmPreProductMapper, CrmPreProduct> implements ICrmPreProductService  {

    @Autowired
    private CrmPreProductServiceManual crmPreProductServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系展示产品〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmPreProductData> crmPreProductDataList = BeanUtil.copyToList(dataList, CrmPreProductData.class);
        //批量保存data表数据
        crmPreProductServiceManual.addDbDataBatch(crmPreProductDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系展示产品〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmPreProductServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmPreProduct::getScopeUserId,scopeUserId)
                .set(CrmPreProduct::getScopeOrgId,scopeOrgId)
                .in(CrmPreProduct::getId,dataIds)
                .update();
        //修改DB Data
        crmPreProductServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}