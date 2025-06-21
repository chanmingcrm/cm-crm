package com.platform.mesh.upms.biz.sms.service.manual;

import com.platform.mesh.upms.biz.modules.log.sms.domain.po.LogSms;
import com.platform.mesh.upms.biz.modules.log.sms.service.ILogSmsService;
import com.platform.mesh.utils.http.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 短信业务
 * @author 蝉鸣
 */
@Service
public class SmsServiceManual{

    @Autowired
    private ILogSmsService logSmsService;

    /**
     * 功能描述:
     * 〈校验是否限流〉
     * @param phone phone
     * @author 蝉鸣
     */
    public Integer checkCanSend(String phone,Integer smsFlag) {
        return logSmsService.checkCanSend(phone,smsFlag);
    }

    /**
     * 功能描述:
     * 〈保存日志〉
     * @param phone phone
     * @param smsFlag smsFlag
     * @author 蝉鸣
     */
    public void saveSmsLog(String phone, Integer smsFlag) {
        LogSms logSms = new LogSms();
        logSms.setSmsIp(IpUtil.getHostIp());
        logSms.setSmsAddr(IpUtil.getIpAddr());
        logSms.setSmsAgent(IpUtil.getIpAgent());
        logSms.setSmsPhone(Long.parseLong(phone));
        logSms.setSmsFlag(smsFlag);
        logSms.setCreateTime(LocalDateTime.now());
        logSmsService.save(logSms);
    }
}