package com.platform.mesh.upms.biz.modules.log.login.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.upms.biz.modules.log.login.domain.po.LogLogin;
import com.platform.mesh.upms.biz.modules.log.login.service.ILogLoginService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 登录日志信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "LogLoginApi", name = "登录日志信息")
@RestController
public class LogLoginApi extends BaseController{
    /**
     * 服务对象
     */
    @Autowired
    private ILogLoginService logLoginService;

    /**
     * 功能描述:
     * 〈新增登录日志〉
     * @param logLoginBO sysLoginInfo
     * @return 正常返回:{@link Result<Void>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "新增登录日志")
    @PostMapping(value ="/api/log/login/add", headers = HttpConst.HEADER_FROM_IN)
    public Result<Void> addLoginLog(@Validated @RequestBody LogLoginBO logLoginBO) {
        LogLogin logLogin = BeanUtil.copyProperties(logLoginBO, LogLogin.class);
        this.logLoginService.save(logLogin);
        return Result.success();
    }


}
