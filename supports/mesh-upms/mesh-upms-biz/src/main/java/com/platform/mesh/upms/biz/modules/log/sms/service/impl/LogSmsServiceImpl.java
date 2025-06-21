package com.platform.mesh.upms.biz.modules.log.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.biz.modules.log.sms.domain.po.LogSms;
import com.platform.mesh.upms.biz.modules.log.sms.mapper.LogSmsMapper;
import com.platform.mesh.upms.biz.modules.log.sms.service.ILogSmsService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 短信日志信息
 * @author 蝉鸣
 */
@Service()
public class LogSmsServiceImpl extends ServiceImpl<LogSmsMapper, LogSms> implements ILogSmsService {

    /**
     * 功能描述:
     * 〈校验是否限流〉
     * @param phone phone
     * @author 蝉鸣
     */
    @Override
    public Integer checkCanSend(String phone,Integer smsFlag) {
        return this.getBaseMapper().checkCanSend(phone, smsFlag, NumberConst.NUM_3,NumberConst.NUM_16);
    }
}

