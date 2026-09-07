package com.platform.mesh.upms.biz.sms.service.impl;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.SmsFlagEnum;
import com.platform.mesh.redis.service.RedissonUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.upms.biz.sms.domain.dto.SmsSendDTO;
import com.platform.mesh.upms.biz.sms.exception.SmsExceptionEnum;
import com.platform.mesh.upms.biz.sms.service.ISmsService;
import com.platform.mesh.upms.biz.sms.service.manual.SmsServiceManual;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.comm.constant.SupplierConstant;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 租户基础
 * @author 蝉鸣
 */
@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private SmsServiceManual smsServiceManual;

    /**
     * 功能描述:
     * 〈发送验证码〉
     * @param sendDTO sendDTO
     * @author 蝉鸣
     */
    @Override
    public void sendSmsCheckCode(SmsSendDTO sendDTO) {

        //校验手机号是否正确
        if(!PhoneUtil.isPhone(sendDTO.getPhone())){
            throw SmsExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //校验当前手机号码验证码是否已存在,避免重复发送
        if(RedissonUtil.hasKey(sendDTO.getPhone())){
            throw SmsExceptionEnum.ADD_HAS_EXISTS.getBaseException();
        }
        //校验签名是否合法
        boolean checkSign = smsServiceManual.checkSign(sendDTO);
        if(!checkSign){
            throw SmsExceptionEnum.ADD_NO_SERVER.getBaseException();
        }
        //检测限流
        //如果当前手机号码3分钟内多次发送,将限制发送10分钟
        //如果当前手机号码短信每天超过20条，则限制发送
        //如果当前IP发送超过100条，则限制发送
        Integer canSend = smsServiceManual.checkCanSend(sendDTO.getPhone(),sendDTO.getSmsFlag());
        if(canSend > NumberConst.NUM_0){
            throw SmsExceptionEnum.ADD_LIMIT_SERVER.getBaseException();
        }

        //发送短信
        //在创建完SmsBlend实例后，再未手动调用注销的情况下框架会持有该实例，可以直接通过指定configId来获取想要的配置，如果你想使用
        //负载均衡形式获取实例，只要使用getSmsBlend的无参重载方法即可，如果你仅有一个配置，也可以使用该方法
        SmsBlend smsBlend = SmsFactory.getSmsBlend(SupplierConstant.ALIBABA);
        //生成验证码信息
        String code = RandomUtil.randomNumbers(NumberConst.NUM_6);
        SmsResponse smsResponse = smsBlend.sendMessage(sendDTO.getPhone(),code);
        if(!smsResponse.isSuccess()){
            throw SmsExceptionEnum.ADD_NO_SERVER.getBaseException();
        }
        //设置缓存 5分钟有效期
        SmsFlagEnum enumByValue = BaseEnum.getEnumByValue(SmsFlagEnum.class, sendDTO.getSmsFlag(), SmsFlagEnum.CHECK);
        String phoneKey = CacheConstants.SMS_PHONE_CACHE.concat(SymbolConst.COLON).concat(sendDTO.getPhone()).concat(SymbolConst.COLON).concat(enumByValue.getDesc());
        RedissonUtil.setCacheObject(phoneKey, code, Duration.ofMinutes(NumberConst.NUM_5));
        //保存短信日志
        smsServiceManual.saveSmsLog(sendDTO.getPhone(),sendDTO.getSmsFlag());
    }
}