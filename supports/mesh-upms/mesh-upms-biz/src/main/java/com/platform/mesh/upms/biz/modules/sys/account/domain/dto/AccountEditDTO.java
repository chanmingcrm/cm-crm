package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "修改账号DTO")
public class AccountEditDTO {

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

	/**
	 * 账号码
	 */
	@Schema(description = "账号码")
	private String accountCode;

	/**
	 * 账户昵称
	 */
	@Schema(description = "账户昵称")
	private String nickName;

	/**
	 * 用户头像
	 */
	@Schema(description = "用户头像")
	private String avatar;

	/**
	 * 帐户类型
	 */
	@Schema(description = "帐户类型")
	private Integer sourceFlag;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	@Schema(description = "帐号状态")
	private Integer accountFlag;
}