package com.platform.mesh.upms.biz.modules.sys.role.service.manual;


import cn.hutool.core.util.IdUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.handler.DataMetaObjectHandler;
import com.platform.mesh.upms.biz.modules.sys.role.domain.po.SysRole;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.service.ISysUserRoleRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class SysRoleServiceManual {

    @Autowired
    private ISysUserRoleRelService sysUserRoleService;

    /**
     * 功能描述:
     * 〈初始化租户角色〉
     * @param userId userId
     * @param roles roles
     * @author 蝉鸣
     */
    @Transactional(rollbackFor = Exception.class)
    public void initTenantRole(Long userId,List<SysRole> roles) {
        List<SysUserRoleRel> relList = roles.stream().map(role -> {
            SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
            sysUserRoleRel.setId(IdUtil.getSnowflake().nextId());
            sysUserRoleRel.setRoleId(role.getId());
            sysUserRoleRel.setUserId(userId);
            sysUserRoleRel.setInitFlag(YesOrNoEnum.YES.getValue());
            sysUserRoleRel.setCreateUserId(NumberConst.NUM_0.longValue());
            sysUserRoleRel.setCreateTime(LocalDateTime.now());
            return sysUserRoleRel;
        }).toList();
        DataMetaObjectHandler.setEnableDataMeta(Boolean.FALSE);
        sysUserRoleService.saveBatch(relList);
        DataMetaObjectHandler.unEnableDataMeta();
    }
}

