package com.platform.mesh.app.biz.modules.app.modulebase.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 模块信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "AppModuleBaseApi", name = "模块")
@RestController
public class AppModuleBaseApi extends BaseController{

    @Autowired
    private IAppModuleBaseService appModuleBaseService;

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Result<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "获取当前模块信息")
    @GetMapping("/api/app/module/base/info/{moduleBaseId}")
    public Result<AppModuleBaseVO> getModuleBaseInfoById(@PathVariable("moduleBaseId")Long moduleBaseId) {
        AppModuleBaseVO appModuleBaseVO = appModuleBaseService.getModuleBaseInfoById(moduleBaseId);
        return Result.success(appModuleBaseVO);
    }

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseIds moduleBaseIds
     * @return 正常返回:{@link Result<List<AppModuleBaseVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块信息")
    @PostMapping("/api/app/module/base/info")
    public Result<List<AppModuleBaseVO>> getModuleBaseInfoByIds(@RequestBody List<Long> moduleBaseIds) {
        List<AppModuleBaseVO> appModuleBaseVOs = appModuleBaseService.getModuleBaseInfoByIds(moduleBaseIds);
        return Result.success(appModuleBaseVOs);
    }

    /**
     * 功能描述:
     * 〈根据表单名称获取模块信息〉
     * @param appTables appTables
     * @return 正常返回:{@link Result<List<AppModuleBaseBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "通过表名获取模块信息")
    @PostMapping(value="/api/app/module/base/info/by/schema")
    public Result<List<AppModuleBaseBO>> getModuleBaseInfoBySchema(@RequestBody List<String> appTables){
        List<AppModuleBase> appModuleBases= appModuleBaseService.getModuleBaseInfoBySchema(appTables);
        return Result.success(BeanUtil.copyToList(appModuleBases, AppModuleBaseBO.class));
    }

    /**
     * 功能描述:
     * 〈获取当前模块所有子信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Result<List<AppModuleBaseVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块所有子信息")
    @GetMapping("/api/app/module/base/child/{moduleBaseId}")
    public Result<List<AppModuleBaseVO>> getModuleBaseChildById(@PathVariable("moduleBaseId")Long moduleBaseId) {
        List<AppModuleBase> appModuleBases = appModuleBaseService.getModuleBaseChildById(moduleBaseId);
        return Result.success(BeanUtil.copyToList(appModuleBases, AppModuleBaseVO.class));
    }

    /**
     * 功能描述:
     * 〈初始化模块ES信息〉
     * @param tableSchema tableSchema
     * @return 正常返回:{@link Result<List<AppModuleBaseVO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "初始化模块ES信息")
    @GetMapping("/api/app/module/base/init/es")
    public Result<List<AppModuleBaseBO>> initModuleBaseEs(@RequestParam("tableSchema") String tableSchema) {
        List<AppModuleBase> moduleBases = appModuleBaseService.initModuleBaseEs(tableSchema);
        return Result.success(BeanUtil.copyToList(moduleBases, AppModuleBaseBO.class));
    }

}