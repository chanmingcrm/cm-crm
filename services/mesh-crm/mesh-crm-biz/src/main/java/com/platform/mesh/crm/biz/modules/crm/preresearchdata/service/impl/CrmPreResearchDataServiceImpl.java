package com.platform.mesh.crm.biz.modules.crm.preresearchdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.domain.po.CrmPreResearchData;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.mapper.CrmPreResearchDataMapper;
import com.platform.mesh.crm.biz.modules.crm.preresearchdata.service.ICrmPreResearchDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系市场调研数据
 * @author 蝉鸣
 */
@Service
public class CrmPreResearchDataServiceImpl extends AppDataServiceAbstract<CrmPreResearchDataMapper, CrmPreResearchData> implements ICrmPreResearchDataService  {

}