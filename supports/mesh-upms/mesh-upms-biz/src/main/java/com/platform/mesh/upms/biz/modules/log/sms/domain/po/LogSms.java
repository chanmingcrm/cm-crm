package com.platform.mesh.upms.biz.modules.log.sms.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 短信日志实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("log_sms")
public class LogSms extends BasePO {

    /**
    * 日志ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 手机号码
     */
    private Long smsPhone;

    /**
     * 发送浏览器
     */
    private String smsAgent;

    /**
     * 发送IP
     */
    private String smsIp;

    /**
     * 发送地址
     */
    private String smsAddr;

    /**
     * 发送类型
     */
    private Integer smsFlag;

    /**
    * 创建时间
    */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}

