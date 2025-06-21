package com.platform.mesh.crm.biz.modules.crm.onprogrammedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.domain.po.CrmOnProgrammeData;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.mapper.CrmOnProgrammeDataMapper;
import com.platform.mesh.crm.biz.modules.crm.onprogrammedata.service.ICrmOnProgrammeDataService;
import org.springframework.stereotype.Service;



/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系方案输出数据
 * @author 蝉鸣
 */
@Service
public class CrmOnProgrammeDataServiceImpl extends AppDataServiceAbstract<CrmOnProgrammeDataMapper, CrmOnProgrammeData> implements ICrmOnProgrammeDataService  {

}