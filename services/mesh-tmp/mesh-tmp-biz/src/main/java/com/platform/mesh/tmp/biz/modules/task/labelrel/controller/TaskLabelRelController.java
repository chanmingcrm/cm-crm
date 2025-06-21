package com.platform.mesh.tmp.biz.modules.task.labelrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.po.TaskLabelRel;
import com.platform.mesh.tmp.biz.modules.task.labelrel.service.ITaskLabelRelService;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.dto.TaskLabelRelDTO;
import com.platform.mesh.tmp.biz.modules.task.labelrel.domain.vo.TaskLabelRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务标签关系信息
 * @author 蝉鸣
 */
@Tag(description = "TaskLabelRelController", name = "任务标签关系")
@RestController
@RequestMapping
public class TaskLabelRelController extends BaseController{
    @Autowired
    private ITaskLabelRelService taskLabelRelService;

    /**
	 * 功能描述:
	 * 〈获取任务标签关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskLabelRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务标签关系分页")
	@PostMapping("/task/label/rel/page")
	public Result<PageVO<TaskLabelRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskLabelRel> labelRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskLabelRel.class);
        MPage<TaskLabelRel> page = taskLabelRelService.page(labelRelMPage);
        PageVO<TaskLabelRelVO> voPage = MPageUtil.convertToVO(page, TaskLabelRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务标签关系信息〉
     * @param labelRelId labelRelId
     * @return 正常返回:{@link Result<TaskLabelRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务标签关系信息")
    @GetMapping("/task/label/rel/info/{labelRelId}")
    public Result<TaskLabelRelVO> getLabelRelInfoById(@PathVariable("labelRelId")Long labelRelId) {
        TaskLabelRelVO taskLabelRelVO = taskLabelRelService.getLabelRelInfoById(labelRelId);
        return Result.success(taskLabelRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务标签关系〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link Result<TaskLabelRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务标签关系")
    @Log(moduleName = "任务标签关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/label/rel/add")
    public Result<TaskLabelRelVO> addLabelRel(@Validated @RequestBody TaskLabelRelDTO labelRelDTO) {
        return Result.success(taskLabelRelService.addLabelRel(labelRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务标签关系〉
     * @param labelRelDTO labelRelDTO
     * @return 正常返回:{@link Result<TaskLabelRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务标签关系")
    @Log(moduleName = "任务标签关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/label/rel/edit")
    public Result<TaskLabelRelVO> editLabelRel(@Validated @RequestBody TaskLabelRelDTO labelRelDTO) {
        return Result.success(taskLabelRelService.editLabelRel(labelRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务标签关系〉
     * @param labelRelId labelRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务标签关系")
    @Log(moduleName = "任务标签关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/label/rel/delete/{labelRelId}")
    public Result<Boolean> deleteLabelRel(@PathVariable(value = "labelRelId",required = false)Long labelRelId) {
        return Result.success(taskLabelRelService.deleteLabelRel(labelRelId));
    }

}