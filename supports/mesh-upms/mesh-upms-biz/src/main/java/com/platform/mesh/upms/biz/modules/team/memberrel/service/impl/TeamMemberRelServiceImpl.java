package com.platform.mesh.upms.biz.modules.team.memberrel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.po.TeamMemberRel;
import com.platform.mesh.upms.biz.modules.team.memberrel.mapper.TeamMemberRelMapper;
import com.platform.mesh.upms.biz.modules.team.memberrel.service.ITeamMemberRelService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 团队成员关系信息
 * @author 蝉鸣
 */
@Service()
public class TeamMemberRelServiceImpl extends ServiceImpl<TeamMemberRelMapper, TeamMemberRel> implements ITeamMemberRelService {


}

