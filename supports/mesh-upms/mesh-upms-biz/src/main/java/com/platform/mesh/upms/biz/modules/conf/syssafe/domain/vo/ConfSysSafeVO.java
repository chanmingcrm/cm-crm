package com.platform.mesh.upms.biz.modules.conf.syssafe.domain.vo;

import java.time.LocalDateTime;
import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 配置安全性VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description ="配置安全性VO")
public class ConfSysSafeVO extends BaseVO {



    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 登录允许客户端
     */
    @Schema(description = "登录允许客户端")
    private String loginClient;

    /**
     * 登录失败重试次数
     */
    @Schema(description = "登录失败重试次数")
    private Integer loginRetry;

    /**
     * 登录验证码
     */
    @Schema(description = "登录验证码")
    private Integer loginCap;

    /**
     * 登录多因素认证
     */
    @Schema(description = "登录多因素认证")
    private Integer loginMfa;

    /**
     * 密码定期更新
     */
    @Schema(description = "密码定期更新")
    private Integer passUpdate;

    /**
     * 密码强度校验
     */
    @Schema(description = "密码强度校验")
    private Integer passCheck;

    /**
     * 密码长度校验
     */
    @Schema(description = "密码长度校验")
    private Integer passLength;

    /**
     * 密码数字校验
     */
    @Schema(description = "密码数字校验")
    private Integer passNum;

    /**
     * 密码小写字母校验
     */
    @Schema(description = "密码小写字母校验")
    private Integer passLChar;

    /**
     * 密码大写字母校验
     */
    @Schema(description = "密码大写字母校验")
    private Integer passUChar;

    /**
     * 密码特殊字母校验
     */
    @Schema(description = "密码特殊字母校验")
    private Integer passSChar;

    /**
     * 密码重置短信验证
     */
    @Schema(description = "密码重置短信验证")
    private Integer passResetSms;

    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者ID
     */
    @Schema(description = "更新者ID")
    private Long updateUserId;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 数据权限用户ID
     */
    @Schema(description = "数据权限用户ID")
    private Long scopeUserId;

    /**
     * 数据权限机构ID
     */
    @Schema(description = "数据权限机构ID")
    private Long scopeOrgId;

}