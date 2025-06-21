package com.platform.mesh.upms.biz.modules.org.post.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostAddDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostEditDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.dto.OrgPostPageDTO;
import com.platform.mesh.upms.biz.modules.org.post.domain.po.OrgPost;
import com.platform.mesh.upms.biz.modules.org.post.domain.vo.OrgPostVO;
import com.platform.mesh.upms.biz.modules.org.post.service.IOrgPostService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 岗位信息
 * @author 蝉鸣
 */
@Tag(description = "OrgPostController", name = "岗位信息")
@RestController
public class OrgPostController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IOrgPostService orgPostService;


    @Operation(summary = "获取岗位分页")
    @PostMapping("/post/page")
    public Result<PageVO<OrgPostVO>> selectPage(@RequestBody OrgPostPageDTO orgPostPageDTO) {
        MPage<OrgPostVO> postMPage = orgPostService.selectPage(orgPostPageDTO);
        return Result.success(MPageUtil.convertToVO(postMPage,OrgPostVO.class));
    }
    /**
     * 功能描述:
     * 〈通过主键查询单条数据〉
     * @param id id
     * @return 正常返回:{@link Result<OrgPost>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取岗位")
    @GetMapping("/post/info/{id}")
    public Result<OrgPostVO> getPostInfoById(@PathVariable("id") Long id) {
        return Result.success(this.orgPostService.getPostInfoById(id));
    }

    /**
     * 功能描述:
     * 〈新增岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link Result<OrgPostVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增岗位")
    @Log(moduleName = "岗位管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/post/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
    public Result<OrgPostVO> addPost(@Validated @RequestBody OrgPostAddDTO postDTO) {
        return Result.success(orgPostService.addPost(postDTO));
    }

    /**
     * 功能描述:
     * 〈修改岗位〉
     * @param postDTO postDTO
     * @return 正常返回:{@link Result<OrgMemberVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改岗位")
    @Log(moduleName = "岗位管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/post/edit")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:edit')")
    public Result<OrgMemberVO> editPost(@Validated @RequestBody OrgPostEditDTO postDTO) {
        return Result.success(orgPostService.editPost(postDTO));
    }

    /**
     * 功能描述:
     * 〈删除成员〉
     * @param postId postId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除岗位")
    @Log(moduleName = "岗位管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/post/delete/{postId}")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
    public Result<Boolean> deletePost(@PathVariable(value = "postId",required = false)Long postId) {
        return Result.success(orgPostService.deletePost(postId));
    }
  
}
