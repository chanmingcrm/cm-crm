package com.platform.mesh.tmp.biz.modules.task.userrelhis.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.po.TaskUserRelHis;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.service.ITaskUserRelHisService;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.dto.TaskUserRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.userrelhis.domain.vo.TaskUserRelHisVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务人员历史信息
 * @author 蝉鸣
 */
@Tag(description = "TaskUserRelHisController", name = "任务人员历史")
@RestController
@RequestMapping
public class TaskUserRelHisController extends BaseController{
    @Autowired
    private ITaskUserRelHisService taskUserRelHisService;

    /**
	 * 功能描述:
	 * 〈获取任务人员历史列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskUserRelHisVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务人员历史分页")
	@PostMapping("/task/user/rel/his/page")
	public Result<PageVO<TaskUserRelHisVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskUserRelHis> userRelHisMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskUserRelHis.class);
        MPage<TaskUserRelHis> page = taskUserRelHisService.page(userRelHisMPage);
        PageVO<TaskUserRelHisVO> voPage = MPageUtil.convertToVO(page, TaskUserRelHisVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务人员历史信息〉
     * @param userRelHisId userRelHisId
     * @return 正常返回:{@link Result<TaskUserRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务人员历史信息")
    @GetMapping("/task/user/rel/his/info/{userRelHisId}")
    public Result<TaskUserRelHisVO> getUserRelHisInfoById(@PathVariable("userRelHisId")Long userRelHisId) {
        TaskUserRelHisVO taskUserRelHisVO = taskUserRelHisService.getUserRelHisInfoById(userRelHisId);
        return Result.success(taskUserRelHisVO);
    }

    /**
     * 功能描述:
     * 〈新增任务人员历史〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link Result<TaskUserRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务人员历史")
    @Log(moduleName = "任务人员历史管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/user/rel/his/add")
    public Result<TaskUserRelHisVO> addUserRelHis(@Validated @RequestBody TaskUserRelHisDTO userRelHisDTO) {
        return Result.success(taskUserRelHisService.addUserRelHis(userRelHisDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务人员历史〉
     * @param userRelHisDTO userRelHisDTO
     * @return 正常返回:{@link Result<TaskUserRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务人员历史")
    @Log(moduleName = "任务人员历史管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/user/rel/his/edit")
    public Result<TaskUserRelHisVO> editUserRelHis(@Validated @RequestBody TaskUserRelHisDTO userRelHisDTO) {
        return Result.success(taskUserRelHisService.editUserRelHis(userRelHisDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务人员历史〉
     * @param userRelHisId userRelHisId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务人员历史")
    @Log(moduleName = "任务人员历史管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/user/rel/his/delete/{userRelHisId}")
    public Result<Boolean> deleteUserRelHis(@PathVariable(value = "userRelHisId",required = false)Long userRelHisId) {
        return Result.success(taskUserRelHisService.deleteUserRelHis(userRelHisId));
    }

}