package com.platform.mesh.upms.biz.sms.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.upms.biz.modules.log.sms.domain.po.LogSms;
import com.platform.mesh.upms.biz.modules.log.sms.service.ILogSmsService;
import com.platform.mesh.upms.biz.sms.domain.dto.SmsSendDTO;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.format.FormatUtil;
import com.platform.mesh.utils.http.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 租户基础
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
        logSms.setSmsAddr(IpUtil.getIpAddr(logSms.getSmsIp()));
        logSms.setSmsAgent(IpUtil.getIpAgent());
        logSms.setSmsPhone(Long.parseLong(phone));
        logSms.setSmsFlag(smsFlag);
        logSms.setCreateTime(LocalDateTime.now());
        logSmsService.save(logSms);
    }

    /**
     * 功能描述:
     * 〈校验签名〉
     * @param sendDTO sendDTO
     * @author 蝉鸣
     */
    public boolean checkSign(SmsSendDTO sendDTO){
        if(ObjectUtil.isEmpty(sendDTO.getSign()) || ObjectUtil.isEmpty(sendDTO.getEncryptCode())){
            return false;
        }
        //判断校验码格式是否正确
        String decryptStr = FormatUtil.decryptStr(sendDTO.getSign(), sendDTO.getEncryptCode(),StrConst.SMS_SIGN);
        if(ObjectUtil.isNull(decryptStr)){
            return false;
        }
        if(decryptStr.contains(SymbolConst.COLON)){
            List<String> decryList = Arrays.stream(decryptStr.split(SymbolConst.AT)).map(String::trim).toList();
            String first = CollUtil.getFirst(decryList);
            String phone = first.replace(StrConst.SMS_SIGN, SymbolConst.BLANK).trim();
            if(!phone.equals(sendDTO.getPhone())){
                return false;
            }
            String last = CollUtil.getLast(decryList);
            LocalDateTime localDateTime = DateTimeUtil.strToLocalDateTime(last);
            return localDateTime.isAfter(LocalDateTime.now().minusMinutes(NumberConst.NUM_1));
        }
        return false;
    }

}