package com.platform.mesh.app.biz.modules.app.modulesettranspick.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.domain.dto.AppModuleSetTransPickDTO;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.domain.po.AppModuleSetTransPick;
import com.platform.mesh.app.biz.modules.app.modulesettranspick.service.IAppModuleSetTransPickService;
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
public class AppModuleSetTransPickController extends BaseController{
    @Autowired
    private IAppModuleSetTransPickService appModuleSetPickService;


    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Result<List<AppModuleSetTransPick>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分配设置信息")
    @GetMapping("/app/module/set/pick/info/{moduleId}")
    public Result<List<AppModuleSetTransPick>> getModuleSetPickByModuleId(@PathVariable("moduleId")Long moduleId) {
        List<AppModuleSetTransPick> appModuleSetTransPicks = appModuleSetPickService.getModuleSetPickByModuleId(moduleId);
        return Result.success(appModuleSetTransPicks);
    }

    /**
     * 功能描述:
     * 〈获取当前模块分配设置信息〉
     * @param transId transId
     * @return 正常返回:{@link Result<List<AppModuleSetTransPick>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块分配设置信息")
    @GetMapping("/app/module/set/pick/by/trans/{transId}")
    public Result<List<AppModuleSetTransPick>> getModuleSetTransPickByTransId(@PathVariable("transId") Long transId) {
        List<AppModuleSetTransPick> appModuleSetTransPicks = appModuleSetPickService.getModuleSetTransPickByTransId(transId);
        return Result.success(appModuleSetTransPicks);
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
    public Result<Boolean> addModuleSetPick(@Validated @RequestBody List<AppModuleSetTransPickDTO> appModuleSetTransDTOS) {
        return Result.success(appModuleSetPickService.addModuleSetPick(appModuleSetTransDTOS));
    }


}