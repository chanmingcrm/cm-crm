package com.platform.mesh.app.biz.modules.app.modulesettrans.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.dto.AppModuleSetTransDTO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.app.biz.modules.app.modulesettrans.service.IAppModuleSetTransService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 模块转化设置信息
 * @author 蝉鸣
 */
@Tag(description = "AppModuleSetTransController", name = "模块转化设置")
@RestController
@RequestMapping
public class AppModuleSetTransController extends BaseController{
    @Autowired
    private IAppModuleSetTransService appModuleSetTransService;


    /**
     * 功能描述:
     * 〈获取当前模块转化字段映射设置信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<AppModuleSetTrans>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块转化字段映射设置信息")
    @PostMapping("/app/module/set/trans/page")
    public Result<PageVO<AppModuleSetTransVO>> getModuleSetTransPage(@RequestBody AppModuleRelPageDTO pageDTO) {
        PageVO<AppModuleSetTransVO> appModuleSetTrans = appModuleSetTransService.getModuleSetTransVOPage(pageDTO);
        return Result.success(appModuleSetTrans);
    }

    /**
     * 功能描述:
     * 〈获取模块转化字段映射设置〉
     * @param id id
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块转化字段映射设置")
    @GetMapping("/app/module/set/trans/info/{id}")
    public Result<AppModuleSetTransVO> getModuleSetTrans(@Validated @PathVariable("id") Long id) {
        return Result.success(BeanUtil.copyProperties(appModuleSetTransService.getById(id), AppModuleSetTransVO.class));
    }

    /**
     * 功能描述:
     * 〈新增模块转化字段映射设置〉
     * @param appModuleSetTransDTO appModuleSetTransDTO
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块转化字段映射设置")
    @PostMapping("/app/module/set/trans/add")
    public Result<Boolean> addModuleSetTrans(@Validated @RequestBody AppModuleSetTransDTO appModuleSetTransDTO) {
        return Result.success(appModuleSetTransService.addModuleSetTrans(appModuleSetTransDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块转化字段映射设置〉
     * @param appModuleSetTransDTO appModuleSetTransDTO
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块转化字段映射设置")
    @PostMapping("/app/module/set/trans/edit")
    public Result<Boolean> editModuleSetTrans(@Validated @RequestBody AppModuleSetTransDTO appModuleSetTransDTO) {
        return Result.success(appModuleSetTransService.editModuleSetTrans(appModuleSetTransDTO));
    }

    /**
     * 功能描述:
     * 〈删除模块转化字段映射设置〉
     * @param id id
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块转化字段映射设置")
    @PostMapping("/app/module/set/trans/delete/{id}")
    public Result<Boolean> addModuleSetTrans(@PathVariable("id") Long id) {
        return Result.success(appModuleSetTransService.delModuleSetTrans(id));
    }


}