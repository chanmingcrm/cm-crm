package com.platform.mesh.crm.biz.modules.crm.onorderdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.domain.po.CrmOnOrderData;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.mapper.CrmOnOrderDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onorderdata.service.ICrmOnOrderDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系订单数据
 * @author 蝉鸣
 */
@Service
public class CrmOnOrderDataServiceImpl extends AppDataServiceAbstract<CrmOnOrderDataMapper, CrmOnOrderData> implements ICrmOnOrderDataService {

}