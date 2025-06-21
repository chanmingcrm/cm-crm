package com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto;

import com.platform.mesh.core.application.domain.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description sys_role_menu_rel实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "角色菜单关系DTO")
public class SysRoleMenuRelPageDTO extends PageDTO {

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private Long roleId;

	/**
	 * 菜单ID
	 */
	@Schema(description = "菜单ID")
	private Long menuId;


}