package com.platform.mesh.app.biz.modules.app.baseuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.app.biz.modules.app.baseuser.domain.po.AppBaseUser;
import com.platform.mesh.app.biz.modules.app.baseuser.mapper.AppBaseUserMapper;
import com.platform.mesh.app.biz.modules.app.baseuser.service.IAppBaseUserService;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 应用
 * @author 蝉鸣
 */
@Service
public class AppBaseUserServiceImpl extends ServiceImpl<AppBaseUserMapper, AppBaseUser> implements IAppBaseUserService {



}
