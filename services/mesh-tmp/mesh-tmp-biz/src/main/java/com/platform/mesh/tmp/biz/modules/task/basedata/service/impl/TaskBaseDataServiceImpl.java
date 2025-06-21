package com.platform.mesh.tmp.biz.modules.task.basedata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.tmp.biz.modules.task.basedata.domain.po.TaskBaseData;
import com.platform.mesh.tmp.biz.modules.task.basedata.mapper.TaskBaseDataMapper;
import com.platform.mesh.tmp.biz.modules.task.basedata.service.ITaskBaseDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 任务数据
 * @author 蝉鸣
 */
@Service
public class TaskBaseDataServiceImpl extends AppDataServiceAbstract<TaskBaseDataMapper, TaskBaseData> implements ITaskBaseDataService {

}