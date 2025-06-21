package com.platform.mesh.upms.biz.modules.sys.userrolerel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRolePageDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.vo.SysUserRoleRelVO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.ISysUserRoleRelService;
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
 * @description 用户信息
 * @author 蝉鸣
 */
@Tag(description = "SysUserRoleRelController", name = "用户角色信息")
@RestController
public class SysUserRoleRelController extends BaseController {

	@Autowired
	private ISysUserRoleRelService sysUserRoleRelService;

	/**
	 * 功能描述:
	 * 〈获取人员角色分页〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<SysUserRoleRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取人员角色分页")
	@PostMapping("/user/role/page")
	public Result<MPage<SysUserRoleRelVO>> selectPage(@RequestBody SysUserRoleRelPageDTO pageDTO) {
		return Result.success(this.sysUserRoleRelService.selectPage(pageDTO));
	}

	/**
	 * 功能描述:
	 * 〈新增人员-角色关系〉
	 * @param sysUserRoleRelDTO sysUserRoleRelDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增人员-角色关系")
	@Log(moduleName = "成员-人员关系管理", operateType = OperateTypeEnum.INSERT)
	@PostMapping("/user/role/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
	public Result<Boolean> addUserRole(@Validated @RequestBody SysUserRoleRelDTO sysUserRoleRelDTO) {
		return Result.success(sysUserRoleRelService.addUserRole(sysUserRoleRelDTO));
	}

	/**
	 * 功能描述:
	 * 〈删除人员-角色关系〉
	 * @param relId relId
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除人员-角色关系")
	@Log(moduleName = "人员-角色关系管理", operateType = OperateTypeEnum.DELETE)
	@PostMapping("/user/role/delete/{relId}")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
	public Result<Boolean> deleteUserRole(@PathVariable(value = "relId")Long relId) {
		return Result.success(sysUserRoleRelService.deleteUserRole(relId));
	}


}

