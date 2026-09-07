package com.platform.mesh.app.biz.modules.app.formcolumnsorting.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnsetting.domain.vo.AppFormColumnSettingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.dto.AppFormColumnSortingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.domain.vo.AppFormColumnSortingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsorting.service.IAppFormColumnSortingService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 单字段排序信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSortingController", name = "表单字段排序")
@RestController
@RequestMapping
public class AppFormColumnSortingController extends BaseController{

    @Autowired
    private IAppFormColumnSortingService appFormColumnSortingService;

    /**
     * 功能描述:
     * 〈获取当前单字段排序信息〉
     * @param formId formId
     * @return 正常返回:{@link Result<List<AppFormColumnSortingVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段排序信息")
    @GetMapping("/app/form/column/sorting/info/{formId}")
    public Result<List<AppFormColumnSortingVO>> getFormColumnSortingInfoById(@PathVariable("formId")Long formId) {
        List<AppFormColumnSortingVO> appFormColumnSortingVOS = appFormColumnSortingService.getFormColumnSortingInfoByFormId(formId);
        return Result.success(appFormColumnSortingVOS);
    }

    /**
     * 功能描述:
     * 〈新增单字段排序〉
     * @param formColumnSortingDTOS formColumnSortingDTOS
     * @return 正常返回:{@link Result<AppFormColumnSettingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段排序")
    @Log(moduleName = "表单字段排序管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/sorting/add")
    public Result<Boolean> addFormColumnSorting(@Validated @RequestBody List<AppFormColumnSortingDTO> formColumnSortingDTOS) {
        return Result.success(appFormColumnSortingService.addFormColumnSorting(formColumnSortingDTOS));
    }


   /**
     * 功能描述:
     * 〈删除单字段排序〉
     * @param formId formId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段排序")
    @Log(moduleName = "表单字段排序管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/sorting/delete/{formId}")
    public Result<Boolean> deleteFormColumnSorting(@PathVariable(value = "formId") Long formId) {
        return Result.success(appFormColumnSortingService.deleteFormColumnSorting(formId));
    }

}