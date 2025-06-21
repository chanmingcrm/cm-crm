package com.platform.mesh.crm.biz.modules.crm.precontactsdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.domain.po.CrmPreContactsData;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.mapper.CrmPreContactsDataMapper;
import com.platform.mesh.crm.biz.modules.crm.precontactsdata.service.ICrmPreContactsDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系联系人
 * @author 蝉鸣
 */
@Service
public class CrmPreContactsDataServiceImpl extends AppDataServiceAbstract<CrmPreContactsDataMapper, CrmPreContactsData> implements ICrmPreContactsDataService {

}