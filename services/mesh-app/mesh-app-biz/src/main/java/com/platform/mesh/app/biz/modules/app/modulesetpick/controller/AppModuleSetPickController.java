package com.platform.mesh.app.biz.modules.app.modulesetpick.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.dto.AppModuleSetPickDTO;
import com.platform.mesh.app.biz.modules.app.modulesetpick.domain.po.AppModuleSetPick;
import com.platform.mesh.app.biz.modules.app.modulesetpick.service.IAppModuleSetPickService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 模块分配设置信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnMappingController", name = "模块分配设置")
@RestController
@RequestMapping
public class AppModuleSetPickController extends BaseController{
    @Autowired
    private IAppModuleSetPickService appModuleSetPickService;


    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Result<AppModuleSetPick>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分配设置信息")
    @GetMapping("/app/module/set/pick/info/{moduleId}")
    public Result<List<AppModuleSetPick>> getModuleSetPickByModuleId(@PathVariable("moduleId")Long moduleId) {
        List<AppModuleSetPick> appModuleSetPicks = appModuleSetPickService.getModuleSetPickByModuleId(moduleId);
        return Result.success(BeanUtil.copyToList(appModuleSetPicks, AppModuleSetPick.class));
    }

    /**
     * 功能描述:
     * 〈新增模块分配设置〉
     * @param appModuleSetTransDTOS appModuleSetTransDTOS
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块分配设置")
    @PostMapping("/app/module/set/pick/add")
    public Result<Boolean> addModuleSetPick(@Validated @RequestBody List<AppModuleSetPickDTO> appModuleSetTransDTOS) {
        return Result.success(appModuleSetPickService.addModuleSetPick(appModuleSetTransDTOS));
    }


}