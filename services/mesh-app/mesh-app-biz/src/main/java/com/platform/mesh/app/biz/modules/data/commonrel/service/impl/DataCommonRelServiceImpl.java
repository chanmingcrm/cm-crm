package com.platform.mesh.app.biz.modules.data.commonrel.service.impl;

import com.platform.mesh.app.api.modules.app.service.impl.AppRelServiceAbstract;
import com.platform.mesh.app.biz.modules.data.commonrel.domain.po.DataCommonRel;
import com.platform.mesh.app.biz.modules.data.commonrel.mapper.DataCommonRelMapper;
import com.platform.mesh.app.biz.modules.data.commonrel.service.IDataCommonRelService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 通用数据数据
 * @author 蝉鸣
 */
@Service
public class DataCommonRelServiceImpl extends AppRelServiceAbstract<DataCommonRelMapper, DataCommonRel> implements IDataCommonRelService {


}