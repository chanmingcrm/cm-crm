package com.platform.mesh.upms.api.modules.sys.user.domain.bo;

import com.platform.mesh.core.application.domain.bo.BaseBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description 菜单BO
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单BO")
public class SysMenuBO extends BaseBO {

	/**
	 * 菜单自增ID
	 */
	@Schema(description = "菜单自增ID")
	private Long id;

	/**
	 * 菜单类型(应用，菜单，页面，按钮)
	 */
	@Schema(description = "菜单类型")
	private Integer menuType;

	/**
	 * 菜单标识
	 */
	@Schema(description = "菜单标识")
	private String menuMac;

	/**
	 * 菜单名称
	 */
	@Schema(description = "菜单名称")
	private String menuName;




}