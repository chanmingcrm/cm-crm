package com.platform.mesh.upms.biz.modules.sys.account.domain.vo;

import com.platform.mesh.security.enums.AccountTypeEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "账户VO")
public class AccountVO  {

	/**
	 * openId
	 */
	@Schema(description = "openId")
	private Long openId;

	/**
	 * 账号码
	 */
	@Schema(description = "账号ID")
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
	@SchemaEnum(value = SourceFlagEnum.class, description = "帐户类型")
	private Integer sourceFlag;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	@SchemaEnum(value = AccountTypeEnum.class, description = "帐号状态")
	private Integer accountFlag;

	/**
	 * 最后登陆IP
	 */
	@Schema(description = "最后登陆IP")
	private String loginIp;

	/**
	 * 登录时间
	 */
	@Schema(description = "用户ID")
	private LocalDateTime loginTime;

	/**
	 * 账户所属当前组织
	 */
	@Schema(description = "账户所属当前组织")
	private Long scopeOrgId;

}