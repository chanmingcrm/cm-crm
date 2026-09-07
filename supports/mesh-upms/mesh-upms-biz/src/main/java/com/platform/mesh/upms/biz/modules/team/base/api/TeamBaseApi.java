package com.platform.mesh.upms.biz.modules.team.base.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.biz.modules.team.base.service.ITeamBaseService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 团队信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "TeamBaseApi", name = "团队信息API")
@RestController
public class TeamBaseApi extends BaseController {


    @Autowired
    private ITeamBaseService teamBaseService;

    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "增加团队成员列表")
    @PostMapping("/api/team/member/add")
    public Result<Boolean> addTeamMember(@RequestBody TeamBaseDTO baseDTO) {
        return Result.success(teamBaseService.addTeamMember(baseDTO));
    }

    /**
     * 功能描述:
     * 〈删除团队成员列表〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "增加团队成员列表")
    @PostMapping("/api/team/member/delete")
    public Result<Boolean> deleteTeamMember(@RequestBody TeamBaseDelDTO delDTO) {
        return Result.success(teamBaseService.deleteTeamMember(delDTO));
    }

    /**
     * 功能描述:
     * 〈获取团队成员列表〉
     * @param getDTO getDTO
     * @return 正常返回:{@link Result<List<TeamMemberRelBO>>}
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "获取团队成员列表")
    @PostMapping("/api/team/member/get")
    public Result<List<TeamMemberRelBO>> getTeamMember(@RequestBody TeamBaseGetDTO getDTO) {
        List<TeamMemberRelBO> teamMember = teamBaseService.getTeamMember(getDTO);
        return Result.success(teamMember);
    }

}
