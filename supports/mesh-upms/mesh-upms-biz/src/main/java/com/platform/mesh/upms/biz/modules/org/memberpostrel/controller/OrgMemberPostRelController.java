package com.platform.mesh.upms.biz.modules.org.memberpostrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelTransDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberLevelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.service.IOrgMemberPostRelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 成员信息
 * @author 蝉鸣
 */
@Tag(description = "OrgMemberPostRelController", name = "成员岗位关系信息")
@RestController
public class OrgMemberPostRelController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IOrgMemberPostRelService orgMemberPostRelService;

    /**
     * 功能描述:
     * 〈获取成员-层级分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgLevelPostRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员-层级分页")
    @PostMapping("/member/level/page")
    public Result<MPage<OrgMemberLevelVO>> selectLevelPage(@RequestBody OrgMemberPostRelPageDTO pageDTO) {
        return Result.success(this.orgMemberPostRelService.selectLevelPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈获取成员-岗位分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<MPage<OrgLevelPostRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员-岗位分页")
    @PostMapping("/member/post/page")
    public Result<MPage<OrgMemberPostRelPageVO>> selectPostPage(@RequestBody OrgMemberPostRelPageDTO pageDTO) {
        return Result.success(this.orgMemberPostRelService.selectPostPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈新增成员-岗位关系〉
     * @param memberPostRelDTO memberPostRelDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增成员-岗位关系")
    @Log(moduleName = "成员-岗位关系管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/member/post/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
    public Result<Boolean> addMemberPost(@Validated @RequestBody OrgMemberPostRelDTO memberPostRelDTO) {
        return Result.success(orgMemberPostRelService.addMemberPost(memberPostRelDTO));
    }

    /**
     * 功能描述:
     * 〈删除成员-岗位关系〉
     * @param relId relId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除成员-岗位关系")
    @Log(moduleName = "成员-岗位关系管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/member/post/delete/{relId}")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
    public Result<Boolean> deleteMemberPost(@PathVariable(value = "relId")Long relId) {
        return Result.success(orgMemberPostRelService.deleteMemberPost(relId));
    }

    /**
     * 功能描述:
     * 〈转移成员-岗位关系〉
     * @param transDTO transDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "转移成员-岗位关系")
    @Log(moduleName = "成员-岗位关系管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/member/post/trans")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
    public Result<Boolean> transMemberPost(@RequestBody OrgMemberPostRelTransDTO transDTO) {
        return Result.success(orgMemberPostRelService.transMemberPost(transDTO));
    }
}
