package com.platform.mesh.upms.biz.modules.team.base.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBasePageDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBaseShareDTO;
import com.platform.mesh.upms.biz.modules.team.base.service.ITeamBaseService;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.vo.TeamMemberRelVO;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 团队信息
 * @author 蝉鸣
 */
@Tag(description = "TeamBaseController", name = "团队信息")
@RestController
public class TeamBaseController extends BaseController {

    @Autowired
    private ITeamBaseService teamBaseService;

    /**
     * 功能描述:
     * 〈查询团队下成员分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<TeamMemberRelVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取组织列表")
    @PostMapping("/team/member/page")
    public Result<PageVO<TeamMemberRelVO>> getTeamMemberPage(@RequestBody TeamBasePageDTO pageDTO) {
        MPage<TeamMemberRelVO> page = teamBaseService.selectTeamMemberPage(pageDTO);
        PageVO<TeamMemberRelVO> voPage = MPageUtil.convertToVO(page, TeamMemberRelVO.class);
        return Result.success(voPage);
    }


    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "增加团队成员列表")
    @PostMapping("/team/member/add")
    public Result<Boolean> addTeamMember(@RequestBody TeamBaseDTO baseDTO) {
        return Result.success(teamBaseService.addTeamMember(baseDTO));
    }

    /**
     * 功能描述:
     * 〈增加团队成员〉
     * @param shareDTO shareDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "共享团队成员列表")
    @PostMapping("/team/member/share")
    public Result<Boolean> shareTeamMember(@RequestBody TeamBaseShareDTO shareDTO) {
        return Result.success(teamBaseService.shareTeamMember(shareDTO));
    }


    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除团队成员")
    @PostMapping("/team/member/delete/{teamId}")
    public Result<Boolean> deleteTeamMember(@RequestBody TeamBaseDelDTO delDTO) {
        return Result.success(teamBaseService.deleteTeamMember(delDTO));
    }

}
