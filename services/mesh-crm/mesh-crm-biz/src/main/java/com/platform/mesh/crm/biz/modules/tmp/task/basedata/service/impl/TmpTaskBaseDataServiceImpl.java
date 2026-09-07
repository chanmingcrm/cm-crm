package com.platform.mesh.crm.biz.modules.tmp.task.basedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.domain.po.TmpTaskBaseData;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.mapper.TmpTaskBaseDataMapper;
import com.platform.mesh.crm.biz.modules.tmp.task.basedata.service.ITmpTaskBaseDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务数据
 * @author 蝉鸣
 */
@Service
public class TmpTaskBaseDataServiceImpl extends AppDataServiceAbstract<TmpTaskBaseDataMapper, TmpTaskBaseData> implements ITmpTaskBaseDataService {

}