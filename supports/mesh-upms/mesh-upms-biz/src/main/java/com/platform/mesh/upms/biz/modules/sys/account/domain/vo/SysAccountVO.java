package com.platform.mesh.upms.biz.modules.sys.account.domain.vo;

import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @author 蝉鸣
 */
@Data
@Schema(description = "系统账户VO")
public class SysAccountVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

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
	 * 用户昵称
	 */
	@Schema(description = "用户昵称")
	private String userName;

	/**
	 * 账户昵称
	 */
	@Schema(description = "账户昵称")
	private String nickName;

	/**
	 * 手机号码
	 */
	@Schema(description = "手机号码")
	private String phone;

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
	@SchemaEnum(value = ActiveFlagEnum.class, description = "帐号状态")
	private Integer accountFlag;

	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	@Schema(description = "删除标志")
	private Integer delFlag;

	/**
	 * 最后登陆IP
	 */
	@Schema(description = "最后登陆IP")
	private String loginIp;

	/**
	 * 登录时间
	 */
	@Schema(description = "登录时间")
	private LocalDateTime loginTime;

	/**
	 * 账户所属当前组织
	 */
	@Schema(description = "账户所属当前组织")
	private Long scopeOrgId;

	/**
	 * openId
	 */
	@Schema(description = "openId")
	private Long openId;

}