package com.platform.mesh.crm.biz.modules.crm.oninvoicedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.domain.po.CrmOnInvoiceData;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.mapper.CrmOnInvoiceDataMapper;
import com.platform.mesh.crm.biz.modules.crm.oninvoicedata.service.ICrmOnInvoiceDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系发票回执数据
 * @author 蝉鸣
 */
@Service
public class CrmOnInvoiceDataServiceImpl extends AppDataServiceAbstract<CrmOnInvoiceDataMapper, CrmOnInvoiceData> implements ICrmOnInvoiceDataService  {

}