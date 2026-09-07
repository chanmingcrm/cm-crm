package com.platform.mesh.upms.biz.modules.sys.account.controller;

import cn.hutool.json.JSONObject;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.sys.account.domain.dto.*;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.AccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.domain.vo.SysAccountVO;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 帐户信息
 * @author 蝉鸣
 */
@Tag(description = "SysUserAccountController", name = "帐户信息")
@RestController
public class SysAccountController extends BaseController {

	@Autowired
	private ISysAccountService sysUserAccountService;


	/**
	 * 功能描述:
	 * 〈获取账户列表〉
	 * @param accountPageDTO accountPageDTO
	 * @return 正常返回:{@link Result<MPage<SysAccountVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取账户分页")
	@PostMapping("/account/page")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:page')")
	public Result<PageVO<SysAccountVO>> selectPage(@Valid @RequestBody AccountPageDTO accountPageDTO) {
		return Result.success(sysUserAccountService.selectPage(accountPageDTO));
	}

    /**
     * 功能描述:
     * 〈获取账户详情〉
     * @param openId openId
     * @return 正常返回:{@link Result<SysAccountVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取账户信息")
    @GetMapping("/account/get/{openId}")
    public Result<SysAccountVO> getByOpenId(@PathVariable("openId") Long openId) {
        SysAccountVO sysAccountVO = sysUserAccountService.getByOpenId(openId);
        return Result.success(sysAccountVO);
    }

	/**
	 * 功能描述:
	 * 〈获取账户详情〉
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<SysAccountVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取账户信息")
	@GetMapping("/account/get/by/{accountId}")
	public Result<SysAccountVO> getByAccountId(@PathVariable("accountId") Long accountId) {
		SysAccountVO sysAccountVO = sysUserAccountService.getByAccountId(accountId);
		return Result.success(sysAccountVO);
	}


	/**
	 * 功能描述:
	 * 〈新增账户〉
	 * @param accountAddDTO accountAddDTO
	 * @return 正常返回:{@link Result<AccountVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "新增账户")
	@PostMapping("/account/add")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:add')")
	public Result<AccountVO> addAccount(@RequestBody AccountAddDTO accountAddDTO) {
		return Result.success(sysUserAccountService.addAccount(accountAddDTO));
	}

	/**
	 * 功能描述:
	 * 〈编辑账户〉
	 * @param accountEditDTO accountEditDTO
	 * @return 正常返回:{@link Result<AccountVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "编辑账户")
	@PostMapping("/account/edit")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:edit')")
	public Result<AccountVO> editAccount(@RequestBody AccountEditDTO accountEditDTO) {
		return Result.success(sysUserAccountService.editAccount(accountEditDTO));
	}

	/**
	 * 功能描述:
	 * 〈切换账户租户/成本中心〉
	 * @param changeDTO changeDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "切换账户租户/成本中心")
	@PostMapping("/account/change")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:edit')")
	public Result<Boolean> changeAccount(@RequestBody AccountChangeDTO changeDTO) {
		return Result.success(sysUserAccountService.changeAccount(changeDTO));
	}

	/**
	 * 功能描述:
	 * 〈修改密码〉
	 * @param passwordDTO passwordDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "修改密码")
	@PostMapping("/account/change/password")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:edit')")
	public Result<Boolean> changePassword(@RequestBody AccountPasswordDTO passwordDTO) {
		return Result.success(sysUserAccountService.changePassword(passwordDTO));
	}

	/**
	 * 功能描述:
	 * 〈重置密码〉
	 * @param resetDTO resetDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "重置密码")
	@PostMapping("/account/reset/password")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:edit')")
	public Result<Boolean> resetPassword(@RequestBody AccountResetDTO resetDTO) {
		return Result.success(sysUserAccountService.resetPassword(resetDTO));
	}

	/**
	 * 功能描述:
	 * 〈删除账户〉
	 * @param openId openId
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "删除账户")
	@DeleteMapping("/account/delete/{openId}")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:delete')")
	public Result<Boolean> deleteByOpenId(@PathVariable("openId") Long openId) {
		sysUserAccountService.deleteByOpenId(openId);
		return Result.success();
	}

	/**
	 * 功能描述:
	 * 〈账户类型〉
	 * @return 正常返回:{@link Result<List<SourceFlagEnum>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "账户类型")
	@PostMapping("/account/source/list")
	public Result<JSONObject> selectAccountSourceList() {
		return Result.success(sysUserAccountService.selectAccountSourceList());
	}

	/**
	 * 功能描述:
	 * 〈绑定租户授权账户〉
	 * @param bindDTO bindDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "绑定租户授权账户")
	@PostMapping("/account/bind/tenant/by/auth")
	public Result<Boolean> bindByTenantAuth(@RequestBody AccountBindDTO bindDTO) {
		return Result.success(sysUserAccountService.bindByTenantAuth(bindDTO));
	}

	/**
	 * 功能描述:
	 * 〈查询已经绑定的账户类型〉
	 * @return 正常返回:{@link Result<List<Integer>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询已经绑定的账户类型")
	@PostMapping("/account/bind/source/type")
	public Result<List<Integer>> bindSourceType() {
		Long userId = UserCacheUtil.getUserId();
		return Result.success(sysUserAccountService.bindSourceType(userId));
	}

	/**
	 * 功能描述:
	 * 〈查询已经绑定的账户类型〉
	 * @param sourceFlag sourceFlag
	 * @return 正常返回:{@link Result<Void>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "查询已经绑定的账户类型")
	@PostMapping("/account/un/bind")
	public Result<Void> unBindAccount(@RequestParam("sourceFlag") Integer sourceFlag) {
		Long userId = UserCacheUtil.getUserId();
		sysUserAccountService.unBindAccount(sourceFlag,userId);
		return Result.success();
	}

}
