package com.platform.mesh.upms.biz.modules.sys.rolemenurel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.dto.SysRoleMenuRelPageDTO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.po.SysRoleMenuRel;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.domain.vo.SysRoleMenuRelVO;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.exception.RoleMenuRelExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.mapper.SysRoleMenuRelMapper;
import com.platform.mesh.upms.biz.modules.sys.rolemenurel.service.ISysRoleMenuRelService;
import com.platform.mesh.utils.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 角色信息
 * @author 蝉鸣
 */
@Service()
public class SysRoleMenuRelServiceImpl extends ServiceImpl<SysRoleMenuRelMapper, SysRoleMenuRel> implements ISysRoleMenuRelService {

    /**
     * 功能描述:
     * 〈获取角色菜单分页〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result <MPage<SysRoleMenuRelVO>>}
     * @author 蝉鸣
     */
    @Override
    public MPage<SysRoleMenuRelVO> selectPage(SysRoleMenuRelPageDTO pageDTO) {
        if(ObjectUtil.isEmpty(pageDTO.getRoleId())){
            throw RoleMenuRelExceptionEnum.ADD_NO_ARGS.getBaseException();
        }
        MPage<SysRoleMenuRel> roleMPage = MPageUtil.pageEntityToMPage(pageDTO, SysRoleMenuRel.class);
        return this.getBaseMapper().selectPageRel(roleMPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈添加角色菜单〉
     * @param sysRoleMenuRelDTO sysRoleMenuRelDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addRoleMenu(SysRoleMenuRelDTO sysRoleMenuRelDTO) {
        if(ObjectUtil.isEmpty(sysRoleMenuRelDTO.getRootMenuId())){
            return Boolean.FALSE;
        }
        //删除旧的
        this.lambdaUpdate()
                .eq(SysRoleMenuRel::getRoleId, sysRoleMenuRelDTO.getRoleId())
                .in(SysRoleMenuRel::getRootMenuId, sysRoleMenuRelDTO.getRootMenuId())
                .remove();
        List<SysRoleMenuRel> list = CollUtil.newArrayList();
        for (Long menuId : sysRoleMenuRelDTO.getMenuIds()) {
            SysRoleMenuRel orgLevelPostRel = BeanUtil.copyProperties(sysRoleMenuRelDTO, SysRoleMenuRel.class);
            orgLevelPostRel.setMenuId(menuId);
            list.add(orgLevelPostRel);
        }
        saveBatch(list);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除角色菜单〉
     * @param relId relId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteRoleMenu(Long relId) {
        removeById(relId);
        return Boolean.TRUE;
    }
}

