package com.platform.mesh.upms.biz.modules.team.memberrolerel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.team.memberrolerel.domain.po.TeamMemberRoleRel;
import com.platform.mesh.upms.biz.modules.team.memberrolerel.mapper.TeamMemberRoleRelMapper;
import com.platform.mesh.upms.biz.modules.team.memberrolerel.service.ITeamMemberRoleRelService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 团队成员角色关系信息
 * @author 蝉鸣
 */
@Service()
public class TeamMemberRoleRelServiceImpl extends ServiceImpl<TeamMemberRoleRelMapper, TeamMemberRoleRel> implements ITeamMemberRoleRelService {


}

