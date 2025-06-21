package com.platform.mesh.crm.biz.modules.crm.ondemanddata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.domain.po.CrmOnDemandData;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.mapper.CrmOnDemandDataMapper;
import com.platform.mesh.crm.biz.modules.crm.ondemanddata.service.ICrmOnDemandDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系需求整理数据
 * @author 蝉鸣
 */
@Service
public class CrmOnDemandDataServiceImpl extends AppDataServiceAbstract<CrmOnDemandDataMapper, CrmOnDemandData> implements ICrmOnDemandDataService  {

}