package com.platform.mesh.upms.biz.modules.sys.menu.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description sys_user实体
 * @author 蝉鸣
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单VO")
public class SysMenuVO extends SysMenuSVO {

	/**
	 * 子路由
	 */
	@Schema(description = "子路由")
	private List<SysMenuVO> children;

}