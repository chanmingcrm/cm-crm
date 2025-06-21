package com.platform.mesh.tmp.biz.modules.task.postrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.po.TaskPostRel;
import com.platform.mesh.tmp.biz.modules.task.postrel.service.ITaskPostRelService;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.dto.TaskPostRelDTO;
import com.platform.mesh.tmp.biz.modules.task.postrel.domain.vo.TaskPostRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务工职关系信息
 * @author 蝉鸣
 */
@Tag(description = "TaskPostRelController", name = "任务工职关系")
@RestController
@RequestMapping
public class TaskPostRelController extends BaseController{
    @Autowired
    private ITaskPostRelService taskPostRelService;

    /**
	 * 功能描述:
	 * 〈获取任务工职关系列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskPostRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务工职关系分页")
	@PostMapping("/task/post/rel/page")
	public Result<PageVO<TaskPostRelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskPostRel> postRelMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskPostRel.class);
        MPage<TaskPostRel> page = taskPostRelService.page(postRelMPage);
        PageVO<TaskPostRelVO> voPage = MPageUtil.convertToVO(page, TaskPostRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务工职关系信息〉
     * @param postRelId postRelId
     * @return 正常返回:{@link Result<TaskPostRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务工职关系信息")
    @GetMapping("/task/post/rel/info/{postRelId}")
    public Result<TaskPostRelVO> getPostRelInfoById(@PathVariable("postRelId")Long postRelId) {
        TaskPostRelVO taskPostRelVO = taskPostRelService.getPostRelInfoById(postRelId);
        return Result.success(taskPostRelVO);
    }

    /**
     * 功能描述:
     * 〈新增任务工职关系〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link Result<TaskPostRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务工职关系")
    @Log(moduleName = "任务工职关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/post/rel/add")
    public Result<TaskPostRelVO> addPostRel(@Validated @RequestBody TaskPostRelDTO postRelDTO) {
        return Result.success(taskPostRelService.addPostRel(postRelDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务工职关系〉
     * @param postRelDTO postRelDTO
     * @return 正常返回:{@link Result<TaskPostRelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务工职关系")
    @Log(moduleName = "任务工职关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/post/rel/edit")
    public Result<TaskPostRelVO> editPostRel(@Validated @RequestBody TaskPostRelDTO postRelDTO) {
        return Result.success(taskPostRelService.editPostRel(postRelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务工职关系〉
     * @param postRelId postRelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务工职关系")
    @Log(moduleName = "任务工职关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/post/rel/delete/{postRelId}")
    public Result<Boolean> deletePostRel(@PathVariable(value = "postRelId",required = false)Long postRelId) {
        return Result.success(taskPostRelService.deletePostRel(postRelId));
    }

}