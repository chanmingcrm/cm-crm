package com.platform.mesh.crm.biz.modules.crm.onsubproductdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.domain.po.CrmOnSubProductData;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.mapper.CrmOnSubProductDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onsubproductdata.service.ICrmOnSubProductDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系关联子产品数据
 * @author 蝉鸣
 */
@Service
public class CrmOnSubProductDataServiceImpl extends AppDataServiceAbstract<CrmOnSubProductDataMapper, CrmOnSubProductData> implements ICrmOnSubProductDataService {

}