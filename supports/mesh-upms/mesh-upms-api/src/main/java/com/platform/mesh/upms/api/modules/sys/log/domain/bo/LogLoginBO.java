package com.platform.mesh.upms.api.modules.sys.log.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @description 系统访问记录
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统访问记录")
public class LogLoginBO extends BaseBO {

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

	/**
	 * openId
	 */
	@Schema(description = "openId")
	private Long openId;

	/**
	 * 用户名称
	 */
	@Schema(description = "用户名称")
	private String loginUserName;

	/**
	 * 登录浏览器
	 */
	@Schema(description = "登录浏览器")
	private String loginAgent;

	/**
	 * 登录IP
	 */
	@Schema(description = "登录IP")
	private String loginIp;

	/**
	 * 登录地址
	 */
	@Schema(description = "登录地址")
	private String loginAddr;

	/**
	 * 登录标识
	 */
	@Schema(description = "登录标识")
	private Integer loginFlag;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}
