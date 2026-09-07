package com.platform.mesh.upms.biz.modules.team.base.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBaseShareDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamLinkDTO;
import com.platform.mesh.upms.biz.modules.team.link.domain.po.TeamLink;
import com.platform.mesh.upms.biz.modules.team.link.service.ITeamLinkService;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamMemberRelDTO;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.po.TeamMemberRel;
import com.platform.mesh.upms.biz.modules.team.memberrel.service.ITeamMemberRelService;
import com.platform.mesh.upms.biz.modules.team.memberrolerel.domain.po.TeamMemberRoleRel;
import com.platform.mesh.upms.biz.modules.team.memberrolerel.service.ITeamMemberRoleRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class TeamBaseServiceManual {

    @Autowired
    private ITeamLinkService teamLinkService;

    @Autowired
    private ITeamMemberRelService teamMemberRelService;

    @Autowired
    private ITeamMemberRoleRelService teamMemberRoleRelService;


    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param teamId teamId
     * @param linkDTO linkDTO
     * @author 蝉鸣
     */
    public void saveTeamLink(Long teamId, TeamLinkDTO linkDTO) {
        if(ObjectUtil.isEmpty(teamId) || ObjectUtil.isEmpty(linkDTO.getDataId())){
            return;
        }
        //先删除旧数据
        boolean exists = teamLinkService.lambdaQuery()
                .eq(TeamLink::getTeamId, teamId)
                .eq(TeamLink::getModuleId, linkDTO.getModuleId())
                .eq(TeamLink::getDataId, linkDTO.getDataId())
                .eq(TeamLink::getInitFlag, YesOrNoEnum.YES.getValue())
                .exists();
        if(exists){
            return;
        }
        TeamLink teamLink = BeanUtil.copyProperties(linkDTO, TeamLink.class);
        teamLink.setTeamId(teamId);
        teamLink.setInitFlag(YesOrNoEnum.YES.getValue());
        teamLinkService.save(teamLink);
    }

    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param teamId teamId
     * @param memberRelDTOS memberRelDTOS
     * @author 蝉鸣
     */
    public void saveTeamMember(Long teamId, List<TeamMemberRelDTO> memberRelDTOS) {
        if(ObjectUtil.isEmpty(teamId) || CollUtil.isEmpty(memberRelDTOS)){
            return;
        }
        List<TeamMemberRel> memberRelList = CollUtil.newArrayList();
        List<TeamMemberRoleRel> memberRelRoleList = CollUtil.newArrayList();
        for (TeamMemberRelDTO memberRelDTO : memberRelDTOS) {
            TeamMemberRel teamMemberRel = BeanUtil.copyProperties(memberRelDTO, TeamMemberRel.class);
            teamMemberRel.setTeamId(teamId);
            memberRelList.add(teamMemberRel);
            for (Long roleId : memberRelDTO.getRoleIds()) {
                TeamMemberRoleRel teamMemberRoleRel = BeanUtil.copyProperties(memberRelDTO, TeamMemberRoleRel.class);
                teamMemberRoleRel.setTeamId(teamId);
                teamMemberRoleRel.setRoleId(roleId);
                memberRelRoleList.add(teamMemberRoleRel);
            }
        }
        List<Long> memberIds = memberRelList.stream().map(TeamMemberRel::getMemberId).distinct().toList();
        //删除旧信息
        teamMemberRelService.lambdaUpdate()
                .eq(TeamMemberRel::getTeamId, teamId)
                .in(TeamMemberRel::getMemberId, memberIds)
                .remove();
        teamMemberRoleRelService.lambdaUpdate()
                .eq(TeamMemberRoleRel::getTeamId, teamId)
                .in(TeamMemberRoleRel::getMemberId, memberIds)
                .remove();
        //增加成员信息
        teamMemberRelService.saveBatch(memberRelList);
        //增加成员角色信息
        teamMemberRoleRelService.saveBatch(memberRelRoleList);
    }

    /**
     * 功能描述:
     * 〈分享团队成员〉
     * @param shareDTO shareDTO
     * @author 蝉鸣
     */
    public void shareTeamMember(TeamBaseShareDTO shareDTO) {
        if(ObjectUtil.isEmpty(shareDTO.getTeamId()) || CollUtil.isEmpty(shareDTO.getModuleIds())){
            return;
        }
        teamLinkService.lambdaUpdate()
                .eq(TeamLink::getTeamId, shareDTO.getTeamId())
                .eq(TeamLink::getInitFlag, YesOrNoEnum.NO.getValue())
                .remove();
        List<TeamLink> teamLinks = shareDTO.getModuleIds().stream().map(moduleId -> {
            TeamLink teamLink = new TeamLink();
            teamLink.setTeamId(shareDTO.getTeamId());
            teamLink.setModuleId(moduleId);
            teamLink.setInitFlag(YesOrNoEnum.NO.getValue());
            return teamLink;
        }).toList();
        teamLinkService.saveBatch(teamLinks);
    }

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @author 蝉鸣
     */
    public void deleteTeamMember(TeamBaseDelDTO delDTO) {
        if(ObjectUtil.isEmpty(delDTO.getTeamId()) || CollUtil.isEmpty(delDTO.getMemberIds())){
            return;
        }
        //删除成员信息
        teamMemberRelService.lambdaUpdate()
                .eq(TeamMemberRel::getTeamId,delDTO.getTeamId())
                .in(TeamMemberRel::getMemberId,delDTO.getMemberIds())
                .remove();
        //删除成员角色信息
        teamMemberRoleRelService.lambdaUpdate()
                .eq(TeamMemberRoleRel::getTeamId,delDTO.getTeamId())
                .in(TeamMemberRoleRel::getMemberId,delDTO.getMemberIds())
                .remove();
    }
}

