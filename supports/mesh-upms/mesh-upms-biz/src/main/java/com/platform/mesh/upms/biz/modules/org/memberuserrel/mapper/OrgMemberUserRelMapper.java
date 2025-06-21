package com.platform.mesh.upms.biz.modules.org.memberuserrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.biz.modules.org.memberuserrel.domain.po.OrgMemberUserRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 成员用户关系
 * @author 蝉鸣
 */
public interface OrgMemberUserRelMapper extends BaseMapper<OrgMemberUserRel> {

    OrgMemberRelBO getDefaultRelByUserId(@Param("userId") Long userId);

    OrgMemberRelBO getDefaultRelByMemberId(@Param("memberId") Long memberId);

    List<OrgMemberRelBO> getDefaultRelByMemberIds(@Param("memberIds") List<Long> memberIds);

    List<Long> getOrgChildLevelRelByAccountId(@Param("userId") Long userId,@Param("leadFlag") Integer leadFlag);

    List<OrgMemberRelBO> getOrgChildUserRelByLevelIds(@Param("levelIds") List<Long> list);
}

