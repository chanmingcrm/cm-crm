package com.platform.mesh.crm.biz.modules.crm.onfollowdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.domain.po.CrmOnFollowData;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.mapper.CrmOnFollowDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onfollowdata.service.ICrmOnFollowDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系跟进拜访数据
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowDataServiceImpl extends AppDataServiceAbstract<CrmOnFollowDataMapper, CrmOnFollowData> implements ICrmOnFollowDataService  {

}