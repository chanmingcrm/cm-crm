package com.platform.mesh.crm.biz.modules.crm.onbusiness.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.domain.po.CrmOnBusiness;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.mapper.CrmOnBusinessMapper;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.ICrmOnBusinessService;
import com.platform.mesh.crm.biz.modules.crm.onbusiness.service.manual.CrmOnBusinessServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.domain.po.CrmOnBusinessData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系商机跟进
 * @author 蝉鸣
 */
@Service
public class CrmOnBusinessServiceImpl extends AppServiceAbstract<CrmOnBusinessMapper, CrmOnBusiness> implements ICrmOnBusinessService  {

    @Autowired
    private CrmOnBusinessServiceManual crmOnBusinessServiceManual;


    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnBusinessData> crmOnBusinessData = BeanUtil.copyToList(dataList, CrmOnBusinessData.class);
        //批量保存data表数据
        crmOnBusinessServiceManual.addDbDataBatch(crmOnBusinessData);
    }

    /**
     * 功能描述:
     * 〈新增客户关系商机跟进〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmOnBusinessServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmOnBusiness::getScopeUserId,scopeUserId)
                .set(CrmOnBusiness::getScopeOrgId,scopeOrgId)
                .in(CrmOnBusiness::getId,dataIds)
                .update();
        //修改DB Data
        crmOnBusinessServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }

}