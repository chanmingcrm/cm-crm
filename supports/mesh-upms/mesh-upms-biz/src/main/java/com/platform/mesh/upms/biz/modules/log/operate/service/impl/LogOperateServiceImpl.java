package com.platform.mesh.upms.biz.modules.log.operate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.log.operate.domain.po.LogOperate;
import com.platform.mesh.upms.biz.modules.log.operate.mapper.LogOperateMapper;
import com.platform.mesh.upms.biz.modules.log.operate.service.ILogOperateService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 操作日志
 * @author 蝉鸣
 */
@Service()
public class LogOperateServiceImpl extends ServiceImpl<LogOperateMapper, LogOperate> implements ILogOperateService {

}

