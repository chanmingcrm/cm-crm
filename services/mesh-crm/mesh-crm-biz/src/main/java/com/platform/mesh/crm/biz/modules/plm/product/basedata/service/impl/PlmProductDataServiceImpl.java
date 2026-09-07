package com.platform.mesh.crm.biz.modules.plm.product.basedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.domain.po.PlmProductData;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.mapper.PlmProductDataMapper;
import com.platform.mesh.crm.biz.modules.plm.product.basedata.service.IPlmProductDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 供应链产品数据
 * @author 蝉鸣
 */
@Service
public class PlmProductDataServiceImpl extends AppDataServiceAbstract<PlmProductDataMapper, PlmProductData> implements IPlmProductDataService {

}