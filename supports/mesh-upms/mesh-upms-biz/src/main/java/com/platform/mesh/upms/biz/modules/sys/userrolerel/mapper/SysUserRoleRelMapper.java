package com.platform.mesh.upms.biz.modules.sys.userrolerel.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.vo.SysUserRoleRelVO;
import org.apache.ibatis.annotations.Param;

/**
 * @description 用户信息Mapper
 * @author 蝉鸣
 */
public interface SysUserRoleRelMapper extends BaseMapper<SysUserRoleRel> {


    @InterceptorIgnore(tenantLine = "true")
    MPage<SysUserRoleRelVO> selectPageRel(MPage<SysUserRoleRel> userMPage, @Param("pageDTO") SysUserRoleRelPageDTO pageDTO);

}
