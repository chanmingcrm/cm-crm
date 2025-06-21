package com.platform.mesh.tmp.biz.modules.task.priority.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.po.TaskPriority;
import com.platform.mesh.tmp.biz.modules.task.priority.service.ITaskPriorityService;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.dto.TaskPriorityDTO;
import com.platform.mesh.tmp.biz.modules.task.priority.domain.vo.TaskPriorityVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务优先级信息
 * @author 蝉鸣
 */
@Tag(description = "TaskPriorityController", name = "任务优先级")
@RestController
@RequestMapping
public class TaskPriorityController extends BaseController{
    @Autowired
    private ITaskPriorityService taskPriorityService;

    /**
	 * 功能描述:
	 * 〈获取任务优先级列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskPriorityVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务优先级分页")
	@PostMapping("/task/priority/page")
	public Result<PageVO<TaskPriorityVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskPriority> priorityMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskPriority.class);
        MPage<TaskPriority> page = taskPriorityService.page(priorityMPage);
        PageVO<TaskPriorityVO> voPage = MPageUtil.convertToVO(page, TaskPriorityVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务优先级信息〉
     * @param priorityId priorityId
     * @return 正常返回:{@link Result<TaskPriorityVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务优先级信息")
    @GetMapping("/task/priority/info/{priorityId}")
    public Result<TaskPriorityVO> getPriorityInfoById(@PathVariable("priorityId")Long priorityId) {
        TaskPriorityVO taskPriorityVO = taskPriorityService.getPriorityInfoById(priorityId);
        return Result.success(taskPriorityVO);
    }

    /**
     * 功能描述:
     * 〈新增任务优先级〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link Result<TaskPriorityVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务优先级")
    @Log(moduleName = "任务优先级管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/priority/add")
    public Result<TaskPriorityVO> addPriority(@Validated @RequestBody TaskPriorityDTO priorityDTO) {
        return Result.success(taskPriorityService.addPriority(priorityDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务优先级〉
     * @param priorityDTO priorityDTO
     * @return 正常返回:{@link Result<TaskPriorityVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务优先级")
    @Log(moduleName = "任务优先级管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/priority/edit")
    public Result<TaskPriorityVO> editPriority(@Validated @RequestBody TaskPriorityDTO priorityDTO) {
        return Result.success(taskPriorityService.editPriority(priorityDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务优先级〉
     * @param priorityId priorityId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务优先级")
    @Log(moduleName = "任务优先级管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/priority/delete/{priorityId}")
    public Result<Boolean> deletePriority(@PathVariable(value = "priorityId",required = false)Long priorityId) {
        return Result.success(taskPriorityService.deletePriority(priorityId));
    }

}