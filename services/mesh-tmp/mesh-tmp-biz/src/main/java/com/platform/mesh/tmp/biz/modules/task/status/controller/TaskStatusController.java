package com.platform.mesh.tmp.biz.modules.task.status.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.status.domain.po.TaskStatus;
import com.platform.mesh.tmp.biz.modules.task.status.service.ITaskStatusService;
import com.platform.mesh.tmp.biz.modules.task.status.domain.dto.TaskStatusDTO;
import com.platform.mesh.tmp.biz.modules.task.status.domain.vo.TaskStatusVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务状态信息
 * @author 蝉鸣
 */
@Tag(description = "TaskStatusController", name = "任务状态")
@RestController
@RequestMapping
public class TaskStatusController extends BaseController{
    @Autowired
    private ITaskStatusService taskStatusService;

    /**
	 * 功能描述:
	 * 〈获取任务状态列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskStatusVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务状态分页")
	@PostMapping("/task/status/page")
	public Result<PageVO<TaskStatusVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskStatus> statusMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskStatus.class);
        MPage<TaskStatus> page = taskStatusService.page(statusMPage);
        PageVO<TaskStatusVO> voPage = MPageUtil.convertToVO(page, TaskStatusVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务状态信息〉
     * @param statusId statusId
     * @return 正常返回:{@link Result<TaskStatusVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务状态信息")
    @GetMapping("/task/status/info/{statusId}")
    public Result<TaskStatusVO> getStatusInfoById(@PathVariable("statusId")Long statusId) {
        TaskStatusVO taskStatusVO = taskStatusService.getStatusInfoById(statusId);
        return Result.success(taskStatusVO);
    }

    /**
     * 功能描述:
     * 〈新增任务状态〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link Result<TaskStatusVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务状态")
    @Log(moduleName = "任务状态管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/status/add")
    public Result<TaskStatusVO> addStatus(@Validated @RequestBody TaskStatusDTO statusDTO) {
        return Result.success(taskStatusService.addStatus(statusDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务状态〉
     * @param statusDTO statusDTO
     * @return 正常返回:{@link Result<TaskStatusVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务状态")
    @Log(moduleName = "任务状态管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/status/edit")
    public Result<TaskStatusVO> editStatus(@Validated @RequestBody TaskStatusDTO statusDTO) {
        return Result.success(taskStatusService.editStatus(statusDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务状态〉
     * @param statusId statusId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务状态")
    @Log(moduleName = "任务状态管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/status/delete/{statusId}")
    public Result<Boolean> deleteStatus(@PathVariable(value = "statusId",required = false)Long statusId) {
        return Result.success(taskStatusService.deleteStatus(statusId));
    }

}