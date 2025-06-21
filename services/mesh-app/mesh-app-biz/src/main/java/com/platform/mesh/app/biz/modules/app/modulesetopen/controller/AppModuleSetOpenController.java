package com.platform.mesh.app.biz.modules.app.modulesetopen.controller;

import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.dto.AppModuleSetOpenDTO;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.po.AppModuleSetOpen;
import com.platform.mesh.app.biz.modules.app.modulesetopen.domain.vo.AppModuleSetOpenVO;
import com.platform.mesh.app.biz.modules.app.modulesetopen.service.IAppModuleSetOpenService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 模块开放设置信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnMappingController", name = "模块开放设置")
@RestController
@RequestMapping
public class AppModuleSetOpenController extends BaseController{
    @Autowired
    private IAppModuleSetOpenService appModuleSetOpenService;

    /**
     * 功能描述:
     * 〈获取当前模块转化字段映射设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Result<AppModuleSetOpen>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块转化字段映射设置信息")
    @GetMapping("/app/module/set/open/info/{moduleId}")
    public Result<AppModuleSetOpen> getModuleSetOpenByModuleId(@PathVariable("moduleId")Long moduleId) {
        AppModuleSetOpen appModuleSetTrans = appModuleSetOpenService.getModuleSetOpenByModuleId(moduleId);
        return Result.success(appModuleSetTrans);
    }

    /**
     * 功能描述:
     * 〈新增模块转化字段映射设置〉
     * @param appModuleSetTransDTO appModuleSetTransDTO
     * @return 正常返回:{@link Result<AppModuleSetOpenVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块转化字段映射设置")
    @PostMapping("/app/module/set/open/add")
    public Result<AppModuleSetOpenVO> addModuleSetOpen(@Validated @RequestBody AppModuleSetOpenDTO appModuleSetTransDTO) {
        return Result.success(appModuleSetOpenService.addModuleSetOpen(appModuleSetTransDTO));
    }

}