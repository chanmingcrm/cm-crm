package com.platform.mesh.upms.biz.modules.team.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBasePageDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBaseShareDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.po.TeamBase;
import com.platform.mesh.upms.biz.modules.team.base.mapper.TeamBaseMapper;
import com.platform.mesh.upms.biz.modules.team.base.service.ITeamBaseService;
import com.platform.mesh.upms.biz.modules.team.base.service.manual.TeamBaseServiceManual;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.vo.TeamMemberRelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 团队信息
 * @author 蝉鸣
 */
@Service()
public class TeamBaseServiceImpl extends ServiceImpl<TeamBaseMapper, TeamBase> implements ITeamBaseService {

    @Autowired
    private TeamBaseServiceManual teamBaseServiceManual;

    /**
     * 功能描述:
     * 〈查询团队下成员分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<TeamMemberRelVO>}
     * @author 蝉鸣
     */
    @Override
    public MPage<TeamMemberRelVO> selectTeamMemberPage(TeamBasePageDTO pageDTO) {
        MPage<TeamBase> mPage = MPageUtil.pageEntityToMPage(pageDTO, TeamBase.class);
        if(YesOrNoEnum.NO.getValue().equals(pageDTO.getOpenFlag())){
            pageDTO.setUserId(UserCacheUtil.getUserId());
        }
        return this.getBaseMapper().selectTeamMemberPage(mPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean addTeamMember(TeamBaseDTO baseDTO) {
        TeamBase teamBase = BeanUtil.copyProperties(baseDTO, TeamBase.class);
        //保存团队信息
        this.saveOrUpdate(teamBase);
        //保存团队数据关联信息
        teamBaseServiceManual.saveTeamLink(teamBase.getId(),baseDTO.getLinkDTO());
        //保存团队成员以及角色信息
        teamBaseServiceManual.saveTeamMember(teamBase.getId(),baseDTO.getMemberRelDTOS());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈共享团队成员〉
     * @param shareDTO shareDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean shareTeamMember(TeamBaseShareDTO shareDTO) {
        teamBaseServiceManual.shareTeamMember(shareDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteTeamMember(TeamBaseDelDTO delDTO) {
        teamBaseServiceManual.deleteTeamMember(delDTO);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈获取团队成员〉
     * @param getDTO getDTO
     * @return 正常返回:{@link List<TeamMemberRelBO>}
     * @author 蝉鸣
     */
    @Override
    public List<TeamMemberRelBO> getTeamMember(TeamBaseGetDTO getDTO) {
        return this.getBaseMapper().getTeamMember(getDTO);
    }
}

