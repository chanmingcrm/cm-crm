package com.platform.mesh.upms.biz.modules.sys.account.domain.dto;

import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "添加账号DTO")
public class AccountAddDTO {
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
	 * 加密码
	 */
	@Schema(description = "加密码")
	private String encryptCode;

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
	@SchemaEnum(value = SourceFlagEnum.class,description = "帐号状态",required = true)
	private Integer sourceFlag;

	/**
	 * 帐号状态（1激活 2停用）
	 */
	@SchemaEnum(value = ActiveFlagEnum.class,description = "帐号状态",required = true)
	private Integer accountFlag;

	/**
	 * 账户所属当前组织
	 */
	@Schema(description = "账户所属当前组织")
	private Long scopeOrgId;
}