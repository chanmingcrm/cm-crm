package com.platform.mesh.crm.biz.modules.crm.precompetitordata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.domain.po.CrmPreCompetitorData;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.mapper.CrmPreCompetitorDataMapper;
import com.platform.mesh.crm.biz.modules.crm.precompetitordata.service.ICrmPreCompetitorDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系竞品分析数据
 * @author 蝉鸣
 */
@Service
public class CrmPreCompetitorDataServiceImpl extends AppDataServiceAbstract<CrmPreCompetitorDataMapper, CrmPreCompetitorData> implements ICrmPreCompetitorDataService  {

}