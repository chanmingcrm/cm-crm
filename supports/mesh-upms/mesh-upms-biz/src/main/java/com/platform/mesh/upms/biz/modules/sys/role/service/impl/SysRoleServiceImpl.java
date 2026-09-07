package com.platform.mesh.upms.biz.modules.sys.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;

import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRoleDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.dto.SysRolePageDTO;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.role.domain.vo.SysRoleVO;
import com.platform.mesh.upms.biz.modules.sys.role.exception.RoleExceptionEnum;
import com.platform.mesh.upms.biz.modules.sys.role.mapper.SysRoleMapper;
import com.platform.mesh.upms.biz.modules.sys.role.service.ISysRoleService;
import com.platform.mesh.upms.biz.modules.sys.role.service.manual.SysRoleServiceManual;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 角色信息
 * @author 蝉鸣
 */
@Service()
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    @Autowired
    private SysRoleServiceManual sysRoleServiceManual;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 功能描述:
     * 〈根据条件分页查询角色列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<SysRoleVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<SysRoleVO> selectPage(SysRolePageDTO pageDTO) {
        MPage<SysRole> userMPage = MPageUtil.pageEntityToMPage(pageDTO, SysRole.class);
        MPage<SysRole> userPage = this.getBaseMapper().selectMPage(userMPage,pageDTO);
        //封装角色信息
        return MPageUtil.convertToVO(userPage, SysRoleVO.class);
    }

    /**
     * 功能描述:
     * 〈通过账户ID查询用户角色信息〉
     * @param userId userId
     * @return 正常返回:{@link List<SysRole>}
     * @author 蝉鸣
     */
    @Override
    public List<SysRole> getRoleInfoByUserId(Long userId) {
        //查询当前用户下关联的角色
        return this.getBaseMapper().getRoleInfoByUserId(userId,YesOrNoEnum.YES.getValue());
    }

    /**
     * 功能描述:
     * 〈添加角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link SysRoleVO}
     * @author 蝉鸣
     */
    @Override
    public SysRoleVO addRole(SysRoleDTO roleDTO) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(roleDTO, sysRole, ObjFieldUtil.ignoreDefault());
        sysRole.setActFlag(YesOrNoEnum.YES.getValue());
        sysRole.setDelFlag(YesOrNoEnum.YES.getValue());
        sysRole.setInitFlag(YesOrNoEnum.NO.getValue());
        this.save(sysRole);
        return BeanUtil.copyProperties(sysRole, SysRoleVO.class);
    }

    /**
     * 功能描述:
     * 〈修改角色〉
     * @param roleDTO roleDTO
     * @return 正常返回:{@link SysRoleVO}
     * @author 蝉鸣
     */
    @Override
    public SysRoleVO editRole(SysRoleDTO roleDTO) {
        SysRole sysRole = BeanUtil.copyProperties(roleDTO, SysRole.class);
        this.updateById(sysRole);
        return BeanUtil.copyProperties(sysRole, SysRoleVO.class);
    }

    /**
     * 功能描述:
     * 〈删除角色〉
     * @param roleId roleId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean deleteRole(Long roleId) {
        SysRole sysRole = getById(roleId);
        if(ObjectUtil.isEmpty(sysRole)) {
            throw RoleExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        if (YesOrNoEnum.INIT.getValue().equals(sysRole.getInitFlag())
                || YesOrNoEnum.YES.getValue().equals(sysRole.getInitFlag())) {
            throw RoleExceptionEnum.ADD_DELETE_INIT_ERROR.getBaseException();
        }
        // 清空userinfo缓存
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_ROLE_DETAILS)).clear();
        sysRole.setDelFlag(YesOrNoEnum.NO.getValue());
        this.updateById(sysRole);
        return Boolean.TRUE;
    }

}

