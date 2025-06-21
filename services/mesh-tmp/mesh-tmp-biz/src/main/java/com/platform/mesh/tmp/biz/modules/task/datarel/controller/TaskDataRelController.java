package com.platform.mesh.tmp.biz.modules.task.datarel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.po.TaskDataRel;
import com.platform.mesh.tmp.biz.modules.task.datarel.service.ITaskDataRelService;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.dto.TaskDataRelDTO;
import com.platform.mesh.tmp.biz.modules.task.datarel.domain.vo.TaskDataRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务数据关联信息
 * @author 蝉鸣
 */
@Tag(description = "TaskDataRelController", name = "任务数据关联")
@RestController
@RequestMapping
public class TaskDataRelController extends BaseController{
    @Autowired
    private ITaskDataRelService taskDataRelService;

    /**
	 * 功能描述:
	 * 〈获取任务数据关联列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskDataRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务数据关联分页")
	@PostMapping("/task/data/rel/page")
	public Result<PageVO<TaskDataRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskDataRel> dataRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskDataRel.class);
        MPage<TaskDataRel> page = taskDataRelService.page(dataRelMPage);
        PageVO<TaskDataRelVO> voPage = MPageUtil.convertToVO(page, TaskDataRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务数据关联信息〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Result<TaskDataRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务数据关联信息")
    @GetMapping("/task/data/rel/info/{dataRelId}")
    public Result<TaskDataRelVO> getDataRelInfoById(@PathVariable("dataRelId")Long dataRelId) {
        TaskDataRelVO taskDataRelVO = taskDataRelService.getDataRelInfoById(dataRelId);
        return Result.success(taskDataRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link Result<TaskDataRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务数据关联")
    @Log(moduleName = "任务数据关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/data/rel/add")
    public Result<TaskDataRelVO> addDataRel(@Validated @RequestBody TaskDataRelDTO dataRelDTO) {
        return Result.success(taskDataRelService.addDataRel(dataRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务数据关联〉
     * @param dataRelDTO dataRelDTO
     * @return 正常返回:{@link Result<TaskDataRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务数据关联")
    @Log(moduleName = "任务数据关联管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/data/rel/edit")
    public Result<TaskDataRelVO> editDataRel(@Validated @RequestBody TaskDataRelDTO dataRelDTO) {
        return Result.success(taskDataRelService.editDataRel(dataRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务数据关联〉
     * @param dataRelId dataRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务数据关联")
    @Log(moduleName = "任务数据关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/data/rel/delete/{dataRelId}")
    public Result<Boolean> deleteDataRel(@PathVariable(value = "dataRelId",required = false)Long dataRelId) {
        return Result.success(taskDataRelService.deleteDataRel(dataRelId));
    }

}