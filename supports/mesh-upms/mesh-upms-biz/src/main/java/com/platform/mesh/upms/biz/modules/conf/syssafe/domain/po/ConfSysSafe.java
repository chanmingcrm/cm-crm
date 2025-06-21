package com.platform.mesh.upms.biz.modules.conf.syssafe.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.platform.mesh.core.application.domain.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 配置安全性DTO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "conf_sys_safe", autoResultMap = true)
public class ConfSysSafe extends BasePO {


    /**
    * 主键ID
    */
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    /**
    * 登录允许客户端
    */
    private String loginClient;

    /**
    * 登录失败重试次数
    */
    private Integer loginRetry;

    /**
    * 登录验证码
    */
    private Integer loginCap;

    /**
    * 登录多因素认证
    */
    private Integer loginMfa;

    /**
    * 密码定期更新
    */
    private Integer passUpdate;

    /**
    * 密码强度校验
    */
    private Integer passCheck;

    /**
    * 密码长度校验
    */
    private Integer passLength;

    /**
    * 密码数字校验
    */
    private Integer passNum;

    /**
    * 密码小写字母校验
    */
    private Integer passLChar;

    /**
    * 密码大写字母校验
    */
    private Integer passUChar;

    /**
    * 密码特殊字母校验
    */
    private Integer passSChar;

    /**
    * 密码重置短信验证
    */
    private Integer passResetSms;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeUserId;

    /**
     * 组织ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long scopeOrgId;

}