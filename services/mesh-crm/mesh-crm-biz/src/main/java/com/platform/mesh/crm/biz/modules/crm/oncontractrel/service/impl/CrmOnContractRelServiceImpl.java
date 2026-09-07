package com.platform.mesh.crm.biz.modules.crm.oncontractrel.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppRelServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.mapper.CrmOnContractRelMapper;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.po.CrmOnContractRel;
import com.platform.mesh.crm.biz.modules.crm.oncontractrel.service.ICrmOnContractRelService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 合同数据
 * @author 蝉鸣
 */
@Service
public class CrmOnContractRelServiceImpl extends AppRelServiceAbstract<CrmOnContractRelMapper, CrmOnContractRel> implements ICrmOnContractRelService {


}