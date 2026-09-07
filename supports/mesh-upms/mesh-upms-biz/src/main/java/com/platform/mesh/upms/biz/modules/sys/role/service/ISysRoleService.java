package com.platform.mesh.upms.biz.modules.sys.role.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRoleDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRolePageDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 角色信息
 * @author 蝉鸣
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 功能描述:
     * 〈分页查询〉
     * @param pageEntity pageEntity
     * @return 正常返回:{@link MPage<SysRoleVO>}
     * @author 蝉鸣
     */
    PageVO<SysRoleVO> selectPage(SysRolePageDTO pageEntity);

    /**
     * 功能描述:
     * 〈通过用户ID查询用户角色信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysRole>}
     * @author 蝉鸣
     */
    List<SysRole> getRoleInfoByUserId(Long userId);

    /**
     * 功能描述:
     * 〈新增角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link SysRoleVO}
     * @author 蝉鸣
     */
    SysRoleVO addRole(SysRoleDTO roleDTO);

    /**
     * 功能描述:
     * 〈修改角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link SysRoleVO}
     * @author 蝉鸣
     */
    SysRoleVO editRole(SysRoleDTO roleDTO);

    /**
     * 功能描述:
     * 〈删除角色〉
     * @param roleId roleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteRole(Long roleId);

}

