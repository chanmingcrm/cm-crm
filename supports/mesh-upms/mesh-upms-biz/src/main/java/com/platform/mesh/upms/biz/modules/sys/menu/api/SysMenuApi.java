package com.platform.mesh.upms.biz.modules.sys.menu.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.sys.menu.domain.bo.AppMenuBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysMenuBO;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.po.SysMenu;
import com.platform.mesh.upms.biz.modules.sys.menu.domain.vo.SysMenuVO;
import com.platform.mesh.upms.biz.modules.sys.menu.service.ISysMenuService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
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
@Hidden
@Tag(description = "SysMenuApi", name = "菜单信息API")
@RestController
public class SysMenuApi extends BaseController {

	@Autowired
	private ISysMenuService sysMenuService;


	/**
	 * 通过账户ID查询用户菜单信息
	 * @param accountId 帐户ID
	 * @return 正常返回:{@link Result<List<SysMenuBO>>}
	 */
	@Operation(summary = "通过账户ID查询用户菜单信息")
	@GetMapping("/api/menu/info/{accountId}")
	public Result<List<SysMenuBO>> getMenuInfoByAccountId(@PathVariable("accountId") Long accountId) {
		List<SysMenu> sysMenus = sysMenuService.getMenuInfoByAccountId(accountId, CollUtil.newArrayList(),CollUtil.newArrayList());
		return Result.success(BeanUtil.copyToList(sysMenus, SysMenuBO.class));
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
	@PostMapping("/api/menu/save/menu")
	public Result<Boolean> addOrEditMenu(@Validated @RequestBody AppMenuBO appMenuBO) {
		return Result.success(sysMenuService.addOrEditMenu(appMenuBO));
	}

	/**
	 * 功能描述:
	 * 〈删除菜单〉
	 * @param moduleIds moduleIds
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "管理业务模块自动生成菜单")
	@PostMapping("/api/menu/app/module/delete")
	public Result<Boolean> appModuleMenuDelete(@Validated @RequestBody List<Long> moduleIds) {
		return Result.success(sysMenuService.appModuleMenuDelete(moduleIds));
	}

	/**
	 * 功能描述:
	 * 〈清除菜单〉
	 * @param moduleIds moduleIds
	 * @return 正常返回:{@link Result<SysMenuVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "管理业务模块自动生成菜单")
	@PostMapping("/api/menu/app/module/clear")
	public Result<Boolean> appModuleMenuClear(@Validated @RequestBody List<Long> moduleIds) {
		return Result.success(sysMenuService.appModuleMenuClear(moduleIds));
	}

	/**
	 * 功能描述:
	 * 〈获取应用模块〉
	 * @return 正常返回:{@link Result<List<Long>>}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@PostMapping("/api/app/modules")
	public Result<List<Long>> getAppModules() {
		return Result.success(sysMenuService.getAppModules());
	}

}
