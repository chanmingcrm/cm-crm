package com.platform.mesh.upms.biz.modules.sys.user.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.user.domain.dto.SysUserPageDTO;
import com.platform.mesh.upms.biz.modules.sys.user.domain.po.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * @description 用户信息Mapper
 * @author 蝉鸣
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @InterceptorIgnore(tenantLine = "true")
    MPage<SysUser> selectMPage(MPage<SysUser> mPage,@Param("pageDTO") SysUserPageDTO pageDTO);
}
