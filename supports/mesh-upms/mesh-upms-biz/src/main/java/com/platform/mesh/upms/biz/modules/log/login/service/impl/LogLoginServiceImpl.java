package com.platform.mesh.upms.biz.modules.log.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.log.login.domain.po.LogLogin;
import com.platform.mesh.upms.biz.modules.log.login.mapper.LogLoginMapper;
import com.platform.mesh.upms.biz.modules.log.login.service.ILogLoginService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 登录日志信息
 * @author 蝉鸣
 */
@Service()
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {

}

