package com.platform.mesh.upms.biz.sms.service;

import com.platform.mesh.upms.biz.sms.domain.dto.SmsSendDTO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 短信服务信息
 * @author 蝉鸣
 */
public interface ISmsService {

    /**
     * 功能描述:
     * 〈发送验证码〉
     * @param sendDTO sendDTO
     * @author 蝉鸣
     */
    void sendSmsCheckCode(SmsSendDTO sendDTO);
}
