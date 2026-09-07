package com.platform.mesh.crm.biz.modules.plm.design.productdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.domain.po.PlmDesignProductData;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.mapper.PlmDesignProductDataMapper;
import com.platform.mesh.crm.biz.modules.plm.design.productdata.service.IPlmDesignProductDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 产品设计
 * @author 蝉鸣
 */
@Service
public class PlmDesignProductDataServiceImpl extends AppDataServiceAbstract<PlmDesignProductDataMapper, PlmDesignProductData> implements IPlmDesignProductDataService  {

}