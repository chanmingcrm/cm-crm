package com.platform.mesh.upms.biz.modules.team.base.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.team.domain.bo.TeamMemberRelBO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseDelDTO;
import com.platform.mesh.upms.api.modules.team.domain.dto.TeamBaseGetDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBasePageDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.dto.TeamBaseShareDTO;
import com.platform.mesh.upms.biz.modules.team.base.domain.po.TeamBase;
import com.platform.mesh.upms.biz.modules.team.memberrel.domain.vo.TeamMemberRelVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 团队信息
 * @author 蝉鸣
 */
public interface ITeamBaseService extends IService<TeamBase> {

    /**
     * 功能描述:
     * 〈查询团队下成员分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<TeamMemberRelVO>}
     * @author 蝉鸣
     */
    MPage<TeamMemberRelVO> selectTeamMemberPage(TeamBasePageDTO pageDTO);

    /**
     * 功能描述:
     * 〈增加团队成员列表〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addTeamMember(TeamBaseDTO baseDTO);

    /**
     * 功能描述:
     * 〈共享团队成员〉
     * @param shareDTO shareDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean shareTeamMember(TeamBaseShareDTO shareDTO);

    /**
     * 功能描述:
     * 〈删除团队成员〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteTeamMember(TeamBaseDelDTO delDTO);

    /**
     * 功能描述:
     * 〈获取团队成员〉
     * @param getDTO getDTO
     * @return 正常返回:{@link List<TeamMemberRelBO>}
     * @author 蝉鸣
     */
    List<TeamMemberRelBO> getTeamMember(TeamBaseGetDTO getDTO);
}

