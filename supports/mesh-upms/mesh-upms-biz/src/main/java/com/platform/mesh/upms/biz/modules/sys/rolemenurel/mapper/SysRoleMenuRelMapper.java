package com.platform.mesh.upms.biz.modules.sys.rolemenurel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.po.SysRoleMenuRel;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.vo.SysRoleMenuRelVO;
import org.apache.ibatis.annotations.Param;

/**
 * @description
 * @author 蝉鸣
 */
public interface SysRoleMenuRelMapper extends BaseMapper<SysRoleMenuRel> {

    MPage<SysRoleMenuRelVO> selectPageRel(MPage<SysRoleMenuRel> roleMPage, @Param("pageDTO") SysRoleMenuRelPageDTO pageDTO);
}

