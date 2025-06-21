package com.platform.mesh.crm.biz.modules.crm.sufopiniondata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.domain.po.CrmSufOpinionData;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.mapper.CrmSufOpinionDataMapper;
import com.platform.mesh.crm.biz.modules.crm.sufopiniondata.service.ICrmSufOpinionDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系意见评价数据
 * @author 蝉鸣
 */
@Service
public class CrmSufOpinionDataServiceImpl extends AppDataServiceAbstract<CrmSufOpinionDataMapper, CrmSufOpinionData> implements ICrmSufOpinionDataService  {

}