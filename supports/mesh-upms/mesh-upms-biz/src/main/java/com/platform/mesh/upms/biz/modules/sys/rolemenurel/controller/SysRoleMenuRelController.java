package com.platform.mesh.upms.biz.modules.sys.rolemenurel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.vo.SysRoleMenuRelVO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.service.ISysRoleMenuRelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 角色菜单信息
 * @author 蝉鸣
 */
@Tag(description = "SysRoleMenuRelController", name = "角色菜单信息")
@RestController
public class SysRoleMenuRelController extends BaseController {

	@Autowired
	private ISysRoleMenuRelService sysRoleMenuRelService;

	/**
	 * 功能描述:
	 * 〈获取角色菜单分页〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<SysRoleMenuRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取角色菜单分页")
	@PostMapping("/role/menu/page")
	public Result<MPage<SysRoleMenuRelVO>> selectPage(@RequestBody SysRoleMenuRelPageDTO pageDTO) {
		return Result.success(this.sysRoleMenuRelService.selectPage(pageDTO));
	}

	/**
	 * 功能描述:
	 * 〈新增角色-菜单关系〉
	 * @param sysRoleMenuRelDTO sysRoleMenuRelDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增角色-菜单关系")
	@Log(moduleName = "成员-人员关系管理", operateType = OperateTypeEnum.INSERT)
	@PostMapping("/role/menu/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
	public Result<Boolean> addRoleMenu(@Validated @RequestBody SysRoleMenuRelDTO sysRoleMenuRelDTO) {
		return Result.success(sysRoleMenuRelService.addRoleMenu(sysRoleMenuRelDTO));
	}

	/**
	 * 功能描述:
	 * 〈删除角色-菜单关系〉
	 * @param relId relId
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除角色-菜单关系")
	@Log(moduleName = "角色-菜单关系管理", operateType = OperateTypeEnum.DELETE)
	@PostMapping("/role/menu/delete/{relId}")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
	public Result<Boolean> deleteRoleMenu(@PathVariable(value = "relId")Long relId) {
		return Result.success(sysRoleMenuRelService.deleteRoleMenu(relId));
	}


}
