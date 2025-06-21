package com.platform.mesh.crm.biz.modules.crm.preproductdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.domain.po.CrmPreProductData;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.mapper.CrmPreProductDataMapper;
import com.platform.mesh.crm.biz.modules.crm.preproductdata.service.ICrmPreProductDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系展示产品数据
 * @author 蝉鸣
 */
@Service
public class CrmPreProductDataServiceImpl extends AppDataServiceAbstract<CrmPreProductDataMapper, CrmPreProductData> implements ICrmPreProductDataService  {

}