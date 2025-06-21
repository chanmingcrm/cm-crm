package com.platform.mesh.tmp.biz.modules.task.userrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.po.TaskUserRel;
import com.platform.mesh.tmp.biz.modules.task.userrel.service.ITaskUserRelService;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.dto.TaskUserRelDTO;
import com.platform.mesh.tmp.biz.modules.task.userrel.domain.vo.TaskUserRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务人员信息
 * @author 蝉鸣
 */
@Tag(description = "TaskUserRelController", name = "任务人员")
@RestController
@RequestMapping
public class TaskUserRelController extends BaseController{
    @Autowired
    private ITaskUserRelService taskUserRelService;

    /**
	 * 功能描述:
	 * 〈获取任务人员列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskUserRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务人员分页")
	@PostMapping("/task/user/rel/page")
	public Result<PageVO<TaskUserRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskUserRel> userRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskUserRel.class);
        MPage<TaskUserRel> page = taskUserRelService.page(userRelMPage);
        PageVO<TaskUserRelVO> voPage = MPageUtil.convertToVO(page, TaskUserRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务人员信息〉
     * @param userRelId userRelId
     * @return 正常返回:{@link Result<TaskUserRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务人员信息")
    @GetMapping("/task/user/rel/info/{userRelId}")
    public Result<TaskUserRelVO> getUserRelInfoById(@PathVariable("userRelId")Long userRelId) {
        TaskUserRelVO taskUserRelVO = taskUserRelService.getUserRelInfoById(userRelId);
        return Result.success(taskUserRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务人员〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link Result<TaskUserRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务人员")
    @Log(moduleName = "任务人员管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/user/rel/add")
    public Result<TaskUserRelVO> addUserRel(@Validated @RequestBody TaskUserRelDTO userRelDTO) {
        return Result.success(taskUserRelService.addUserRel(userRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务人员〉
     * @param userRelDTO userRelDTO
     * @return 正常返回:{@link Result<TaskUserRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务人员")
    @Log(moduleName = "任务人员管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/user/rel/edit")
    public Result<TaskUserRelVO> editUserRel(@Validated @RequestBody TaskUserRelDTO userRelDTO) {
        return Result.success(taskUserRelService.editUserRel(userRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务人员〉
     * @param userRelId userRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务人员")
    @Log(moduleName = "任务人员管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/user/rel/delete/{userRelId}")
    public Result<Boolean> deleteUserRel(@PathVariable(value = "userRelId",required = false)Long userRelId) {
        return Result.success(taskUserRelService.deleteUserRel(userRelId));
    }

}