package com.platform.mesh.upms.biz.modules.org.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.annotation.IgnoreDataScope;
import com.platform.mesh.mybatis.plus.extention.MPage;
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
     * 〈通过ids获取组织成员信息〉
     * @param userIds userIds
     * @return 正常返回:{@link List<OrgMember>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<OrgMember> getOrgMemberByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * 功能描述:
     * 〈根据用户ID查询所属成员信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysMemberVO>}
     * @author 蝉鸣
     */
    @IgnoreDataScope()
    List<SysMemberVO> getMemberVOByUserId(@Param("userId") Long userId);
}

