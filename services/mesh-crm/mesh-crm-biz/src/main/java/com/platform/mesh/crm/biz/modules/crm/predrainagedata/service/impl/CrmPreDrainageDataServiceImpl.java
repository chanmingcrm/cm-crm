package com.platform.mesh.crm.biz.modules.crm.predrainagedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.domain.po.CrmPreDrainageData;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.mapper.CrmPreDrainageDataMapper;
import com.platform.mesh.crm.biz.modules.crm.predrainagedata.service.ICrmPreDrainageDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动引流数据
 * @author 蝉鸣
 */
@Service
public class CrmPreDrainageDataServiceImpl extends AppDataServiceAbstract<CrmPreDrainageDataMapper, CrmPreDrainageData> implements ICrmPreDrainageDataService  {

}