package com.platform.mesh.upms.biz.modules.sys.rolemenurel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.po.SysRoleMenuRel;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.vo.SysRoleMenuRelVO;
import com.platform.mesh.utils.result.Result;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 角色信息
 * @author 蝉鸣
 */
public interface ISysRoleMenuRelService extends IService<SysRoleMenuRel> {


    /**
     * 功能描述:
     * 〈获取角色菜单分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <MPage<SysRoleMenuRelVO>>}
     * @author 蝉鸣
     */
    MPage<SysRoleMenuRelVO> selectPage(SysRoleMenuRelPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈添加角色菜单关系〉
     * @param sysRoleMenuRelDTO sysRoleMenuRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addRoleMenu(SysRoleMenuRelDTO sysRoleMenuRelDTO);

    /**
     * 功能描述:
     * 〈删除角色菜单关系〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteRoleMenu(Long relId);

    /**
     * 功能描述:
     * 〈清楚不存在的菜单关系〉
     * @author 蝉鸣
     */
    void clearNullRoleMenuRel();
}

