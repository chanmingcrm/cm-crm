package com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.domain.po.CrmSufDeliverData;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.mapper.CrmSufDeliverDataMapper;
import com.platform.mesh.crm.biz.modules.crm.sufdeliverdata.service.ICrmSufDeliverDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系标的交付数据
 * @author 蝉鸣
 */
@Service
public class CrmSufDeliverDataServiceImpl extends AppDataServiceAbstract<CrmSufDeliverDataMapper, CrmSufDeliverData> implements ICrmSufDeliverDataService  {

}