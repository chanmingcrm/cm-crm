package com.platform.mesh.upms.biz.modules.sys.user.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.swagger.config.enums.SchemaEnum;
import com.platform.mesh.upms.api.modules.sys.user.enums.ActiveFlagEnum;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 用户VO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户VO")
public class SysUserVO extends BaseVO {

	/**
	 * 用户ID
	 */
	@Schema(description = "用户id")
	private Long userId;

//	/**
//	 * 用户OpenId
//	 */
//	@Schema(description = "用户OpenId")
//	private String openId;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String userName;

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
	 * 用户性别
	 */
	@Schema(description = "用户性别")
	private Integer gender;

	/**
	 * 用户状态（ActiveFlagEnum）
	 */
	@SchemaEnum(value = ActiveFlagEnum.class, description = "用户状态")
	private Integer userFlag;

	/**
	 * 身份证唯一编号
	 */
	@Schema(description = "身份证唯一编号")
	private String idCard;


	/**
	 * 管理员状态(AdminFlagEnum)
	 */
	@Schema(description = "管理员状态(AdminFlagEnum)")
	private Integer adminFlag;


	/**
	 * 删除标志（0代表存在 2代表删除）
	 */
	@SchemaEnum(value = YesOrNoEnum.class, description = "删除标志（0代表存在 2代表删除）")
	private Integer delFlag;

	/**
	 * 最后登陆IP
	 */
	@Schema(description = "最后登陆IP")
	private String loginIp;

	/**
	 * 最后登录时间
	 */
	@Schema(description = "最后登录时间")
	private LocalDateTime loginDate;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 角色组
	 */
	@Schema(description = "角色组")
	private List<SysRoleVO> roleVOS;

	/**
	 * 岗位组
	 */
	@Schema(description = "岗位组")
	private List<UserOrgVO> orgVOS;
}