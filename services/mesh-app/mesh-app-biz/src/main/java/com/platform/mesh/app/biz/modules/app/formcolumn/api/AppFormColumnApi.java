package com.platform.mesh.app.biz.modules.app.formcolumn.api;

import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.bo.SyncDataBO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
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
 * @description 表单字段关联信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "AppFormColumnApi", name = "表单字段关联")
@RestController
public class AppFormColumnApi extends BaseController{
    @Autowired
    private IAppFormColumnService appFormColumnService;

    /**
     * 功能描述:
     * 〈获取当前表单字段关联信息〉
     * @param formId formId
     * @return 正常返回:{@link Result<List<AppFormColumnBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "获取当前表单字段平铺结构关联信息")
    @GetMapping("/api/app/form/column/list")
    public Result<List<AppFormColumnBO>> getFormColumnList(@RequestParam("moduleId")Long moduleId
            , @RequestParam("formId")Long formId) {
        List<AppFormColumnBO> appFormColumnBOS = appFormColumnService.getFormColumnBOList(moduleId,formId);
        return Result.success(appFormColumnBOS);
    }

    /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link Result<List<AppFormColumnBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
    @PostMapping("/api/app/form/column/fast/form/type")
    public Result<List<AppFormColumnBO>> fastColumnByModuleAndFormType(@RequestParam("moduleId")Long moduleId
            , @RequestParam("formType")Integer formType) {
        return Result.success(appFormColumnService.fastColumnBOByModuleAndFormType(moduleId,formType));
    }

    /**
     * 功能描述:
     * 〈获取当前表单字段关联信息〉
     * @param formId formId
     * @return 正常返回:{@link Result<List<AppFormColumnVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前表单字段树结构关联信息")
    @GetMapping("/api/app/form/column/tree/{moduleId}/{formId}")
    public Result<List<AppFormColumnVO>> getFormColumnTree(@PathVariable("moduleId")Long moduleId
            ,@PathVariable("formId")Long formId) {
        List<AppFormColumnVO> appFormColumnVOs = appFormColumnService.getFormColumnTree(moduleId,formId);
        return Result.success(appFormColumnVOs);
    }

    /**
     * 功能描述:
     * 〈获取同步信息使用关联模块以及字段信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link Result<List<SyncDataBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @GetMapping("/api/app/form/column/rel/module")
    public Result<List<SyncDataBO>> getRelModuleToSync(@RequestParam("moduleId")Long moduleId) {
        List<SyncDataBO> syncDataBOS = appFormColumnService.getRelModuleToSync(moduleId);
        return Result.success(syncDataBOS);
    }

}
