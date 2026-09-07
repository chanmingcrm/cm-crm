package com.platform.mesh.upms.biz.modules.org.member.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberTransBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysOrgBO;
import com.platform.mesh.upms.biz.modules.org.member.domain.dto.OrgMemberPageDTO;
import com.platform.mesh.upms.biz.modules.org.member.domain.po.OrgMember;
import com.platform.mesh.upms.biz.modules.org.member.domain.vo.OrgMemberVO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.vo.SysMemberVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 成员信息
 * @author 蝉鸣
 */
public interface OrgMemberMapper extends BaseMapper<OrgMember> {


    /***
     * 功能描述:
     * 〈成员列表查询〉
     * @param orgMemberPageDTO orgMemberPageDTO
     * @return 正常返回:{@link MPage<OrgMemberVO>}
     * @author 蝉鸣
     * @since 2024/9/6 19:46
     */
    MPage<OrgMemberVO> selectMemberPage(MPage<OrgMember> mPage,@Param("orgMemberPageDTO") OrgMemberPageDTO orgMemberPageDTO);

    /**
     * 功能描述:
     * 〈根据成员ID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<SysOrgBO> getMemberInfoByUserId(@Param("userId") Long userId);

    /**
     * 功能描述:
     * 〈根据成员ID查询所属组织信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysOrgBO>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<SysOrgBO> getMemberOrgByUserId(@Param("userId") Long userId);

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param userIds userIds
     * @return 正常返回:{@link List<OrgMember>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<OrgMember> getOrgMemberByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * 功能描述:
     * 〈通过ids获取组织成员信息〉
     * @param memberIds memberIds
     * @return 正常返回:{@link List<OrgMember>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<OrgMemberBO> getOrgMemberByIds(@Param("memberIds") List<Long> memberIds);

    /**
     * 功能描述:
     * 〈根据用户ID查询所属成员信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysMemberVO>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<SysMemberVO> getMemberVOByUserId(@Param("userId") Long userId);

    /**
     * 功能描述:
     * 〈根据成员名称获取组织下成员信息〉
     * @param memberName memberName
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    OrgMemberBO getOrgMemberByNameFirst(@Param("memberName") String memberName);

    /**
     * 功能描述:
     * 〈根据用户ID获取组织下成员信息〉
     * @param userId userId
     * @return 正常返回:{@link OrgMember}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    OrgMemberBO getOrgMemberByUserIdFirst(@Param("userId") Long userId);

    /**
     * 功能描述:
     * 〈根据成员信息〉
     * @param targetUserId targetUserId
     * @return 正常返回:{@link OrgMemberTransBO}
     * @author 蝉鸣
     */
    OrgMemberTransBO getTransMember(@Param("targetUserId") Long targetUserId);

    /**
     * 功能描述:
     * 〈查询上级部门领导〉
     * @param levelIds levelIds
     * @param leadFlag leadFlag
     * @return 正常返回:{@link List<OrgMemberVO>}
     * @author 蝉鸣
     */
    List<OrgMemberVO> getMemberLeadList(@Param("levelIds") List<Long> levelIds,@Param("leadFlag") Integer leadFlag);

    /**
     * 功能描述:
     * 〈查询直接上级〉
     * @param accountId accountId
     * @param leadFlag leadFlag
     * @return 正常返回:{@link OrgMemberBO}
     * @author 蝉鸣
     */
    OrgMemberBO getLeaderDirect(@Param("accountId") Long accountId,@Param("leadFlag") Integer leadFlag);

    /**
     * 功能描述:
     * 〈查询直接上级〉
     * @param levelIds levelIds
     * @param leadFlag leadFlag
     * @return 正常返回:{@link List<OrgMemberBO>}
     * @author 蝉鸣
     */
    List<OrgMemberBO> getLeaderLoop(@Param("levelIds") List<Long> levelIds,@Param("leadFlag") Integer leadFlag);
}

