package com.platform.mesh.upms.biz.modules.sys.account.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.biz.modules.sys.account.service.ISysAccountService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 用户信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "SysAccountApi", name = "账户信息API")
@RestController
public class SysAccountApi extends BaseController {


    @Autowired
    private ISysAccountService sysAccountService;

    /**
     * 通过账户ID查询账户信息
     * @param accountId 帐户ID
     * @return 用户对象信息
     */
    @AuthIgnore
    @Operation(summary = "通过账户ID查询账户信息")
    @GetMapping("/api/account/info/{accountId}")
    public Result<SysAccountBO> getAccountInfoByAccountId(@PathVariable("accountId") Long accountId) {
        SysAccountBO sysAccount = sysAccountService.getBOByAccountId(accountId);
        return Result.success(sysAccount);
    }

    /**
     * 功能描述:
     * 〈第三方账户登录绑定账户〉
     * @param accountBO accountBO
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "第三方账户登录绑定账户")
    @PostMapping("/api/account/bind/third")
    public void thirdBindAccount(@RequestBody SysAccountBO accountBO) {
        sysAccountService.thirdBindAccount(accountBO);
    }

}
