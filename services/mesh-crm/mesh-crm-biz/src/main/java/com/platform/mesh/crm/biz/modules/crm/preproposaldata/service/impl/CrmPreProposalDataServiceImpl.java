package com.platform.mesh.crm.biz.modules.crm.preproposaldata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.domain.po.CrmPreProposalData;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.mapper.CrmPreProposalDataMapper;
import com.platform.mesh.crm.biz.modules.crm.preproposaldata.service.ICrmPreProposalDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系提案报价数据
 * @author 蝉鸣
 */
@Service
public class CrmPreProposalDataServiceImpl extends AppDataServiceAbstract<CrmPreProposalDataMapper, CrmPreProposalData> implements ICrmPreProposalDataService  {

}