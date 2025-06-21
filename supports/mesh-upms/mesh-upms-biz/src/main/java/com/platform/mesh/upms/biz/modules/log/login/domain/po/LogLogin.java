package com.platform.mesh.upms.biz.modules.log.login.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
                        
/**
 * @description 登录日志(LogLogin)实体类
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("log_login")
public class LogLogin extends BasePO {

    /**
    * 日志ID
    */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * openID
     */
    private Long openId;

    /**
     * 登录用户名称
     */
    private String loginUserName;

    /**
     * 登录浏览器
     */
    private String loginAgent;

    /**
     * 登录Ip
     */
    private String loginIp;

    /**
     * 登录地址
     */
    private String loginAddr;

    /**
     * 登录URL
     */
    private String loginUrl;

    /**
     * 登录参数
     */
    private String loginParam;

    /**
     * 登录标识
     */
    private Integer loginFlag;

    /**
     * 备注
     */
    private String remark;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

}

