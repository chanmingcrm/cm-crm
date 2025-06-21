package com.platform.mesh.tmp.biz.modules.work.logdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.tmp.biz.modules.work.logdata.domain.po.WorkLogData;
import com.platform.mesh.tmp.biz.modules.work.logdata.mapper.WorkLogDataMapper;
import com.platform.mesh.tmp.biz.modules.work.logdata.service.IWorkLogDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 工作日志数据
 * @author 蝉鸣
 */
@Service
public class WorkLogDataServiceImpl extends AppDataServiceAbstract<WorkLogDataMapper, WorkLogData> implements IWorkLogDataService {

}