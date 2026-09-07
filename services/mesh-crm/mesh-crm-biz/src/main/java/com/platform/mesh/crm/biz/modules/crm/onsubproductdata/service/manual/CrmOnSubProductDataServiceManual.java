package com.platform.mesh.crm.biz.modules.crm.onsubproductdata.service.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系关联子产品数据
 * @author 蝉鸣
 */
@Service
public class CrmOnSubProductDataServiceManual {

    private final static Logger log = LoggerFactory.getLogger(CrmOnSubProductDataServiceManual.class);

}