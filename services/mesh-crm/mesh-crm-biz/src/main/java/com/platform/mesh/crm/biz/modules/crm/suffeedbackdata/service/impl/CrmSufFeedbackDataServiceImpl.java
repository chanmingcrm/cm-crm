package com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.domain.po.CrmSufFeedbackData;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.mapper.CrmSufFeedbackDataMapper;
import com.platform.mesh.crm.biz.modules.crm.suffeedbackdata.service.ICrmSufFeedbackDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系市场反馈数据
 * @author 蝉鸣
 */
@Service
public class CrmSufFeedbackDataServiceImpl extends AppDataServiceAbstract<CrmSufFeedbackDataMapper, CrmSufFeedbackData> implements ICrmSufFeedbackDataService  {

}