package com.platform.mesh.upms.api.modules.sys.account.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统账户BO")
public class SysAccountBO extends BaseBO {

	/**
	 * 账户ID
	 */
	@Schema(description = "账户ID")
	private Long accountId;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

	/**
	 * 账号码
	 */
	@Schema(description = "账号码")
	private String accountCode;

	/**
	 * 校验码
	 */
	@Schema(description = "校验码")
	private String checkCode;

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
	 * 账户状态
	 */
	@Schema(description = "账户状态")
	private Integer accountFlag;

	/**
	 * 账户所属当前根组织：用于冗余公司类型ID
	 */
	@Schema(description = "账户所属当前根组织：用于冗余公司类型ID")
	private Long scopeRootId;

	/**
	 * 账户所属当前组织
	 */
	@Schema(description = "账户所属当前组织")
	private Long scopeOrgId;

}