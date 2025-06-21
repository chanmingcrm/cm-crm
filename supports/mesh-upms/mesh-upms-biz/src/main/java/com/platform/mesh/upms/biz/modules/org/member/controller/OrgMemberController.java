package com.platform.mesh.upms.biz.modules.org.member.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberPageDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberInfoVO;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.member.service.IOrgMemberService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 成员信息
 * @author 蝉鸣
 */
@Tag(description = "OrgMemberController", name = "成员信息")
@RestController
public class OrgMemberController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IOrgMemberService orgMemberService;

    /**
     * 功能描述:
     * 〈查询成员分页〉
     * @param orgMemberPageDTO orgMemberPageDTO
     * @return 正常返回:{@link Result<PageVO<OrgMemberVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员分页")
    @PostMapping("/member/page")
    public Result<PageVO<OrgMemberVO>> selectPage(@RequestBody OrgMemberPageDTO orgMemberPageDTO) {
        MPage<OrgMemberVO> memberMPage = orgMemberService.selectPage(orgMemberPageDTO);
        return Result.success(MPageUtil.convertToVO(memberMPage,OrgMemberVO.class));
    }

    /**
     * 功能描述:
     * 〈通过主键查询单条数据〉
     * @param id id
     * @return 正常返回:{@link Result}
     * @author 蝉鸣
     */
    @Operation(summary = "获取成员")
    @GetMapping("/member/info/{id}")
    public Result<OrgMemberInfoVO> getMemberInfoById(@PathVariable("id") Long id) {
        return Result.success(this.orgMemberService.getMemberInfoById(id));
    }

    /**
     * 功能描述:
     * 〈新增成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link Result<OrgMemberVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增成员")
    @Log(moduleName = "成员管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/member/add")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:add')")
    public Result<OrgMemberVO> addMember(@Validated @RequestBody OrgMemberDTO memberDTO) {
        return Result.success(orgMemberService.addMember(memberDTO));
    }

    /**
     * 功能描述:
     * 〈修改成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link Result<OrgMemberVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改成员")
    @Log(moduleName = "成员管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/member/edit")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:edit')")
    public Result<OrgMemberVO> editMember(@Validated @RequestBody OrgMemberDTO memberDTO) {
        return Result.success(orgMemberService.editMember(memberDTO));
    }

    /**
     * 功能描述:
     * 〈删除成员〉
     * @param memberId memberId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除成员")
    @Log(moduleName = "成员管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/member/delete/{memberId}")
//	@PreAuthorize("@rolePermission.hasPermi('org:level:delete')")
    public Result<Boolean> deleteMember(@PathVariable(value = "memberId",required = false)Long memberId) {
        return Result.success(orgMemberService.deleteMember(memberId));
    }
}
