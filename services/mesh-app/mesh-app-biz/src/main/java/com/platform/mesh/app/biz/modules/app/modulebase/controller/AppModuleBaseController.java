package com.platform.mesh.app.biz.modules.app.modulebase.controller;

import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseCopyDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBaseDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleBasePageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.dto.AppModuleRelPageDTO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.po.AppModuleBase;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleBaseVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleFastPageVO;
import com.platform.mesh.app.biz.modules.app.modulebase.domain.vo.AppModuleRelDictVO;
import com.platform.mesh.app.biz.modules.app.modulebase.service.IAppModuleBaseService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 模块信息
 * @author 蝉鸣
 */
@Tag(description = "AppModuleBaseController", name = "模块")
@RestController
@RequestMapping
public class AppModuleBaseController extends BaseController{

    @Autowired
    private IAppModuleBaseService appModuleBaseService;

    /**
	 * 功能描述:
	 * 〈获取模块列表〉
	 * @param  appModuleBasePageDTO appModuleBasePageDTO
	 * @return 正常返回:{@link Result<MPage<AppModuleBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取模块分页")
	@PostMapping("/app/module/base/page")
	public Result<PageVO<AppModuleBaseVO>> selectPage(@RequestBody AppModuleBasePageDTO appModuleBasePageDTO) {
        MPage<AppModuleBase> page = appModuleBaseService.selectPage(appModuleBasePageDTO);
        PageVO<AppModuleBaseVO> voPage = MPageUtil.convertToVO(page, AppModuleBaseVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前模块信息〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Result<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前模块信息")
    @GetMapping("/app/module/base/info/{moduleBaseId}")
    public Result<AppModuleBaseVO> getModuleBaseInfoById(@PathVariable("moduleBaseId")Long moduleBaseId) {
        AppModuleBaseVO appModuleBaseVO = appModuleBaseService.getModuleBaseInfoById(moduleBaseId);
        return Result.success(appModuleBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增模块〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link Result<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增模块")
    @Log(moduleName = "模块管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/module/base/add")
    public Result<AppModuleBaseVO> addModuleBase(@Validated @RequestBody AppModuleBaseDTO moduleBaseDTO) {
        return Result.success(appModuleBaseService.addModuleBase(moduleBaseDTO));
    }

    /**
     * 功能描述:
     * 〈修改模块〉
     * @param moduleBaseDTO moduleBaseDTO
     * @return 正常返回:{@link Result<AppModuleBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改模块")
    @Log(moduleName = "模块管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/module/base/edit")
    public Result<AppModuleBaseVO> editModuleBase(@Validated @RequestBody AppModuleBaseDTO moduleBaseDTO) {
        return Result.success(appModuleBaseService.editModuleBase(moduleBaseDTO));
    }

   /**
     * 功能描述:
     * 〈删除模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除模块")
    @Log(moduleName = "模块管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/module/base/delete/{moduleBaseId}")
    public Result<Boolean> deleteModuleBase(@PathVariable(value = "moduleBaseId",required = false)Long moduleBaseId) {
        return Result.success(appModuleBaseService.deleteModuleBase(moduleBaseId));
    }

    /**
     * 功能描述:
     * 〈发布模块〉
     * @param moduleBaseId moduleBaseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "发布模块")
    @PostMapping("/app/module/base/public/{moduleBaseId}")
    public Result<Boolean> publicModuleBase(@PathVariable(value = "moduleBaseId",required = false)Long moduleBaseId) {
        return Result.success(appModuleBaseService.publicModuleBase(moduleBaseId));
    }

    /**
     * 功能描述:
     * 〈拷贝模块〉
     * @param copyDTO copyDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "拷贝模块")
    @PostMapping("/app/module/base/copy")
    public Result<Boolean> copyModuleBase(@RequestBody AppModuleBaseCopyDTO copyDTO) {
        return Result.success(appModuleBaseService.copyModuleBase(copyDTO));
    }

    /**
     * 功能描述:
     * 〈获取模块默认列表配置信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Result<AppModuleFastPageVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块默认列表配置信息")
    @PostMapping("/app/module/base/fast/page/{moduleId}")
    public Result<AppModuleFastPageVO> fastPageFormBase(@PathVariable(value = "moduleId")Long moduleId) {
        return Result.success(appModuleBaseService.fastPageFormBase(moduleId));
    }

    /**
     * 功能描述:
     * 〈获取模块关联字典分页〉
     * @param appModuleRelPageDTO appModuleRelPageDTO
     * @return 正常返回:{@link Result<MPage<AppFormColumnVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模块关联字典分页")
    @PostMapping("/app/module/base/rel/dict")
    public Result<PageVO<AppModuleRelDictVO>> selectRelDictPage(@RequestBody AppModuleRelPageDTO appModuleRelPageDTO) {
        MPage<AppModuleRelDictVO> dictPage = appModuleBaseService.selectRelDictPage(appModuleRelPageDTO);
        PageVO<AppModuleRelDictVO> voPage = MPageUtil.convertToVO(dictPage, AppModuleRelDictVO.class);
        return Result.success(voPage);
    }

}