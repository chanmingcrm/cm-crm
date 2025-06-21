package com.platform.mesh.upms.biz.modules.log.modify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.log.modify.domain.po.LogModify;
import com.platform.mesh.upms.biz.modules.log.modify.mapper.LogModifyMapper;
import com.platform.mesh.upms.biz.modules.log.modify.service.ILogModifyService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 修改日志
 * @author 蝉鸣
 */
@Service()
public class LogModifyServiceImpl extends ServiceImpl<LogModifyMapper, LogModify> implements ILogModifyService {

}

