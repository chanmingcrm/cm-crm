package com.platform.mesh.tmp.biz.modules.task.post.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.tmp.biz.modules.task.post.domain.po.TaskPost;
import com.platform.mesh.tmp.biz.modules.task.post.service.ITaskPostService;
import com.platform.mesh.tmp.biz.modules.task.post.domain.dto.TaskPostDTO;
import com.platform.mesh.tmp.biz.modules.task.post.domain.vo.TaskPostVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 任务工职信息
 * @author 蝉鸣
 */
@Tag(description = "TaskPostController", name = "任务工职")
@RestController
@RequestMapping
public class TaskPostController extends BaseController{
    @Autowired
    private ITaskPostService taskPostService;

    /**
	 * 功能描述:
	 * 〈获取任务工职列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<TaskPostVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取任务工职分页")
	@PostMapping("/task/post/page")
	public Result<PageVO<TaskPostVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<TaskPost> postMPage = MPageUtil.pageEntityToMPage(pageDTO, TaskPost.class);
        MPage<TaskPost> page = taskPostService.page(postMPage);
        PageVO<TaskPostVO> voPage = MPageUtil.convertToVO(page, TaskPostVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前任务工职信息〉
     * @param postId postId
     * @return 正常返回:{@link Result<TaskPostVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前任务工职信息")
    @GetMapping("/task/post/info/{postId}")
    public Result<TaskPostVO> getPostInfoById(@PathVariable("postId")Long postId) {
        TaskPostVO taskPostVO = taskPostService.getPostInfoById(postId);
        return Result.success(taskPostVO);
    }

    /**
     * 功能描述:
     * 〈新增任务工职〉
     * @param postDTO postDTO
     * @return 正常返回:{@link Result<TaskPostVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增任务工职")
    @Log(moduleName = "任务工职管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/task/post/add")
    public Result<TaskPostVO> addPost(@Validated @RequestBody TaskPostDTO postDTO) {
        return Result.success(taskPostService.addPost(postDTO));
    }

    /**
     * 功能描述:
     * 〈修改任务工职〉
     * @param postDTO postDTO
     * @return 正常返回:{@link Result<TaskPostVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改任务工职")
    @Log(moduleName = "任务工职管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/task/post/edit")
    public Result<TaskPostVO> editPost(@Validated @RequestBody TaskPostDTO postDTO) {
        return Result.success(taskPostService.editPost(postDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除任务工职〉
     * @param postId postId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除任务工职")
    @Log(moduleName = "任务工职管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/task/post/delete/{postId}")
    public Result<Boolean> deletePost(@PathVariable(value = "postId",required = false)Long postId) {
        return Result.success(taskPostService.deletePost(postId));
    }

}