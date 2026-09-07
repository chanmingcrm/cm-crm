package com.platform.mesh.upms.biz.modules.log.login.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

                        
/**
 * @description 日志(LogLogin)VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description="日志")
public class LogLoginVO extends BaseVO {

    /**
     * 用户ID
     */
    @Schema(description = "创建人")
    private Long userId;

    /**
     * 账户ID
     */
    @Schema(description = "创建人")
    private Long accountId;

    /**
     * openID
     */
    @Schema(description = "创建人")
    private Long openId;

    /**
     * 登录用户名称
     */
    @Schema(description = "创建人")
    private String loginUserName;

    /**
     * 登录浏览器
     */
    @Schema(description = "创建人")
    private String loginAgent;

    /**
     * 登录Ip
     */
    @Schema(description = "创建人")
    private String loginIp;

    /**
     * 登录地址
     */
    @Schema(description = "创建人")
    private String loginAddr;

    /**
     * 登录URL
     */
    @Schema(description = "创建人")
    private String loginUrl;

    /**
     * 登录参数
     */
    @Schema(description = "创建人")
    private String loginParam;

    /**
     * 登录标识
     */
    @Schema(description = "创建人")
    private Integer loginFlag;

    /**
     * 备注
     */
    @Schema(description = "创建人")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建人")
    private LocalDateTime createTime;

}
