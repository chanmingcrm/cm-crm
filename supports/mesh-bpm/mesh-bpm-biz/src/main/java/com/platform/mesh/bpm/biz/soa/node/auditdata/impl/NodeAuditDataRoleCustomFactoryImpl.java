package com.platform.mesh.bpm.biz.soa.node.auditdata.impl;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.domain.vo.NodeAuditDataVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysRoleBO;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @description 定时节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeAuditDataRoleCustomFactoryImpl implements NodeAuditDataService {

    private final static Logger log = LoggerFactory.getLogger(NodeAuditDataRoleCustomFactoryImpl.class);

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 功能描述:
     * 〈节点城里人类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeAuditDataTypeEnum nodeAuditData() {
        return NodeAuditDataTypeEnum.ROLE_CUSTOM;
    }

    /**
     * 功能描述:
     * 〈获取节点审批人员Ids〉
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<Long> getAuditDataIds(List<Long> auditDataIds) {
        return auditDataIds;
    }

    /**
     * 功能描述:
     * 〈获取节点审批人员Ids〉
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<Long> getAuditDataToUserIds(List<Long> auditDataIds) {
        return auditDataIds;
    }

    /**
     * 功能描述:
     * 〈获取审批数据范围〉
     * @param processTodoBO processTodoBO
     * @author 蝉鸣
     */
    public void getAuditDataScopeBO(BpmInstProcessTodoBO processTodoBO){
        List<Integer> roleTypes = processTodoBO.getRoleTypes();
        //增加当前支持类型
        roleTypes.add(this.nodeAuditData().getValue());
        Set<Long> roleIds = processTodoBO.getRoleIds();
        //增加当前人员角色ID
        List<SysRoleBO> sysRoleBOS = UserCacheUtil.getAccountRoleCache(processTodoBO.getAccountId());
        List<Long> ids = sysRoleBOS.stream().map(SysRoleBO::getId).distinct().toList();
        roleIds.addAll(ids);
    }

    /**
     * 功能描述:
     * 〈节点处理VO对象〉
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    @Override
    public List<NodeAuditDataVO> getAuditDataVO(List<Long> auditDataIds){
        if(CollUtil.isEmpty(auditDataIds)){
            return CollUtil.newArrayList();
        }
        Result<List<SysRoleBO>>  sysRoleByIds= remoteUserService.getRoleByIds(auditDataIds);
        Optional<List<SysRoleBO>> sysRoleBOS = ResultUtil.of(sysRoleByIds).getData();
        if(sysRoleBOS.isEmpty()){
            return CollUtil.newArrayList();
        }
        //根据人员ID获取人员对象
        List<NodeAuditDataVO> auditDataVOS = CollUtil.newArrayList();
        sysRoleBOS.get().forEach(sysRoleBO -> {
            NodeAuditDataVO nodeAuditDataVO = new NodeAuditDataVO();
            nodeAuditDataVO.setAuditDataId(sysRoleBO.getId());
            nodeAuditDataVO.setAuditDataName(sysRoleBO.getRoleName());
            auditDataVOS.add(nodeAuditDataVO);
        });
        return auditDataVOS;
    }

    /**
     * 功能描述:
     * 〈获取当前账户节点处理权限〉
     * @param auditDataIds auditDataIds
     * @param accountId accountId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean getCanAudit(List<Long> auditDataIds, Long accountId){
        //增加当前人员角色ID
        List<SysRoleBO> sysRoleBOS = UserCacheUtil.getAccountRoleCache(accountId);
        List<Long> ids = sysRoleBOS.stream().map(SysRoleBO::getId).distinct().toList();
        return CollUtil.containsAny(ids, auditDataIds);
    }


    /**
     * 功能描述:
     * 〈获取可以审批的节点〉
     * @param nodeAudits nodeAudits
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstNodeAudit>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNodeAudit> getCanAuditNode(List<BpmInstNodeAudit> nodeAudits, Long accountId){
        //增加当前人员角色ID
        List<SysRoleBO> sysRoleBOS = UserCacheUtil.getAccountRoleCache(accountId);
        List<Long> ids = sysRoleBOS.stream().map(SysRoleBO::getId).distinct().toList();
        //如果以人维度审批，则通过levelId 代表auditDataId
        return nodeAudits.stream().filter(audit -> ids.contains(audit.getAuditDataId())).toList();
    }
}
