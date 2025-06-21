package com.platform.mesh.upms.biz.modules.sys.userrolerel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.dto.SysUserRoleRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.vo.SysUserRoleRelVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 用户信息
 * @author 蝉鸣
 */
public interface ISysUserRoleRelService extends IService<SysUserRoleRel> {


    /**
     * 功能描述:
     * 〈根据条件分页查询用户列表〉
     * @param pageEntity pageEntity
     * @return 正常返回:{@link MPage <SysUserRoleRelVO>}
     * @author 蝉鸣
     */
    MPage<SysUserRoleRelVO> selectPage(SysUserRoleRelPageDTO pageEntity);

    /**
     * 功能描述:
     * 〈添加用户角色关系〉
     * @param sysUserRoleRelDTO sysUserRoleRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addUserRole(SysUserRoleRelDTO sysUserRoleRelDTO);

    /**
     * 功能描述:
     * 〈删除用户角色关系〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteUserRole(Long relId);

}
