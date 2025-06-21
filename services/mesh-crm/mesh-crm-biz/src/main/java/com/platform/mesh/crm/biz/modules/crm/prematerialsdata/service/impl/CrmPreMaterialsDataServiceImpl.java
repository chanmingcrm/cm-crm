package com.platform.mesh.crm.biz.modules.crm.prematerialsdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.domain.po.CrmPreMaterialsData;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.mapper.CrmPreMaterialsDataMapper;
import com.platform.mesh.crm.biz.modules.crm.prematerialsdata.service.ICrmPreMaterialsDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系活动物料数据
 * @author 蝉鸣
 */
@Service
public class CrmPreMaterialsDataServiceImpl extends AppDataServiceAbstract<CrmPreMaterialsDataMapper, CrmPreMaterialsData> implements ICrmPreMaterialsDataService  {

}