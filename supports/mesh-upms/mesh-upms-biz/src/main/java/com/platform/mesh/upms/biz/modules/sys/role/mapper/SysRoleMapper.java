package com.platform.mesh.upms.biz.modules.sys.role.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRolePageDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description
 * @author 蝉鸣
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 功能描述:
     * 〈通过用户ID查询用户角色信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysRole>}
     * @author 蝉鸣
     */
    @InterceptorIgnore(tenantLine = "true")
    List<SysRole> getRoleInfoByUserId(@Param("userId") Long userId, @Param("delFlag") Integer delFlag);

    @InterceptorIgnore(tenantLine = "true")
    MPage<SysRole> selectMPage(MPage<SysRole> userMPage, @Param("pageDTO") SysRolePageDTO pageDTO);
}

