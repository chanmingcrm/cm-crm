package com.platform.mesh.crm.biz.modules.crm.onpaymentdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.domain.po.CrmOnPaymentData;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.mapper.CrmOnPaymentDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onpaymentdata.service.ICrmOnPaymentDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系款项记录数据
 * @author 蝉鸣
 */
@Service
public class CrmOnPaymentDataServiceImpl extends AppDataServiceAbstract<CrmOnPaymentDataMapper, CrmOnPaymentData> implements ICrmOnPaymentDataService  {

}