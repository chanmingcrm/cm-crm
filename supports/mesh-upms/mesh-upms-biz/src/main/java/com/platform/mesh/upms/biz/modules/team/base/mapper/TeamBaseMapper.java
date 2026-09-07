package com.platform.mesh.upms.biz.modules.team.base.mapper;


import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBasePageDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.po.TeamBase;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.vo.TeamMemberRelVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 团队
 * @author 蝉鸣
 */
public interface TeamBaseMapper extends BaseMapper<TeamBase> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<TeamMemberRelVO> selectTeamMemberPage(MPage<TeamBase> mPage,@Param("pageDTO") TeamBasePageDTO pageDTO);

    @InterceptorIgnore(tenantLine = "true")
    List<TeamMemberRelBO> getTeamMember(@Param("getDTO") TeamBaseGetDTO getDTO);
}

