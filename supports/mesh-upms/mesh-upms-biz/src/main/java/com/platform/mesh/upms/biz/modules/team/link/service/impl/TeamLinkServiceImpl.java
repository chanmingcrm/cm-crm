package com.platform.mesh.upms.biz.modules.team.link.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.upms.biz.modules.team.link.domain.po.TeamLink;
import com.platform.mesh.upms.biz.modules.team.link.mapper.TeamLinkMapper;
import com.platform.mesh.upms.biz.modules.team.link.service.ITeamLinkService;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 团队数据关系信息
 * @author 蝉鸣
 */
@Service()
public class TeamLinkServiceImpl extends ServiceImpl<TeamLinkMapper, TeamLink> implements ITeamLinkService {


}

