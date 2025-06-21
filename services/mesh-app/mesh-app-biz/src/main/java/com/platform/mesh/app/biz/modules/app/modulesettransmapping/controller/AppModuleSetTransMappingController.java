package com.platform.mesh.app.biz.modules.app.modulesettransmapping.controller;

import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.dto.AppModuleSetTransMappingDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.domain.po.AppModuleSetTransMapping;
import com.platform.mesh.app.biz.modules.app.modulesettransmapping.service.IAppModuleSetTransMappingService;
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
 * @description 模块转化字段映射设置信息
 * @author 蝉鸣
 */
@Tag(description = "AppModuleSetTransMappingController", name = "模块转化字段映射设置")
@RestController
@RequestMapping
public class AppModuleSetTransMappingController extends BaseController{
    @Autowired
    private IAppModuleSetTransMappingService appModuleSetTransMappingService;

    /**
     * 功能描述:
     * 〈获取当前模块转化字段映射设置信息〉
     * @param formModuleId formModuleId
     * @return 正常返回:{@link Result<List<AppModuleSetTransMapping>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块转化字段映射设置信息")
    @GetMapping("/app/module/set/trans/mapping/info/{formModuleId}/{toModuleId}")
    public Result<List<AppModuleSetTransMapping>> getModuleSetTransMappingByModuleId(@PathVariable("formModuleId")Long formModuleId,@PathVariable("toModuleId")Long toModuleId) {
        List<AppModuleSetTransMapping> mappingByModules = appModuleSetTransMappingService.getModuleSetTransMappingByModuleId(formModuleId, toModuleId);
        return Result.success(mappingByModules);
    }

    /**
     * 功能描述:
     * 〈新增模块转化字段映射设置〉
     * @param moduleSetTransMappingDTOS moduleSetTransMappingDTOS
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块转化字段映射设置")
    @PostMapping("/app/module/set/trans/mapping/add")
    public Result<Boolean> addModuleSetTransMapping(@Validated @RequestBody List<AppModuleSetTransMappingDTO> moduleSetTransMappingDTOS) {
        return Result.success(appModuleSetTransMappingService.addModuleSetTransMapping(moduleSetTransMappingDTOS));
    }


}