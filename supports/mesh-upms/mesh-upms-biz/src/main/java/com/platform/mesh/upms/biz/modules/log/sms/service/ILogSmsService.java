package com.platform.mesh.upms.biz.modules.log.sms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.biz.modules.log.sms.domain.po.LogSms;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 短信日志信息
 * @author 蝉鸣
 */
public interface ILogSmsService extends IService<LogSms> {


    /**
     * 功能描述:
     * 〈校验是否限流〉
     * @param phone phone
     * @author 蝉鸣
     */
    Integer checkCanSend(String phone,Integer smsFlag);
}

