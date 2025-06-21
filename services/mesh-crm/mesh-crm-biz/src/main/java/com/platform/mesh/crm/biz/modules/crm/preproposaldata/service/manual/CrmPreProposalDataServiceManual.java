package com.platform.mesh.crm.biz.modules.crm.preproposaldata.service.manual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系提案报价数据
 * @author 蝉鸣
 */
@Service
public class CrmPreProposalDataServiceManual{

    private final static Logger log = LoggerFactory.getLogger(CrmPreProposalDataServiceManual.class);

}