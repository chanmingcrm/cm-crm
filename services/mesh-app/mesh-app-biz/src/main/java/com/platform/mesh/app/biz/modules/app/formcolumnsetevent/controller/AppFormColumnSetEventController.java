package com.platform.mesh.app.biz.modules.app.formcolumnsetevent.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.dto.AppFormColumnSetEventDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.po.AppFormColumnSetEvent;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.domain.vo.AppFormColumnSetEventVO;
import com.platform.mesh.app.biz.modules.app.formcolumnsetevent.service.IAppFormColumnSetEventService;
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
 * @description 单字段事件信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSetEventController", name = "单字段事件")
@RestController
@RequestMapping
public class AppFormColumnSetEventController extends BaseController{
    @Autowired
    private IAppFormColumnSetEventService appFormColumnSetEventService;

    /**
	 * 功能描述:
	 * 〈获取单字段事件列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormColumnSetEventVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取单字段事件分页")
	@PostMapping("/app/form/column/set/event/page")
	public Result<PageVO<AppFormColumnSetEventVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppFormColumnSetEvent> formColumnSetEventMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnSetEvent.class);
        MPage<AppFormColumnSetEvent> page = appFormColumnSetEventService.page(formColumnSetEventMPage);
        PageVO<AppFormColumnSetEventVO> voPage = MPageUtil.convertToVO(page, AppFormColumnSetEventVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前单字段事件信息〉
     * @param formColumnSetEventId formColumnSetEventId
     * @return 正常返回:{@link Result<AppFormColumnSetEventVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段事件信息")
    @GetMapping("/app/form/column/set/event/info/{formColumnSetEventId}")
    public Result<AppFormColumnSetEventVO> getFormColumnSetEventInfoById(@PathVariable("formColumnSetEventId")Long formColumnSetEventId) {
        AppFormColumnSetEventVO appFormColumnSetEventVO = appFormColumnSetEventService.getFormColumnSetEventInfoById(formColumnSetEventId);
        return Result.success(appFormColumnSetEventVO);
    }

    /**
     * 功能描述:
     * 〈新增单字段事件〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link Result<AppFormColumnSetEventVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段事件")
    @Log(moduleName = "单字段事件管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/set/event/add")
    public Result<AppFormColumnSetEventVO> addFormColumnSetEvent(@Validated @RequestBody AppFormColumnSetEventDTO formColumnSetEventDTO) {
        return Result.success(appFormColumnSetEventService.addFormColumnSetEvent(formColumnSetEventDTO));
    }

    /**
     * 功能描述:
     * 〈修改单字段事件〉
     * @param formColumnSetEventDTO formColumnSetEventDTO
     * @return 正常返回:{@link Result<AppFormColumnSetEventVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改单字段事件")
    @Log(moduleName = "单字段事件管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/column/set/event/edit")
    public Result<AppFormColumnSetEventVO> editFormColumnSetEvent(@Validated @RequestBody AppFormColumnSetEventDTO formColumnSetEventDTO) {
        return Result.success(appFormColumnSetEventService.editFormColumnSetEvent(formColumnSetEventDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除单字段事件〉
     * @param formColumnSetEventId formColumnSetEventId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段事件")
    @Log(moduleName = "单字段事件管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/set/event/delete/{formColumnSetEventId}")
    public Result<Boolean> deleteFormColumnSetEvent(@PathVariable(value = "formColumnSetEventId",required = false)Long formColumnSetEventId) {
        return Result.success(appFormColumnSetEventService.deleteFormColumnSetEvent(formColumnSetEventId));
    }

}