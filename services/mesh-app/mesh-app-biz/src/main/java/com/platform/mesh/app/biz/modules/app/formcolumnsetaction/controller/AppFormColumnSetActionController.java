package com.platform.mesh.app.biz.modules.app.formcolumnsetaction.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.dto.AppFormColumnSetActionDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.po.AppFormColumnSetAction;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.domain.vo.AppFormColumnSetActionVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetaction.service.IAppFormColumnSetActionService;
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
 * @description 单字段动作信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSetActionController", name = "单字段动作")
@RestController
@RequestMapping
public class AppFormColumnSetActionController extends BaseController{
    @Autowired
    private IAppFormColumnSetActionService appFormColumnSetActionService;

    /**
	 * 功能描述:
	 * 〈获取单字段动作列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormColumnSetActionVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取单字段动作分页")
	@PostMapping("/app/form/column/set/action/page")
	public Result<PageVO<AppFormColumnSetActionVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppFormColumnSetAction> formColumnSetActionMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnSetAction.class);
        MPage<AppFormColumnSetAction> page = appFormColumnSetActionService.page(formColumnSetActionMPage);
        PageVO<AppFormColumnSetActionVO> voPage = MPageUtil.convertToVO(page, AppFormColumnSetActionVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前单字段动作信息〉
     * @param formColumnSetActionId formColumnSetActionId
     * @return 正常返回:{@link Result<AppFormColumnSetActionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段动作信息")
    @GetMapping("/app/form/column/set/action/info/{formColumnSetActionId}")
    public Result<AppFormColumnSetActionVO> getFormColumnSetActionInfoById(@PathVariable("formColumnSetActionId")Long formColumnSetActionId) {
        AppFormColumnSetActionVO appFormColumnSetActionVO = appFormColumnSetActionService.getFormColumnSetActionInfoById(formColumnSetActionId);
        return Result.success(appFormColumnSetActionVO);
    }

    /**
     * 功能描述:
     * 〈新增单字段动作〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link Result<AppFormColumnSetActionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段动作")
    @Log(moduleName = "单字段动作管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/set/action/add")
    public Result<AppFormColumnSetActionVO> addFormColumnSetAction(@Validated @RequestBody AppFormColumnSetActionDTO formColumnSetActionDTO) {
        return Result.success(appFormColumnSetActionService.addFormColumnSetAction(formColumnSetActionDTO));
    }

    /**
     * 功能描述:
     * 〈修改单字段动作〉
     * @param formColumnSetActionDTO formColumnSetActionDTO
     * @return 正常返回:{@link Result<AppFormColumnSetActionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改单字段动作")
    @Log(moduleName = "单字段动作管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/column/set/action/edit")
    public Result<AppFormColumnSetActionVO> editFormColumnSetAction(@Validated @RequestBody AppFormColumnSetActionDTO formColumnSetActionDTO) {
        return Result.success(appFormColumnSetActionService.editFormColumnSetAction(formColumnSetActionDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除单字段动作〉
     * @param formColumnSetActionId formColumnSetActionId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段动作")
    @Log(moduleName = "单字段动作管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/set/action/delete/{formColumnSetActionId}")
    public Result<Boolean> deleteFormColumnSetAction(@PathVariable(value = "formColumnSetActionId",required = false)Long formColumnSetActionId) {
        return Result.success(appFormColumnSetActionService.deleteFormColumnSetAction(formColumnSetActionId));
    }

}