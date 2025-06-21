package com.platform.mesh.app.biz.modules.app.modulesettrans.api;

import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleSetTransBO;
import com.platform.mesh.app.api.modules.app.domain.dto.ModulePageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.service.IAppModuleSetTransService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
     * 〈获取模块转移分页〉
     * @param  pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<AppModuleSetTransBO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块转移分页")
    @PostMapping("/api/app/module/set/trans/page")
    public Result<PageVO<AppModuleSetTransBO>> selectPage(@RequestBody ModulePageDTO pageDTO) {
        PageVO<AppModuleSetTransBO> boPage = appModuleSetTransService.getModuleSetTransBOPage(pageDTO);
        return Result.success(boPage);
    }

    /**
     * 功能描述:
     * 〈根据Id获取模块转化配置〉
     * @param  transId transId
     * @return 正常返回:{@link Result<AppModuleSetTransBO>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据Id获取模块转化配置")
    @GetMapping("/api/app/module/set/trans/by/{transId}")
    public Result<AppModuleSetTransBO> getModuleSetTransById(@PathVariable("transId") Long transId) {
        AppModuleSetTransBO transBO = appModuleSetTransService.getModuleSetTransById(transId);
        return Result.success(transBO);
    }

}