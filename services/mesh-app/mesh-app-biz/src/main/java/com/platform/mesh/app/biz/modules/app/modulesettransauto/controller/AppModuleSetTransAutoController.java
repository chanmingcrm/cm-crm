package com.platform.mesh.app.biz.modules.app.modulesettransauto.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.po.AppModuleSetTrans;
import com.platform.mesh.app.biz.modules.app.modulesettrans.domain.vo.AppModuleSetTransVO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.dto.AppModuleSetTransAutoPageDTO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.domain.vo.AppModuleSetTransAutoVO;
import com.platform.mesh.app.biz.modules.app.modulesettransauto.service.IAppModuleSetTransAutoService;
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
 * @description 模块转化自动化设置
 * @author 蝉鸣
 */
@Tag(description = "AppModuleSetTransAutoController", name = "模块转化自动化设置")
@RestController
@RequestMapping
public class AppModuleSetTransAutoController extends BaseController{

    @Autowired
    private IAppModuleSetTransAutoService appModuleSetTransAutoService;


    /**
     * 功能描述:
     * 〈获取当前模块转化字段映射自动化设置信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<AppModuleSetTrans>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块转化字段映射自动化设置信息")
    @PostMapping("/app/module/set/trans/auto/page")
    public Result<PageVO<AppModuleSetTransAutoVO>> getModuleSetTransAutoPage(@RequestBody AppModuleSetTransAutoPageDTO pageDTO) {
        PageVO<AppModuleSetTransAutoVO> appModuleSetTrans = appModuleSetTransAutoService.getModuleSetTransAutoVOPage(pageDTO);
        return Result.success(appModuleSetTrans);
    }

    /**
     * 功能描述:
     * 〈获取模块转化字段映射自动化设置〉
     * @param id id
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块转化字段映射自动化设置")
    @GetMapping("/app/module/set/trans/auto/info/{id}")
    public Result<AppModuleSetTransAutoVO> getModuleSetTransAuto(@Validated @PathVariable("id") Long id) {
        return Result.success(BeanUtil.copyProperties(appModuleSetTransAutoService.getById(id), AppModuleSetTransAutoVO.class));
    }

    /**
     * 功能描述:
     * 〈新增模块转化字段映射自动化设置〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块转化字段映射自动化设置")
    @PostMapping("/app/module/set/trans/auto/add")
    public Result<Boolean> addModuleSetTransAuto(@Validated @RequestBody AppModuleSetTransAutoDTO addDTO) {
        return Result.success(appModuleSetTransAutoService.addModuleSetTransAuto(addDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块转化字段映射自动化设置〉
     * @param editDTO editDTO
     * @return 正常返回:{@link Result<AppModuleSetTransVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块转化字段映射自动化设置")
    @PostMapping("/app/module/set/trans/auto/edit")
    public Result<Boolean> editModuleSetTransAuto(@Validated @RequestBody AppModuleSetTransAutoDTO editDTO) {
        return Result.success(appModuleSetTransAutoService.editModuleSetTransAuto(editDTO));
    }

    /**
     * 功能描述:
     * 〈删除模块转化字段映射自动化设置〉
     * @param id id
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块转化字段映射自动化设置")
    @PostMapping("/app/module/set/trans/auto/delete/{id}")
    public Result<Boolean> addModuleSetTransAuto(@PathVariable("id") Long id) {
        return Result.success(appModuleSetTransAutoService.removeById(id));
    }


}