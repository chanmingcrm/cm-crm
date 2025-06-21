package com.platform.mesh.tmp.biz.modules.task.label.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.label.domain.po.TaskLabel;
import com.platform.mesh.tmp.biz.modules.task.label.service.ITaskLabelService;
import com.platform.mesh.tmp.biz.modules.task.label.domain.dto.TaskLabelDTO;
import com.platform.mesh.tmp.biz.modules.task.label.domain.vo.TaskLabelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务标签信息
 * @author 蝉鸣
 */
@Tag(description = "TaskLabelController", name = "任务标签")
@RestController
@RequestMapping
public class TaskLabelController extends BaseController{
    @Autowired
    private ITaskLabelService taskLabelService;

    /**
	 * 功能描述:
	 * 〈获取任务标签列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskLabelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务标签分页")
	@PostMapping("/task/label/page")
	public Result<PageVO<TaskLabelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskLabel> labelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskLabel.class);
        MPage<TaskLabel> page = taskLabelService.page(labelMPage);
        PageVO<TaskLabelVO> voPage = MPageUtil.convertToVO(page, TaskLabelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务标签信息〉
     * @param labelId labelId
     * @return 正常返回:{@link Result<TaskLabelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务标签信息")
    @GetMapping("/task/label/info/{labelId}")
    public Result<TaskLabelVO> getLabelInfoById(@PathVariable("labelId")Long labelId) {
        TaskLabelVO taskLabelVO = taskLabelService.getLabelInfoById(labelId);
        return Result.success(taskLabelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务标签〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link Result<TaskLabelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务标签")
    @Log(moduleName = "任务标签管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/label/add")
    public Result<TaskLabelVO> addLabel(@Validated @RequestBody TaskLabelDTO labelDTO) {
        return Result.success(taskLabelService.addLabel(labelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务标签〉
     * @param labelDTO labelDTO
     * @return 正常返回:{@link Result<TaskLabelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务标签")
    @Log(moduleName = "任务标签管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/label/edit")
    public Result<TaskLabelVO> editLabel(@Validated @RequestBody TaskLabelDTO labelDTO) {
        return Result.success(taskLabelService.editLabel(labelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务标签〉
     * @param labelId labelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务标签")
    @Log(moduleName = "任务标签管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/label/delete/{labelId}")
    public Result<Boolean> deleteLabel(@PathVariable(value = "labelId",required = false)Long labelId) {
        return Result.success(taskLabelService.deleteLabel(labelId));
    }

}