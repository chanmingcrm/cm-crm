package com.platform.mesh.app.biz.modules.app.formbase.controller;

import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBaseDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.dto.AppFormBasePageDTO;
import com.platform.mesh.app.biz.modules.app.formbase.domain.po.AppFormBase;
import com.platform.mesh.app.biz.modules.app.formbase.domain.vo.AppFormBaseVO;
import com.platform.mesh.app.biz.modules.app.formbase.service.IAppFormBaseService;
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
 * @description 单信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormBaseController", name = "表单管理")
@RestController
@RequestMapping
public class AppFormBaseController extends BaseController{

    @Autowired
    private IAppFormBaseService appFormBaseService;

    /**
	 * 功能描述:
	 * 〈获取单列表〉
	 * @param appFormBasePageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取表单分页")
	@PostMapping("/app/form/base/page")
	public Result<PageVO<AppFormBaseVO>> selectPage(@RequestBody AppFormBasePageDTO appFormBasePageDTO) {
        MPage<AppFormBase> page = appFormBaseService.selectPage(appFormBasePageDTO);
        PageVO<AppFormBaseVO> voPage = MPageUtil.convertToVO(page, AppFormBaseVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前单信息〉
     * @param formBaseId formBaseId
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前表单信息")
    @GetMapping("/app/form/base/info/{formBaseId}")
    public Result<AppFormBaseVO> getFormBaseInfoById(@PathVariable("formBaseId")Long formBaseId) {
        AppFormBaseVO appFormBaseVO = appFormBaseService.getFormBaseInfoById(formBaseId);
        return Result.success(appFormBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增单〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增表单")
    @Log(moduleName = "表单管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/base/add")
    public Result<AppFormBaseVO> addFormBase(@Validated @RequestBody AppFormBaseDTO formBaseDTO) {
        return Result.success(appFormBaseService.addFormBase(formBaseDTO));
    }

    /**
     * 功能描述:
     * 〈修改单〉
     * @param formBaseDTO formBaseDTO
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改表单")
    @Log(moduleName = "表单管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/base/edit")
    public Result<AppFormBaseVO> editFormBase(@Validated @RequestBody AppFormBaseDTO formBaseDTO) {
        return Result.success(appFormBaseService.editFormBase(formBaseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除单〉
     * @param formBaseId formBaseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除表单")
    @Log(moduleName = "表单管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/base/delete/{formBaseId}")
    public Result<Boolean> deleteFormBase(@PathVariable(value = "formBaseId",required = false)Long formBaseId) {
        return Result.success(appFormBaseService.deleteFormBase(formBaseId));
    }

    /**
     * 功能描述:
     * 〈获取当前默认类型表单信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link Result<AppFormBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前默认类型表单信息")
    @GetMapping("/app/form/base/default/{moduleId}/{formType}")
    public Result<AppFormBaseVO> getFormBaseDefaultByModuleId(@PathVariable("moduleId")Long moduleId,@PathVariable("formType")Integer formType) {
        AppFormBaseVO appFormBaseVO = appFormBaseService.getFormBaseDefaultByModuleId(moduleId,formType);
        return Result.success(appFormBaseVO);
    }
}