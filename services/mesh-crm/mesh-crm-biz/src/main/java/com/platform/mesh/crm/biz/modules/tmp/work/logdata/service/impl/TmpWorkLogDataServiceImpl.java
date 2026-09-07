package com.platform.mesh.crm.biz.modules.tmp.work.logdata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.domain.po.TmpWorkLogData;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.mapper.TmpWorkLogDataMapper;
import com.platform.mesh.crm.biz.modules.tmp.work.logdata.service.ITmpWorkLogDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 工作日志数据
 * @author 蝉鸣
 */
@Service
public class TmpWorkLogDataServiceImpl extends AppDataServiceAbstract<TmpWorkLogDataMapper, TmpWorkLogData> implements ITmpWorkLogDataService {

}