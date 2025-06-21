package com.platform.mesh.upms.biz.modules.sys.user.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysAccountInfoBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import com.platform.mesh.upms.biz.modules.sys.user.service.ISysUserService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 用户信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "SysUserApi", name = "用户信息API")
@RestController
public class SysUserApi extends BaseController {

	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 功能描述:
	 * 〈通过用户名查询用户(此接口会隐藏部分信息,请对号入座使用)〉
	 * @param accountCode accountCode
	 * @param sourceFlag sourceFlag
	 * @return 正常返回:{@link Result< SysAccountInfoBO >}
	 * @author 蝉鸣
	 */
	@AuthIgnore
	@Operation(summary = "获取当前用户信息(认证中心服务专用)")
	@GetMapping("/api/user/info/{accountCode}/{sourceFlag}")
	public Result<SysAccountInfoBO> info(@PathVariable("accountCode") String accountCode, @PathVariable("sourceFlag") Integer sourceFlag) {
		SysAccountInfoBO sysUserInfo = sysUserService.getUserInfoByAccountCode(accountCode, sourceFlag);
		return Result.success(sysUserInfo);
	}

	/**
	 * 通过账户ID查询用户信息
	 * @param userId 用户ID
	 * @return 用户对象信息
	 */
	@AuthIgnore
	@Operation(summary = "通过账户ID查询用户信息")
	@GetMapping("/api/user/info/{userId}")
	public Result<SysUserBO> getUserInfoByUserId(@PathVariable("userId") Long userId) {
		SysUser sysUser = sysUserService.getById(userId);
		return Result.success(BeanUtil.copyProperties(sysUser, SysUserBO.class));
	}

}
