package com.platform.mesh.upms.biz.modules.sys.userrolerel.service.manual;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.upms.biz.modules.sys.userrolerel.domain.po.SysUserRoleRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class SysUserRoleRelServiceManual {

	private static final Logger log = LoggerFactory.getLogger(SysUserRoleRelServiceManual.class);

    /**
     * 功能描述:
     * 〈获取需要保护的角色ID〉
     * @param initRel initRel
     * @param userId userId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    public List<Long> getIgnoreRoleIds(List<SysUserRoleRel> initRel, Long userId) {
        List<Long> ignoreIds = List.of();
        if(CollUtil.isEmpty(initRel)){
            return ignoreIds;
        }
        //拥有初始化角色人员
        List<Long> userIds = initRel.stream().map(SysUserRoleRel::getUserId).distinct().toList();
        //当前人员拥有的初始化角色
        List<Long> userRoleIds  = initRel.stream()
                .filter(rel -> rel.getUserId().equals(userId))
                .map(SysUserRoleRel::getRoleId).distinct().toList();
        //至少保留一位人员具有初始化角色
        if(userIds.size() <= NumberConst.NUM_1 && userIds.contains(userId)){
            return userRoleIds;
        }
        //其他人员初始化角色ID
        List<Long> otherRoleIds = initRel.stream()
                .filter(rel -> !rel.getUserId().equals(userId))
                .map(SysUserRoleRel::getRoleId).toList();
        //当前人员的初始化角色其他人员没有的角色ID
        return userRoleIds.stream()
                .filter(roleId -> !otherRoleIds.contains(roleId)).distinct().toList();
    }
}
