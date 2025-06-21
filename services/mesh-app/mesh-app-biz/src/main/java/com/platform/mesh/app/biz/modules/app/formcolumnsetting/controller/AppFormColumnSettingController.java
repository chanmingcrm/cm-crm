package com.platform.mesh.app.biz.modules.app.formcolumnsetting.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.dto.AppFormColumnSettingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.po.AppFormColumnSetting;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetting.service.IAppFormColumnSettingService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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
 * @description 单字段配置信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSettingController", name = "单字段配置")
@RestController
@RequestMapping
public class AppFormColumnSettingController extends BaseController{
    @Autowired
    private IAppFormColumnSettingService appFormColumnSettingService;

    /**
	 * 功能描述:
	 * 〈获取单字段配置列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormColumnSettingVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取单字段配置分页")
	@PostMapping("/app/form/column/setting/page")
	public Result<PageVO<AppFormColumnSettingVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppFormColumnSetting> formColumnMappingMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnSetting.class);
        MPage<AppFormColumnSetting> page = appFormColumnSettingService.page(formColumnMappingMPage);
        PageVO<AppFormColumnSettingVO> voPage = MPageUtil.convertToVO(page, AppFormColumnSettingVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前单字段配置信息〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Result<AppFormColumnSettingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段配置信息")
    @GetMapping("/app/form/column/setting/info/{formColumnMappingId}")
    public Result<AppFormColumnSettingVO> getFormColumnMappingInfoById(@PathVariable("formColumnMappingId")Long formColumnMappingId) {
        AppFormColumnSettingVO AppFormColumnSettingVO = appFormColumnSettingService.getFormColumnMappingInfoById(formColumnMappingId);
        return Result.success(AppFormColumnSettingVO);
    }

    /**
     * 功能描述:
     * 〈新增单字段配置〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link Result<AppFormColumnSettingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段配置")
    @Log(moduleName = "单字段配置管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/setting/add")
    public Result<AppFormColumnSettingVO> addFormColumnMapping(@Validated @RequestBody AppFormColumnSettingDTO formColumnMappingDTO) {
        return Result.success(appFormColumnSettingService.addFormColumnMapping(formColumnMappingDTO));
    }

    /**
     * 功能描述:
     * 〈修改单字段配置〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link Result<AppFormColumnSettingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改单字段配置")
    @Log(moduleName = "单字段配置管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/column/setting/edit")
    public Result<AppFormColumnSettingVO> editFormColumnMapping(@Validated @RequestBody AppFormColumnSettingDTO formColumnMappingDTO) {
        return Result.success(appFormColumnSettingService.editFormColumnMapping(formColumnMappingDTO));
    }

   /**
     * 功能描述:
     * 〈删除单字段配置〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段配置")
    @Log(moduleName = "单字段配置管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/setting/delete/{formColumnMappingId}")
    public Result<Boolean> deleteFormColumnMapping(@PathVariable(value = "formColumnMappingId",required = false)Long formColumnMappingId) {
        return Result.success(appFormColumnSettingService.deleteFormColumnMapping(formColumnMappingId));
    }

}