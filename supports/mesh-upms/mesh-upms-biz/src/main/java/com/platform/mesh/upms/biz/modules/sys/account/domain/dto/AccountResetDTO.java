package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 修改账号成本中心
 * @author 蝉鸣
 */
@Data
@Schema(description = "修改账号成本中心")
public class AccountResetDTO {

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	private String phone;

	/**
	 * 新密码
	 */
	@Schema(description = "新密码")
	private String newPassword;

	/**
	 * 校验码
	 */
	@Schema(description = "校验码")
	private String encryptCode;

	/**
	 * 校验码
	 */
	@Schema(description = "校验码")
	private String smsCode;
}