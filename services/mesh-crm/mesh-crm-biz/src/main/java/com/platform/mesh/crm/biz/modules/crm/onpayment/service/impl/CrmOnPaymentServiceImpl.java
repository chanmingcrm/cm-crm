package com.platform.mesh.crm.biz.modules.crm.onpayment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.service.impl.AppServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onpayment.domain.po.CrmOnPayment;
import com.platform.mesh.crm.biz.modules.crm.onpayment.mapper.CrmOnPaymentMapper;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.ICrmOnPaymentService;
import com.platform.mesh.crm.biz.modules.crm.onpayment.service.manual.CrmOnPaymentServiceManual;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系款项记录
 * @author 蝉鸣
 */
@Service
public class CrmOnPaymentServiceImpl extends AppServiceAbstract<CrmOnPaymentMapper, CrmOnPayment> implements ICrmOnPaymentService  {

    @Autowired
    private CrmOnPaymentServiceManual crmOnPaymentServiceManual;

    /**
     * 功能描述:
     * 〈新增客户关系款项记录〉
     * @param dataList dataList
     * @author 蝉鸣
     */
    @Override
    public <D extends AppDataPO> void addDbDataBatch(List<D> dataList) {
        List<CrmOnPaymentData> crmOnPaymentDataList = BeanUtil.copyToList(dataList, CrmOnPaymentData.class);
        //批量保存data表数据
        crmOnPaymentServiceManual.addDbDataBatch(crmOnPaymentDataList);
    }

    /**
     * 功能描述:
     * 〈修改客户关系款项记录〉
     * @param dataId dataId
     * @param dataEditSimpDTO dataEditSimpDTO
     * @author 蝉鸣
     */
    @Override
    public void editDbDataBatch(Long dataId, DataEditSimpDTO dataEditSimpDTO) {
        //批量保存data表数据
        crmOnPaymentServiceManual.editDbDataBatch(dataId,dataEditSimpDTO);
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
                .set(CrmOnPayment::getScopeUserId,scopeUserId)
                .set(CrmOnPayment::getScopeOrgId,scopeOrgId)
                .in(CrmOnPayment::getId,dataIds)
                .update();
        //修改DB Data
        crmOnPaymentServiceManual.transDbDataBatch(dataIds,scopeUserId,scopeOrgId);
    }
}