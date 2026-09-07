package com.platform.mesh.bpm.biz.soa.node.auditdata.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.domain.vo.NodeAuditDataVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
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
public class NodeAuditDataUserLeaderFactoryImpl implements NodeAuditDataService {

    private final static Logger log = LoggerFactory.getLogger(NodeAuditDataUserLeaderFactoryImpl.class);

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;

    /**
     * 功能描述:
     * 〈节点城里人类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeAuditDataTypeEnum nodeAuditData() {
        return NodeAuditDataTypeEnum.USER_LEADER;
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
        Long accountId = UserCacheUtil.getAccountId();
        OrgMemberBO orgMemberBO = remoteOrgMemberService.getLeaderDirect(accountId).getData();
        //获取当前人员的直属领导
        if(ObjectUtil.isEmpty(orgMemberBO)||ObjectUtil.isEmpty(orgMemberBO.getId())){
            return CollUtil.newArrayList();
        }
        return CollUtil.newArrayList(orgMemberBO.getId());
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
        Long accountId = UserCacheUtil.getAccountId();
        OrgMemberBO orgMemberBO = remoteOrgMemberService.getLeaderDirect(accountId).getData();
        //获取当前人员的直属领导
        return CollUtil.newArrayList(orgMemberBO.getUserId());
    }

    /**
     * 功能描述:
     * 〈获取审批数据范围〉
     * @param processTodoBO processTodoBO
     * @author 蝉鸣
     */
    public void getAuditDataScopeBO(BpmInstProcessTodoBO processTodoBO){
        List<Integer> userTypes = processTodoBO.getUserTypes();
        //增加当前支持类型
        userTypes.add(this.nodeAuditData().getValue());
        Set<Long> userIds = processTodoBO.getUserIds();
        //增加当前人员成员ID
        SysAccountBO sysAccountBO = UserCacheUtil.getAccountInfoCache(processTodoBO.getAccountId());
        Result<List<OrgMemberBO>> memberByUserIds = remoteOrgMemberService.getOrgMemberByUserIds(CollUtil.newArrayList(sysAccountBO.getUserId()));
        Optional<List<OrgMemberBO>> orgMemberBOS = ResultUtil.of(memberByUserIds).getData();
        if(orgMemberBOS.isEmpty()){
            return;
        }
        List<Long> ids = orgMemberBOS.get().stream().map(OrgMemberBO::getId).distinct().toList();
        userIds.addAll(ids);
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
        //根据人员ID获取人员对象
        Result<List<OrgMemberBO>> orgMemberByIds = remoteOrgMemberService.getOrgMemberByIds(auditDataIds);
        Optional<List<OrgMemberBO>> orgMemberBOS = ResultUtil.of(orgMemberByIds).getData();
        if(orgMemberBOS.isEmpty()){
            return CollUtil.newArrayList();
        }
        List<NodeAuditDataVO> auditDataVOS = CollUtil.newArrayList();
        orgMemberBOS.get().forEach(orgMemberBO -> {
            NodeAuditDataVO nodeAuditDataVO = new NodeAuditDataVO();
            nodeAuditDataVO.setAuditDataId(orgMemberBO.getId());
            nodeAuditDataVO.setAuditDataName(orgMemberBO.getMemberName());
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
        //增加当前人员组织ID
        SysAccountBO sysAccountBO = UserCacheUtil.getAccountInfoCache(accountId);
        //根据用户ID获取成员信息
        Result<List<OrgMemberBO>> memberByUserIds = remoteOrgMemberService.getOrgMemberByUserIds(CollUtil.newArrayList(sysAccountBO.getUserId()));
        Optional<List<OrgMemberBO>> orgMemberBOS = ResultUtil.of(memberByUserIds).getData();
        List<Long> ids = orgMemberBOS.map(memberBOS -> memberBOS.stream().map(OrgMemberBO::getId).distinct().toList()).orElseGet(CollUtil::newArrayList);
        //遍历节点设置节点权限信息
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
        //增加当前人员组织ID
        SysAccountBO sysAccountBO = UserCacheUtil.getAccountInfoCache(accountId);
        //根据用户ID获取成员信息
        Result<List<OrgMemberBO>> memberByUserIds = remoteOrgMemberService.getOrgMemberByUserIds(CollUtil.newArrayList(sysAccountBO.getUserId()));
        Optional<List<OrgMemberBO>> orgMemberBOS = ResultUtil.of(memberByUserIds).getData();
        List<Long> ids = orgMemberBOS.map(memberBOS -> memberBOS.stream().map(OrgMemberBO::getId).distinct().toList()).orElseGet(CollUtil::newArrayList);
        //如果以人维度审批，则通过levelId 代表auditDataId
        return nodeAudits.stream().filter(audit -> ids.contains(audit.getAuditDataId())).toList();
    }

}
