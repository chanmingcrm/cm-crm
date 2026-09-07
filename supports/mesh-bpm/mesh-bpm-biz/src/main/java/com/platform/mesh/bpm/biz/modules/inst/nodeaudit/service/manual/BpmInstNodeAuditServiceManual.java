package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.manual;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.soa.node.audit.NodeAuditService;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.audit.factory.NodeAuditFactory;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.factory.NodeAuditDataFactory;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstNodeAuditServiceManual {


    @Autowired
    private NodeAuditFactory nodeAuditFactory;

    @Autowired
    private NodeAuditDataFactory nodeAuditDataFactory;

    @Autowired
    private RemoteMsgService remoteMsgService;

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;

    /**
     * 功能描述:
     * 〈校验当前审批节点是否通过〉
     * @param nodeAudits nodeAudits
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    public BpmInstNodePassBO checkInstNodeAuditPass(List<BpmInstNodeAudit> nodeAudits) {
        BpmInstNodePassBO nodeAuditBO = new BpmInstNodePassBO();
        if(CollUtil.isEmpty(nodeAudits)) {
            return nodeAuditBO;
        }
        //获取所有的审批值
        List<Integer> auditPass = nodeAudits.stream().map(BpmInstNodeAudit::getAuditPass).toList();
        //获取审批类型
        BpmInstNodeAudit nodeAudit = CollUtil.getFirst(nodeAudits);
        Integer auditFlag = nodeAudit.getAuditFlag();
        NodeAuditFlagEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditFlagEnum.class, auditFlag);
        //获取对应的服务
        NodeAuditService nodeAuditService = nodeAuditFactory.getNodeAuditService(enumByValue);
        //校验审批结果
        nodeAuditBO = nodeAuditService.check(auditPass);
        //如果审批不通过，则获取最新的不通过审批状态
        if(!nodeAuditBO.getCanPass()){
            List<BpmInstNodeAudit> unPassList = nodeAudits.stream().filter(audit -> {
                NodePassEnum nodePassEnum = BaseEnum.getEnumByValue(NodePassEnum.class, audit.getAuditPass());
                //使用code对比
                return nodePassEnum.getCode().equals(NodePassEnum.UN_PASS.getCode())
                        || nodePassEnum.getCode().equals(NodePassEnum.UN_VALID.getCode());
            }).toList();
            if(CollUtil.isNotEmpty(unPassList)) {
                nodeAuditBO.setAuditPass(CollUtil.getFirst(unPassList).getAuditPass());
            }
        }
        //流程记录最后审批状态
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        instProcessService.lambdaUpdate()
                .set(BpmInstProcess::getPassFlag,nodeAuditBO.getAuditPass())
                .eq(BpmInstProcess::getId,nodeAudit.getInstProcessId())
                .update();
        return nodeAuditBO;
    }

    /**
     * 功能描述:
     * 〈校验当前审批节点是否通过〉
     * @param nodeAudits nodeAudits
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    public List<BpmInstNodeAudit> getNodeCanAudit(List<BpmInstNodeAudit> nodeAudits, Long auditAccountId) {
        if(CollUtil.isEmpty(nodeAudits)) {
            return nodeAudits;
        }
        //获取审批类型
        BpmInstNodeAudit nodeAudit = CollUtil.getFirst(nodeAudits);
        NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, nodeAudit.getAuditDataType());
        NodeAuditDataService nodeAuditDataService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
        if(ObjectUtil.isEmpty(nodeAuditDataService)) {
            return CollUtil.newArrayList();
        }
        return nodeAuditDataService.getCanAuditNode(nodeAudits, auditAccountId);
    }

    /**
     * 功能描述:
     * 〈根据审批数据类型获取审批人信息〉
     * @param ids ids
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    public List<Long> getAuditDataIds(Integer auditDataType, List<Long> ids) {
        if(ObjectUtil.isEmpty(auditDataType)) {
            return CollUtil.newArrayList();
        }
        NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, auditDataType);
        NodeAuditDataService nodeAuditDataService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
        if(ObjectUtil.isEmpty(nodeAuditDataService)) {
            return CollUtil.newArrayList();
        }
        return nodeAuditDataService.getAuditDataIds(ids);
    }

    /**
     * 功能描述:
     * 〈获取流程节点〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    public BpmInstNode getInstNodeById(Long instNodeId) {
        IBpmInstNodeService bpmInstNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        return bpmInstNodeService.getById(instNodeId);
    }

    /**
     * 功能描述:
     * 〈给审批人发送消息〉
     * @param instNode instNode
     * @param auditDataIds auditDataIds
     * @author 蝉鸣
     */
    public void sendAuditMsg(BpmInstNode instNode, List<Long> auditDataIds) {
        if(!NodeRunEnum.RUNNING.getValue().equals(instNode.getRunFlag())){
            return;
        }
        //当前auditDataIds是组织的成员member_id,需要转化为user_id
        List<OrgMemberBO> relBOS = remoteOrgMemberService.getOrgMemberByIds(auditDataIds).getData();
        if (CollUtil.isEmpty(relBOS)) {
            return;
        }
        List<Long> userIds = relBOS.stream().map(OrgMemberBO::getUserId).distinct().toList();
        IBpmInstNodeService bpmInstNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        BpmInstNodeBO bpmInstNodeBO = bpmInstNodeService.getInstNodeData(instNode.getId());
        MsgBaseBO msgBaseBO = new MsgBaseBO();
        msgBaseBO.setModuleId(bpmInstNodeBO.getModuleId());
        msgBaseBO.setDataId(bpmInstNodeBO.getDataId());
        msgBaseBO.setMsgFlag(MsgFlagEnum.AUDIT_TODO.getValue());
        msgBaseBO.setMsgType(MsgTypeEnum.INIT.getValue());
        msgBaseBO.setMsgTitle(bpmInstNodeBO.getInstProcessName());
        msgBaseBO.setMsgBody(bpmInstNodeBO.getDataName());
        msgBaseBO.setMsgUserIds(userIds);
        remoteMsgService.sendMsg(msgBaseBO);
    }
}

