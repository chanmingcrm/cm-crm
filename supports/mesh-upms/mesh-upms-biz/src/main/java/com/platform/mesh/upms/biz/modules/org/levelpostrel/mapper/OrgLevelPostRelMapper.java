package com.platform.mesh.upms.biz.modules.org.levelpostrel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.dto.OrgLevelPostRelPageDTO;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.po.OrgLevelPostRel;
import com.platform.mesh.upms.biz.modules.org.levelpostrel.domain.vo.OrgLevelPostRelVO;
import org.apache.ibatis.annotations.Param;

/**
 * @description 组织岗位关系
 * @author 蝉鸣
 */
public interface OrgLevelPostRelMapper extends BaseMapper<OrgLevelPostRel> {

    MPage<OrgLevelPostRelVO> selectPageRel(MPage<OrgLevelPostRel> userMPage,@Param("pageDTO") OrgLevelPostRelPageDTO pageDTO);

    OrgLevelPostRelVO getLevelPostRelByPostId(@Param("postId") Long postId);
}

