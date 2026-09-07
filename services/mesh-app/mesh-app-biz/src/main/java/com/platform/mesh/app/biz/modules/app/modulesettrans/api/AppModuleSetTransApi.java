package com.platform.mesh.app.biz.modules.app.modulesettrans.api;

import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.service.IAppModuleSetTransService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
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
 * @description 模块转化设置信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "AppModuleSetTransApi", name = "模块转化设置信息")
@RestController
public class AppModuleSetTransApi extends BaseController{
    @Autowired
    private IAppModuleSetTransService appModuleSetTransService;

    /**
     * 功能描述:
     * 〈根据Id获取模块转化配置〉
     * @param  transId transId
     * @return 正常返回:{@link Result<AppModuleSetTransBO>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "根据Id获取模块转化配置")
    @GetMapping("/api/app/module/set/trans/by/{transId}")
    public Result<AppModuleSetTransBO> getModuleSetTransById(@PathVariable("transId") Long transId) {
        AppModuleSetTransBO transBO = appModuleSetTransService.getModuleSetTransById(transId);
        return Result.success(transBO);
    }

}