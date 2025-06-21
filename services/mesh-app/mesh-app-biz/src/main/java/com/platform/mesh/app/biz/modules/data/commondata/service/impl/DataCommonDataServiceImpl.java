package com.platform.mesh.app.biz.modules.data.commondata.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppDataServiceAbstract;
import com.platform.mesh.app.biz.modules.data.commondata.domain.po.DataCommonData;
import com.platform.mesh.app.biz.modules.data.commondata.mapper.DataCommonDataMapper;
import com.platform.mesh.app.biz.modules.data.commondata.service.IDataCommonDataService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 通用数据数据
 * @author 蝉鸣
 */
@Service
public class DataCommonDataServiceImpl extends AppDataServiceAbstract<DataCommonDataMapper, DataCommonData> implements IDataCommonDataService {


}