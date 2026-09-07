package com.platform.mesh.gen.biz.modules.gen.grouprel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.dto.GenGroupRelPageDTO;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.po.GenGroupRel;
import com.platform.mesh.gen.biz.modules.gen.grouprel.domain.vo.GenGroupRelVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.apache.ibatis.annotations.Param;

public interface GenGroupRelMapper extends BaseMapper<GenGroupRel> {


    MPage<GenGroupRelVO> selectAllPage(IPage<GenGroupRel> mPage,@Param("pageDTO") GenGroupRelPageDTO pageDTO);
}
