package com.platform.mesh.crm.biz.init.es.service.impl;

import com.platform.mesh.app.api.modules.init.es.service.impl.AppInitEsServiceImpl;
import com.platform.mesh.crm.biz.init.es.service.ICrmInitEsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系初始化Es
 * @author 蝉鸣
 */
@Service
public class CrmInitEsServiceImpl extends AppInitEsServiceImpl implements ICrmInitEsService {

    private final static Logger log = LoggerFactory.getLogger(CrmInitEsServiceImpl.class);

}