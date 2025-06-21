package com.platform.mesh.upms.biz.modules.sys.user.domain.vo;

import com.platform.mesh.core.application.domain.vo.BaseVO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description 登录用户基本信息实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "登录用户基本信息实体")
public class SysUserInfoVO extends BaseVO {

	/**
	 * 用户基本信息
	 */
	@Schema(description = "用户基本信息")
	private SysUserVO sysUserVO;


	/**
	 * 帐户基本信息
	 */
	@Schema(description = "帐户基本信息")
	private AccountVO accountVO;

	/**
	 * 角色组
	 */
	@Schema(description = "角色组")
	private List<SysRoleVO> roleVOS;

	/**
	 * 菜单组
	 */
	@Schema(description = "菜单组")
	private List<SysMenuVO> menuVOS;

	/**
	 * 岗位组
	 */
	@Schema(description = "岗位组")
	private List<SysOrgVO> orgVOS;

	/**
	 * 成员组
	 */
	@Schema(description = "成员组")
	private List<SysMemberVO> memberVOS;


}
