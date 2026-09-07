package com.platform.mesh.upms.biz.modules.sys.menu.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.SysMenuPageDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.dto.RouteDTO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuSVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.RouteVO;
import com.platform.mesh.upms.biz.modules.sys.menu.service.ISysMenuService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 菜单信息
 * @author 蝉鸣
 */
@Tag(description = "SysMenuController", name = "菜单信息")
@RestController
public class SysMenuController extends BaseController {

	@Autowired
	private ISysMenuService sysMenuService;

	@Operation(summary = "获取组织列表")
	@PostMapping("/menu/page")
	public Result<PageVO<SysMenuSVO>> getMenuPage(@RequestBody SysMenuPageDTO pageEntity) {
		MPage<SysMenu> page = sysMenuService.getMenuPage(pageEntity);
		PageVO<SysMenuSVO> voPage = MPageUtil.convertToVO(page, SysMenuSVO.class);
		return Result.success(voPage);
	}

	/**
	 * 功能描述:
	 * 〈获取当前菜单信息〉
	 * @param menuId menuId
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取当前菜单信息")
	@GetMapping("/menu/get/{menuId}")
	public Result<SysMenuVO> getMenuInfoById(@PathVariable("menuId")Long menuId) {
		SysMenuVO sysMenuVO = sysMenuService.getMenuInfoById(menuId);
		return Result.success(sysMenuVO);
	}

	/**
	 * 功能描述:
	 * 〈获取单一结构体的菜单信息〉
	 * @param menuId menuId
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取当前菜单单层对象信息")
	@GetMapping("/menu/get/simple/{menuId}")
	public Result<SysMenuSVO> getSMenuInfoById(@PathVariable("menuId")Long menuId) {
		SysMenuSVO sysMenuSVO = sysMenuService.getSMenuInfoById(menuId);
		return Result.success(sysMenuSVO);
	}


	/**
	 * 功能描述:
	 * 〈获取菜单树结构〉
	 * @param menuId menuId
	 * @return 正常返回:{@link Result<List<SysMenuVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取菜单树结构")
	@GetMapping("/menu/tree")
	public Result<List<SysMenuVO>> getMenuTree(@RequestParam(value = "menuId",required = false)Long menuId) {
		List<SysMenuVO> menuTree = sysMenuService.getMenuTree(menuId);
		return Result.success(menuTree);
	}

	/**
	 * 功能描述:
	 * 〈获取路由树结构〉
	 * @param routeDTO routeDTO
	 * @return 正常返回:{@link Result<RouteVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取路由树结构")
	@PostMapping("/menu/route/info")
	public Result<RouteVO> getMenuRouteInfo(@RequestBody RouteDTO routeDTO) {
		RouteVO routeInfo = sysMenuService.getMenuRouteInfo(routeDTO);
		return Result.success(routeInfo);
	}

	/**
	 * 功能描述:
	 * 〈新增菜单〉
	 * @param menuDTO menuDTO
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增菜单")
	@Log(moduleName = "菜单管理", operateType = OperateTypeEnum.INSERT)
	@PostMapping("/menu/add")
//	@PreAuthorize("@rolePermission.hasPermi('upms:menu:add')")
	public Result<SysMenuVO> addMenu(@Validated @RequestBody SysMenuDTO menuDTO) {
		return Result.success(sysMenuService.addMenu(menuDTO));
	}

	/**
	 * 功能描述:
	 * 〈修改菜单〉
	 * @param menuDTO menuDTO
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "修改菜单")
	@Log(moduleName = "菜单管理", operateType = OperateTypeEnum.UPDATE)
	@PostMapping("/menu/edit")
//	@PreAuthorize("@rolePermission.hasPermi('upms:menu:edit')")
	public Result<SysMenuVO> editMenu(@Validated @RequestBody SysMenuDTO menuDTO) {
		return Result.success(sysMenuService.editMenu(menuDTO));
	}

	/**
	 * 功能描述:
	 * 〈删除菜单〉
	 * @param menuId menuId
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除菜单")
	@Log(moduleName = "菜单管理", operateType = OperateTypeEnum.DELETE)
	@PostMapping("/menu/delete/{menuId}")
//	@PreAuthorize("@rolePermission.hasPermi('upms:menu:delete')")
	public Result<Boolean> deleteMenu(@PathVariable(value = "menuId",required = false)Long menuId) {
		return Result.success(sysMenuService.deleteMenu(menuId));
	}


	/**
	 * 功能描述:
	 * 〈新增菜单〉
	 * @param appMenuBO appMenuBO
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "管理业务模块自动生成菜单")
	@Log(moduleName = "菜单管理", operateType = OperateTypeEnum.INSERT)
	@PostMapping("/menu/auto")
//	@PreAuthorize("@rolePermission.hasPermi('upms:menu:add')")
	public Result<Boolean> menuAuto(@Validated @RequestBody AppMenuBO appMenuBO) {
		return Result.success(sysMenuService.addOrEditMenu(appMenuBO));
	}
}
