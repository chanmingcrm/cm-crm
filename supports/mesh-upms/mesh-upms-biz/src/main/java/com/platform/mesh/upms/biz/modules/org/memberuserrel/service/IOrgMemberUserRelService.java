package com.platform.mesh.upms.biz.modules.org.memberuserrel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.dto.OrgMemberAddDTO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 成员信息
 * @author 蝉鸣
 */
public interface IOrgMemberUserRelService extends IService<OrgMemberUserRel> {

    /**
     * 功能描述:
     * 〈添加成员-用户关系〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addMemberUser(OrgMemberAddDTO addDTO);

    /**
     * 功能描述:
     * 〈删除成员-用户关系〉
     * @param memberId memberId
     * @author 蝉鸣
     */
    void deleteMemberUserByMemberId(Long memberId);

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param userId userId
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    OrgMemberRelBO getOrgMemberUserDefaultRelByUserId(Long userId);

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberId memberId
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    OrgMemberRelBO getOrgMemberUserDefaultRelByMemberId(Long memberId);

    /**
     * 功能描述:
     * 〈获取成员默认关联信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link OrgMemberRelBO}
     * @author 蝉鸣
     */
    List<OrgMemberRelBO> getOrgMemberUserDefaultRelByIds(List<Long> memberIds);

    /**
     * 功能描述:
     * 〈获取当前账户下的下属部门〉
     * @param accountId accountId
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<Long> getOrgChildLevelByAccountId(Long accountId);

    /**
     * 功能描述:
     * 〈获取当前部门下所有的下属人员〉
     * @param levelIds levelIds
     * @return 正常返回:{@link List<OrgMemberRelBO>}
     * @author 蝉鸣
     */
    List<OrgMemberRelBO> getOrgChildUserRelByLevelIds(List<Long> levelIds);
}

