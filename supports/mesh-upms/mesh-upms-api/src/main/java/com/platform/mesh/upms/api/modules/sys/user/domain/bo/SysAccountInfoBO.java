package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @description 登录用户基本信息实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "登录用户基本信息实体")
public class SysAccountInfoBO extends BaseBO {

	/**
	 * 用户基本信息
	 */
	@Schema(description = "用户基本信息")
	private SysUserBO sysUserBO;

	/**
	 * 帐户基本信息
	 */
	@Schema(description = "帐户基本信息")
	private SysAccountBO accountBO;

	/**
	 * 角色组
	 */
	@Schema(description = "角色组")
	private List<SysRoleBO> roleBOS;

	/**
	 * 菜单组
	 */
	@Schema(description = "菜单组")
	private List<SysMenuBO> menuBOS;

	/**
	 * 岗位组
	 */
	@Schema(description = "岗位组")
	private List<SysOrgBO> orgBOS;

}
