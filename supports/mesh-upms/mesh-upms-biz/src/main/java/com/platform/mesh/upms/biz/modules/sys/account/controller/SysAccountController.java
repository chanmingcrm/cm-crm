package com.platform.mesh.upms.biz.modules.sys.account.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.sys.account.domain.dto.*;
import com.platform.mesh.upms.biz.modules.sys.account.domain.po.SysAccount;
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
	 * @param accountId accountId
	 * @return 正常返回:{@link Result<SysAccountVO>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取账户信息")
	@GetMapping("/account/get/{accountId}")
//	@PreAuthorize("@rolePermission.hasPermi('upms:account:info')")
	public Result<SysAccountVO> getByOpenId(@PathVariable("accountId") Long accountId) {
		SysAccountVO sysAccountVO = sysUserAccountService.getByOpenId(accountId);
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
	 * 〈切换账户/成本中心〉
	 * @param changeDTO changeDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "切换账户/成本中心")
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

}
