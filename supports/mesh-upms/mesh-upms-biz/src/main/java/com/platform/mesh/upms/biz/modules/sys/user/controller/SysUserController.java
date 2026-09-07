package com.platform.mesh.upms.biz.modules.sys.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserPageDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserInfoVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysUserVO;
import com.platform.mesh.upms.biz.modules.sys.user.service.ISysUserService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 用户信息
 * @author 蝉鸣
 */
@Tag(description = "SysUserController", name = "用户信息")
@RestController
public class SysUserController extends BaseController {

	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 功能描述:
	 * 〈获取用户列表〉
	 * @param sysUserPageDTO sysUserPageDTO
	 * @return 正常返回:{@link Result<MPage<SysUserVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取用户分页")
	@PostMapping("/user/page")
	public Result<PageVO<SysUserVO>> selectPage(@RequestBody SysUserPageDTO sysUserPageDTO) {
		return Result.success(sysUserService.selectPage(sysUserPageDTO));
	}

	/**
	 * 功能描述:
	 * 〈新增用户〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link Result<SysUserVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增用户")
	@PostMapping("/user/add")
	public Result<SysUserVO> addUser(@Validated @RequestBody SysUserDTO sysUserDTO){
		SysUserVO sysUserVO = sysUserService.addUser(sysUserDTO);
		return Result.success(sysUserVO);
	}

	/**
	 * 功能描述:
	 * 〈获取当前用户信息〉
	 * @param userId userId
	 * @return 正常返回:{@link Result<SysUserVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取当前用户信息")
	@Log(moduleName = "用户信息", operateType = OperateTypeEnum.SELECT)
	@GetMapping("/user/get")
	public Result<SysUserVO> getUser(@RequestParam(value = "userId") Long userId){
        SysUserVO sysUserVO = sysUserService.getUserById(userId);
		return Result.success(sysUserVO);
	}

	/**
	 * 功能描述:
	 * 〈获取当前用户信息〉
	 * @return 正常返回:{@link Result<SysUserInfoVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取当前用户信息")
	@GetMapping("/user/login/info")
	public Result<SysUserInfoVO> getUserLoginInfo() {
		Long accountId = UserCacheUtil.getAccountId();
		SysUserInfoVO allInfoById = sysUserService.getUserInfoByAccountId(accountId);
		return Result.success(allInfoById);
	}

	/**
	 * 功能描述:
	 * 〈编辑用户〉
	 * @param sysUserDTO sysUserDTO
	 * @return 正常返回:{@link Result<SysUserVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "编辑用户")
	@PostMapping("/user/edit")
	public Result<SysUserVO> editUser(@Validated @RequestBody SysUserDTO sysUserDTO){
		SysUserVO sysUserInfoVO = sysUserService.editUser(sysUserDTO);
		return Result.success(sysUserInfoVO);
	}

	/**
	 * 功能描述:
	 * 〈删除用户〉
	 * @param userId userId
	 * @return 正常返回:{@link Result<Boolean>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除用户")
	@PostMapping("/user/delete")
	public Result<Boolean> deleteUser(@RequestParam(value = "userId") Long userId){
		Boolean result = sysUserService.deleteSysUser(userId);
		return Result.success(result);
	}

}
