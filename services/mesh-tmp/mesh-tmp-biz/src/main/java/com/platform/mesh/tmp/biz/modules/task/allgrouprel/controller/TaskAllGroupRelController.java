package com.platform.mesh.tmp.biz.modules.task.allgrouprel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.po.TaskAllGroupRel;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.service.ITaskAllGroupRelService;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.dto.TaskAllGroupRelDTO;
import com.platform.mesh.tmp.biz.modules.task.allgrouprel.domain.vo.TaskAllGroupRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务分组关系信息
 * @author 蝉鸣
 */
@Tag(description = "TaskAllGroupRelController", name = "任务分组关系")
@RestController
@RequestMapping
public class TaskAllGroupRelController extends BaseController{
    @Autowired
    private ITaskAllGroupRelService taskAllGroupRelService;

    /**
	 * 功能描述:
	 * 〈获取任务分组关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskAllGroupRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务分组关系分页")
	@PostMapping("/task/all/group/rel/page")
	public Result<PageVO<TaskAllGroupRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskAllGroupRel> allGroupRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskAllGroupRel.class);
        MPage<TaskAllGroupRel> page = taskAllGroupRelService.page(allGroupRelMPage);
        PageVO<TaskAllGroupRelVO> voPage = MPageUtil.convertToVO(page, TaskAllGroupRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务分组关系信息〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<TaskAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务分组关系信息")
    @GetMapping("/task/all/group/rel/info/{allGroupRelId}")
    public Result<TaskAllGroupRelVO> getAllGroupRelInfoById(@PathVariable("allGroupRelId")Long allGroupRelId) {
        TaskAllGroupRelVO taskAllGroupRelVO = taskAllGroupRelService.getAllGroupRelInfoById(allGroupRelId);
        return Result.success(taskAllGroupRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务分组关系〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<TaskAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务分组关系")
    @Log(moduleName = "任务分组关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/all/group/rel/add")
    public Result<TaskAllGroupRelVO> addAllGroupRel(@Validated @RequestBody TaskAllGroupRelDTO allGroupRelDTO) {
        return Result.success(taskAllGroupRelService.addAllGroupRel(allGroupRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务分组关系〉
     * @param allGroupRelDTO allGroupRelDTO
     * @return 正常返回:{@link Result<TaskAllGroupRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务分组关系")
    @Log(moduleName = "任务分组关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/all/group/rel/edit")
    public Result<TaskAllGroupRelVO> editAllGroupRel(@Validated @RequestBody TaskAllGroupRelDTO allGroupRelDTO) {
        return Result.success(taskAllGroupRelService.editAllGroupRel(allGroupRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务分组关系〉
     * @param allGroupRelId allGroupRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务分组关系")
    @Log(moduleName = "任务分组关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/all/group/rel/delete/{allGroupRelId}")
    public Result<Boolean> deleteAllGroupRel(@PathVariable(value = "allGroupRelId",required = false)Long allGroupRelId) {
        return Result.success(taskAllGroupRelService.deleteAllGroupRel(allGroupRelId));
    }

}