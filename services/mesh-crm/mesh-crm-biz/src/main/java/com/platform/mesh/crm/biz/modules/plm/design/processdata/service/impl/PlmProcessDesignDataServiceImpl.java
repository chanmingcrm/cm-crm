package com.platform.mesh.crm.biz.modules.plm.design.processdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.domain.po.PlmProcessDesignData;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.mapper.PlmProcessDesignDataMapper;
import com.platform.mesh.crm.biz.modules.plm.design.processdata.service.IPlmProcessDesignDataService;
import org.springframework.stereotype.Service;

/**
 * @description 工序设计数据
 * @author 蝉鸣
 */
@Service
public class PlmProcessDesignDataServiceImpl extends AppDataServiceAbstract<PlmProcessDesignDataMapper, PlmProcessDesignData> implements IPlmProcessDesignDataService {

}
