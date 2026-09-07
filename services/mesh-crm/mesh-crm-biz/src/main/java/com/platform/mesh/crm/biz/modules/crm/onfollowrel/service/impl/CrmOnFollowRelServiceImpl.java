package com.platform.mesh.crm.biz.modules.crm.onfollowrel.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppRelServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.mapper.CrmOnFollowRelMapper;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.po.CrmOnFollowRel;
import com.platform.mesh.crm.biz.modules.crm.onfollowrel.service.ICrmOnFollowRelService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 跟进数据数据
 * @author 蝉鸣
 */
@Service
public class CrmOnFollowRelServiceImpl extends AppRelServiceAbstract<CrmOnFollowRelMapper, CrmOnFollowRel> implements ICrmOnFollowRelService {


}