package com.platform.mesh.crm.biz.modules.crm.onbusinessdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.domain.po.CrmOnBusinessData;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.mapper.CrmOnBusinessDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onbusinessdata.service.ICrmOnBusinessDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系商机跟进数据
 * @author 蝉鸣
 */
@Service
public class CrmOnBusinessDataServiceImpl extends AppDataServiceAbstract<CrmOnBusinessDataMapper, CrmOnBusinessData> implements ICrmOnBusinessDataService  {


}