package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "绑定账号DTO")
public class AccountBindDTO {

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private List<Long> accountIds;

	/**
	 * 租户授权码
	 */
	@Schema(description = "租户授权码")
	private String tenantAuth;
}