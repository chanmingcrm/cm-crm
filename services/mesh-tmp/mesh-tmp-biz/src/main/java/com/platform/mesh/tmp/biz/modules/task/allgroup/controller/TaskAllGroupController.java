package com.platform.mesh.tmp.biz.modules.task.allgroup.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.po.TaskAllGroup;
import com.platform.mesh.tmp.biz.modules.task.allgroup.service.ITaskAllGroupService;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.dto.TaskAllGroupDTO;
import com.platform.mesh.tmp.biz.modules.task.allgroup.domain.vo.TaskAllGroupVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务分组信息
 * @author 蝉鸣
 */
@Tag(description = "TaskAllGroupController", name = "任务分组")
@RestController
@RequestMapping
public class TaskAllGroupController extends BaseController{
    @Autowired
    private ITaskAllGroupService taskAllGroupService;

    /**
	 * 功能描述:
	 * 〈获取任务分组列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskAllGroupVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务分组分页")
	@PostMapping("/task/all/group/page")
	public Result<PageVO<TaskAllGroupVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskAllGroup> allGroupMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskAllGroup.class);
        MPage<TaskAllGroup> page = taskAllGroupService.page(allGroupMPage);
        PageVO<TaskAllGroupVO> voPage = MPageUtil.convertToVO(page, TaskAllGroupVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务分组信息〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<TaskAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务分组信息")
    @GetMapping("/task/all/group/info/{allGroupId}")
    public Result<TaskAllGroupVO> getAllGroupInfoById(@PathVariable("allGroupId")Long allGroupId) {
        TaskAllGroupVO taskAllGroupVO = taskAllGroupService.getAllGroupInfoById(allGroupId);
        return Result.success(taskAllGroupVO);
    }

    /**
     * 功能描述:
     * 〈新增任务分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<TaskAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务分组")
    @Log(moduleName = "任务分组管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/all/group/add")
    public Result<TaskAllGroupVO> addAllGroup(@Validated @RequestBody TaskAllGroupDTO allGroupDTO) {
        return Result.success(taskAllGroupService.addAllGroup(allGroupDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务分组〉
     * @param allGroupDTO allGroupDTO
     * @return 正常返回:{@link Result<TaskAllGroupVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务分组")
    @Log(moduleName = "任务分组管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/all/group/edit")
    public Result<TaskAllGroupVO> editAllGroup(@Validated @RequestBody TaskAllGroupDTO allGroupDTO) {
        return Result.success(taskAllGroupService.editAllGroup(allGroupDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务分组〉
     * @param allGroupId allGroupId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务分组")
    @Log(moduleName = "任务分组管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/all/group/delete/{allGroupId}")
    public Result<Boolean> deleteAllGroup(@PathVariable(value = "allGroupId",required = false)Long allGroupId) {
        return Result.success(taskAllGroupService.deleteAllGroup(allGroupId));
    }

}