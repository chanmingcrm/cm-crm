package com.platform.mesh.tmp.biz.modules.task.postrelhis.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.po.TaskPostRelHis;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.service.ITaskPostRelHisService;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.dto.TaskPostRelHisDTO;
import com.platform.mesh.tmp.biz.modules.task.postrelhis.domain.vo.TaskPostRelHisVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务工职关系历史信息
 * @author 蝉鸣
 */
@Tag(description = "TaskPostRelHisController", name = "任务工职关系历史")
@RestController
@RequestMapping
public class TaskPostRelHisController extends BaseController{
    @Autowired
    private ITaskPostRelHisService taskPostRelHisService;

    /**
	 * 功能描述:
	 * 〈获取任务工职关系历史列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskPostRelHisVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务工职关系历史分页")
	@PostMapping("/task/post/rel/his/page")
	public Result<PageVO<TaskPostRelHisVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskPostRelHis> postRelHisMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskPostRelHis.class);
        MPage<TaskPostRelHis> page = taskPostRelHisService.page(postRelHisMPage);
        PageVO<TaskPostRelHisVO> voPage = MPageUtil.convertToVO(page, TaskPostRelHisVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务工职关系历史信息〉
     * @param postRelHisId postRelHisId
     * @return 正常返回:{@link Result<TaskPostRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务工职关系历史信息")
    @GetMapping("/task/post/rel/his/info/{postRelHisId}")
    public Result<TaskPostRelHisVO> getPostRelHisInfoById(@PathVariable("postRelHisId")Long postRelHisId) {
        TaskPostRelHisVO taskPostRelHisVO = taskPostRelHisService.getPostRelHisInfoById(postRelHisId);
        return Result.success(taskPostRelHisVO);
    }

    /**
     * 功能描述:
     * 〈新增任务工职关系历史〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link Result<TaskPostRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务工职关系历史")
    @Log(moduleName = "任务工职关系历史管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/post/rel/his/add")
    public Result<TaskPostRelHisVO> addPostRelHis(@Validated @RequestBody TaskPostRelHisDTO postRelHisDTO) {
        return Result.success(taskPostRelHisService.addPostRelHis(postRelHisDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务工职关系历史〉
     * @param postRelHisDTO postRelHisDTO
     * @return 正常返回:{@link Result<TaskPostRelHisVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务工职关系历史")
    @Log(moduleName = "任务工职关系历史管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/post/rel/his/edit")
    public Result<TaskPostRelHisVO> editPostRelHis(@Validated @RequestBody TaskPostRelHisDTO postRelHisDTO) {
        return Result.success(taskPostRelHisService.editPostRelHis(postRelHisDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务工职关系历史〉
     * @param postRelHisId postRelHisId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务工职关系历史")
    @Log(moduleName = "任务工职关系历史管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/post/rel/his/delete/{postRelHisId}")
    public Result<Boolean> deletePostRelHis(@PathVariable(value = "postRelHisId",required = false)Long postRelHisId) {
        return Result.success(taskPostRelHisService.deletePostRelHis(postRelHisId));
    }

}