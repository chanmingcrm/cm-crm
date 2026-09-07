package com.platform.mesh.app.biz.modules.app.formcolumn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.mapper.AppFormColumnMapper;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnAddService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 单字段关联
 * @author 蝉鸣
 */
@Service
public class AppFormColumnAddServiceImpl extends ServiceImpl<AppFormColumnMapper, AppFormColumn> implements IAppFormColumnAddService {

}
