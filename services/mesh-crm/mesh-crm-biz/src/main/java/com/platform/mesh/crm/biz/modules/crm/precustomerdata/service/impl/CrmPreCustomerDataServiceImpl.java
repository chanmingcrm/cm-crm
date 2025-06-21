package com.platform.mesh.crm.biz.modules.crm.precustomerdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.domain.po.CrmPreCustomerData;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.mapper.CrmPreCustomerDataMapper;
import com.platform.mesh.crm.biz.modules.crm.precustomerdata.service.ICrmPreCustomerDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系客户对象
 * @author 蝉鸣
 */
@Service
public class CrmPreCustomerDataServiceImpl extends AppDataServiceAbstract<CrmPreCustomerDataMapper, CrmPreCustomerData> implements ICrmPreCustomerDataService  {

}