package com.platform.mesh.upms.biz.modules.org.memberpostrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.dto.OrgMemberPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.po.OrgMemberPostRel;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberLevelVO;
import com.platform.mesh.upms.biz.modules.org.memberpostrel.domain.vo.OrgMemberPostRelPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * @description
 * @author 蝉鸣
 */
public interface OrgMemberPostRelMapper extends BaseMapper<OrgMemberPostRel> {

    MPage<OrgMemberLevelVO> selectLevelPage(MPage<OrgMemberLevelVO> levelMPage, @Param("accountId") Long accountId);

    MPage<OrgMemberPostRelPageVO> selectPageRel(MPage<OrgMemberPostRel> userMPage,@Param("pageDTO") OrgMemberPostRelPageDTO pageDTO);

}

