package com.platform.mesh.crm.biz.modules.init.db.service.impl;

import com.platform.mesh.app.api.modules.init.db.service.impl.DbServiceImpl;
import com.platform.mesh.crm.biz.modules.init.db.service.ICrmDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description Db服务
 * @author 蝉鸣
 */
@Service
public class CrmDbServiceImpl extends DbServiceImpl implements ICrmDbService  {

    private static final Logger log = LoggerFactory.getLogger(CrmDbServiceImpl.class);


}