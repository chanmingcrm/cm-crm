package com.platform.mesh.app.biz.modules.app.modulesettranspick.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 模块分配设置
 * @author 蝉鸣
 */
@Service
public class AppModuleSetTransPickServiceManual {

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;


    /**
     * 功能描述:
     * 〈获取成员人员关系〉
     * @param memberIds memberIds
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<Long, Long> getMemberUserRelByIds(List<Long> memberIds) {
        if(CollUtil.isEmpty(memberIds)){
            return new HashMap<>();
        }
        List<OrgMemberBO> memberBOS = remoteOrgMemberService.getOrgMemberByIds(memberIds).getData();
        if(CollUtil.isEmpty(memberBOS)){
            return new HashMap<>();
        }
        return memberBOS.stream()
                .filter(relBO -> ObjectUtil.isNotEmpty(relBO.getUserId()))
                .collect(Collectors.toMap(OrgMemberBO::getId, OrgMemberBO::getUserId));
    }
}