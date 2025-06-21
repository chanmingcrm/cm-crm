package com.platform.mesh.crm.biz.modules.crm.sufreviewdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.domain.po.CrmSufReviewData;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.mapper.CrmSufReviewDataMapper;
import com.platform.mesh.crm.biz.modules.crm.sufreviewdata.service.ICrmSufReviewDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系复盘总结数据
 * @author 蝉鸣
 */
@Service
public class CrmSufReviewDataServiceImpl extends AppDataServiceAbstract<CrmSufReviewDataMapper, CrmSufReviewData> implements ICrmSufReviewDataService  {

}