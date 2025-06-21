package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @description sys_user实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户BO")
public class SysUserBO extends BaseBO {

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private Long userId;

	/**
	 * 用户昵称
	 */
	@Schema(description = "用户昵称")
	private String nickName;

	/**
	 * 用户头像
	 */
	@Schema(description = "用户头像")
	private String avatar;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 身份证唯一编号
	 */
	@Schema(description = "身份证唯一编号")
	private String idCard;

	/**
	 * 用户性别
	 */
	@Schema(description = "用户性别")
	private String gender;

	/**
	 * 帐号状态（0正常 1停用）
	 */
	@Schema(description = "帐号状态（0正常 1停用）")
	private Integer userFlag;

	/**
	 * 最后登录IP
	 */
	@Schema(description = "最后登录IP")
	private String loginIp;

	/**
	 * 最后登录时间
	 */
	@Schema(description = "最后登录时间")
	private LocalDateTime loginTime;

}