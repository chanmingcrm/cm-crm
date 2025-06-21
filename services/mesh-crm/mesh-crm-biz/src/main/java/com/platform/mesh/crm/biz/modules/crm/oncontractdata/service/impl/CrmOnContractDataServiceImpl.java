package com.platform.mesh.crm.biz.modules.crm.oncontractdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.domain.po.CrmOnContractData;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.mapper.CrmOnContractDataMapper;
import com.platform.mesh.crm.biz.modules.crm.oncontractdata.service.ICrmOnContractDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系合同签订数据
 * @author 蝉鸣
 */
@Service
public class CrmOnContractDataServiceImpl extends AppDataServiceAbstract<CrmOnContractDataMapper, CrmOnContractData> implements ICrmOnContractDataService  {

}