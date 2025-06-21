package com.platform.mesh.upms.biz.modules.sys.role.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysRoleBO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 角色信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "SysRoleApi", name = "角色信息API")
@RestController
public class SysRoleApi extends BaseController {

	@Autowired
	private ISysRoleService sysRoleService;


	/**
	 * 通过账户ID查询用户角色信息
	 * @param accountId 帐户ID
	 * @return 正常返回:{@link Result<List<SysRoleBO>>}
	 */
	@Operation(summary = "通过账户ID查询用户角色信息")
	@GetMapping("/api/role/info/{accountId}")
	public Result<List<SysRoleBO>> getRoleInfoByAccountId(@PathVariable("accountId") Long accountId) {
		Long userId = UserCacheUtil.getUserId();
		List<SysRole> sysRoles = sysRoleService.getRoleInfoByUserId(userId);
		return Result.success(BeanUtil.copyToList(sysRoles, SysRoleBO.class));
	}

	/**
	 * 功能描述:
	 * 〈通过ids获取角色信息〉
	 * @param roleIds roleIds
	 * @return 正常返回:{@link Result<List<SysRoleBO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取组织信息缓存")
	@PostMapping("/api/sys/role/by/ids")
	public Result<List<SysRoleBO>> getRoleByIds(@RequestBody List<Long> roleIds) {
		List<SysRole> sysRoles = sysRoleService.listByIds(roleIds);
		List<SysRoleBO> sysRoleBOS = sysRoles.stream().map(sysRole -> BeanUtil.copyProperties(sysRole, SysRoleBO.class)).toList();
		return Result.success(sysRoleBOS);
	}

}
