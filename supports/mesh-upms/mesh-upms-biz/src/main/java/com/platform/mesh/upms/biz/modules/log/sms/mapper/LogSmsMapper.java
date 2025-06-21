package com.platform.mesh.upms.biz.modules.log.sms.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.biz.modules.log.sms.domain.po.LogSms;
import org.apache.ibatis.annotations.Param;

/**
 * @description
 * @author 蝉鸣
 */
public interface LogSmsMapper extends BaseMapper<LogSms> {

    @InterceptorIgnore(tenantLine = "true")
    Integer checkCanSend(@Param("phone") String phone, @Param("smsFlag") Integer smsFlag, @Param("minLimit") Integer minLimit, @Param("dayLimit") Integer dayLimit);
}

