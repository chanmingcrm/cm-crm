package com.platform.mesh.upms.biz.modules.org.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberDelDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberPageDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberTransDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberInfoVO;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.org.member.service.manual.OrgMemberServiceManual;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysMemberVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 成员信息
 * @author 蝉鸣
 */
public interface IOrgMemberService extends IService<OrgMember> {


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link OrgMemberServiceManual}
     * @author 蝉鸣
     */
    OrgMemberServiceManual getServiceManual();

    /***
     * 功能描述:
     * 〈成员列表查询〉
     * @param orgMemberPageDTO orgMemberPageDTO
     * @return 正常返回:{@link MPage<OrgMemberVO>}
     * @author 蝉鸣
     * @since 2024/9/6 19:46
     */
    MPage<OrgMemberVO> selectPage(OrgMemberPageDTO orgMemberPageDTO);

    /**
     * 功能描述:
     * 〈获取当前成员信息〉
     * @param memberId memberId
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    OrgMemberInfoVO getMemberInfoById(Long memberId);

    /**
     * 功能描述:
     * 〈添加成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    OrgMemberVO addMember(OrgMemberDTO memberDTO);

    /**
     * 功能描述:
     * 〈修改成员〉
     * @param memberDTO memberDTO
     * @return 正常返回:{@link OrgMemberVO}
     * @author 蝉鸣
     */
    OrgMemberVO editMember(OrgMemberDTO memberDTO);

    /**
     * 功能描述:
     * 〈删除成员〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteMember(OrgMemberDelDTO delDTO);

    /**
     * 功能描述:
     * 〈根据用户ID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    List<SysOrgBO> getMemberInfoByUserId(Long userId);

    /**
     * 功能描述:
     * 〈根据用户ID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    List<SysOrgBO> getMemberOrgByUserId(Long userId);

    /**
     * 功能描述:
     * 〈根据用户ID查询所属成员信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysMemberVO>}
     * @author 蝉鸣
     */
    List<SysMemberVO> getMemberVOByUserId(Long userId);

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param userIds userIds
     * @return 正常返回:{@link List<OrgMember>}
     * @author 蝉鸣
     */
    List<OrgMember> getOrgMemberByUserIds(List<Long> userIds);

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link List<OrgMemberBO>}
     * @author 蝉鸣
     */
    List<OrgMemberBO> getOrgMemberByIds(List<Long> memberIds);

    /**
     * 功能描述:
     * 〈根据成员名称获取组织下成员信息〉
     * @param memberName memberName
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    OrgMemberBO getOrgMemberByNameFirst(String memberName);

    /**
     * 功能描述:
     * 〈根据人员ID获取组织下成员信息〉
     * @param userId userId
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    OrgMemberBO getOrgMemberByUserIdFirst(Long userId);

    /**
     * 功能描述:
     * 〈转移成员下的数据〉
     * @param transDTO transDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean transMemberData(OrgMemberTransDTO transDTO);

    /**
     * 功能描述:
     * 〈查询当前人员的上级〉
     * @param id id
     * @return 正常返回:{@link List<OrgMemberVO>}
     * @author 蝉鸣
     */
    List<OrgMemberVO> getMemberLeadList(Long id);

    /**
     * 功能描述:
     * 〈获取直属上级〉
     * @param accountId accountId
     * @return 正常返回:{@link OrgMemberBO}
     * @author 蝉鸣
     */
    OrgMemberBO getLeaderDirect(Long accountId);

    /**
     * 功能描述:
     * 〈获取多层上级〉
     * @param accountId accountId
     * @return 正常返回:{@link List<OrgMemberBO>}
     * @author 蝉鸣
     */
    List<OrgMemberBO> getLeaderLoop(Long accountId);
}

