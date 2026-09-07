package com.platform.mesh.app.biz.modules.app.modulesettransauto.api;

import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.service.IAppModuleSetTransAutoService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 模块转化自动化设置信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "AppModuleSetTransAutoApi", name = "模块转化自动化设置信息")
@RestController
public class AppModuleSetTransAutoApi extends BaseController{

    @Autowired
    private IAppModuleSetTransAutoService appModuleSetTransAutoService;

    /**
     * 功能描述:
     * 〈获取模块转移分页〉
     * @param  pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<AppModuleSetTransBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "获取模块转移分页")
    @PostMapping("/api/app/module/set/trans/auto/page")
    public Result<PageVO<AppModuleSetTransBO>> selectPage(@RequestBody ModulePageDTO pageDTO) {
        PageVO<AppModuleSetTransBO> boPage = appModuleSetTransAutoService.getModuleSetTransAutoBOPage(pageDTO);
        return Result.success(boPage);
    }

}