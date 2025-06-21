package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 修改账号成本中心
 * @author 蝉鸣
 */
@Data
@Schema(description = "修改账号成本中心")
public class AccountChangeDTO {

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

	/**
	 * 账户所属当前组织
	 */
	@Schema(description = "账户所属当前组织")
	private Long scopeOrgId;

}