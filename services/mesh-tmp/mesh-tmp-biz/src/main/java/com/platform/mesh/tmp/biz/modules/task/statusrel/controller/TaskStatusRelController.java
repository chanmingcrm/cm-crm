package com.platform.mesh.tmp.biz.modules.task.statusrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.po.TaskStatusRel;
import com.platform.mesh.tmp.biz.modules.task.statusrel.service.ITaskStatusRelService;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.dto.TaskStatusRelDTO;
import com.platform.mesh.tmp.biz.modules.task.statusrel.domain.vo.TaskStatusRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务状态关系信息
 * @author 蝉鸣
 */
@Tag(description = "TaskStatusRelController", name = "任务状态关系")
@RestController
@RequestMapping
public class TaskStatusRelController extends BaseController{
    @Autowired
    private ITaskStatusRelService taskStatusRelService;

    /**
	 * 功能描述:
	 * 〈获取任务状态关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskStatusRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务状态关系分页")
	@PostMapping("/task/status/rel/page")
	public Result<PageVO<TaskStatusRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskStatusRel> statusRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskStatusRel.class);
        MPage<TaskStatusRel> page = taskStatusRelService.page(statusRelMPage);
        PageVO<TaskStatusRelVO> voPage = MPageUtil.convertToVO(page, TaskStatusRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务状态关系信息〉
     * @param statusRelId statusRelId
     * @return 正常返回:{@link Result<TaskStatusRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务状态关系信息")
    @GetMapping("/task/status/rel/info/{statusRelId}")
    public Result<TaskStatusRelVO> getStatusRelInfoById(@PathVariable("statusRelId")Long statusRelId) {
        TaskStatusRelVO taskStatusRelVO = taskStatusRelService.getStatusRelInfoById(statusRelId);
        return Result.success(taskStatusRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务状态关系〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link Result<TaskStatusRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务状态关系")
    @Log(moduleName = "任务状态关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/status/rel/add")
    public Result<TaskStatusRelVO> addStatusRel(@Validated @RequestBody TaskStatusRelDTO statusRelDTO) {
        return Result.success(taskStatusRelService.addStatusRel(statusRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务状态关系〉
     * @param statusRelDTO statusRelDTO
     * @return 正常返回:{@link Result<TaskStatusRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务状态关系")
    @Log(moduleName = "任务状态关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/status/rel/edit")
    public Result<TaskStatusRelVO> editStatusRel(@Validated @RequestBody TaskStatusRelDTO statusRelDTO) {
        return Result.success(taskStatusRelService.editStatusRel(statusRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务状态关系〉
     * @param statusRelId statusRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务状态关系")
    @Log(moduleName = "任务状态关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/status/rel/delete/{statusRelId}")
    public Result<Boolean> deleteStatusRel(@PathVariable(value = "statusRelId",required = false)Long statusRelId) {
        return Result.success(taskStatusRelService.deleteStatusRel(statusRelId));
    }

}