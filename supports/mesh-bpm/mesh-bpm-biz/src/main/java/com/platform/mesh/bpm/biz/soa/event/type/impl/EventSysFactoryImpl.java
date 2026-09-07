package com.platform.mesh.bpm.biz.soa.event.type.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.enums.InstEventHandleEnum;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.domain.po.BpmInstEventRel;
import com.platform.mesh.bpm.biz.modules.inst.eventrel.service.IBpmInstEventRelService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.soa.event.type.EventTypeService;
import com.platform.mesh.bpm.biz.soa.event.type.enums.EventTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.factory.NodeAuditDataFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberBO;
import com.platform.mesh.upms.api.modules.org.member.feign.RemoteOrgMemberService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSysFactoryImpl implements EventTypeService<BpmInstEvent> {

    private final static Logger log = LoggerFactory.getLogger(EventSysFactoryImpl.class);

    @Autowired
    private NodeAuditDataFactory nodeAuditDataFactory;

    @Autowired
    private RemoteMsgService remoteMsgService;

    @Autowired
    private RemoteOrgMemberService remoteOrgMemberService;

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link EventTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public EventTypeEnum eventType() {
        return EventTypeEnum.SYS;
    }

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param instEvent instEvent
     * @return 正常返回:{@link BpmInstEvent}
     * @author 蝉鸣
     */
    @Override
    public BpmInstEvent handle(BpmInstEvent instEvent) {
        //获取实例节点
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        BpmInstNodeBO bpmInstNodeBO = instNodeService.getInstNodeData(instEvent.getInstNodeId());
        if(ObjectUtil.isEmpty(bpmInstNodeBO.getModuleId())){
            return instEvent;
        }
        List<Long> sendUserIds = CollUtil.newArrayList();
        //获取审批人员
        List<Long> auditUserIds = this.getAuditUserIds(bpmInstNodeBO);
        sendUserIds.addAll(auditUserIds);
        //获取关联人员
        List<Long> relUserIds = this.getRelUserIds(instEvent);
        sendUserIds.addAll(relUserIds);
        //给目标人员发送站内信
        MsgBaseBO msgBaseBO = this.getBaseBO(bpmInstNodeBO, sendUserIds);
        remoteMsgService.sendMsg(msgBaseBO);
        //使用工具类避免循环服务依赖
        IBpmInstEventService instEventService = SpringContextHolderUtil.getBean(IBpmInstEventService.class);
        instEvent.setHandleFlag(InstEventHandleEnum.DONE.getValue());
        instEventService.updateById(instEvent);
        return instEvent;
    }


    /**
     * 功能描述:
     * 〈事件处理〉
     * @param instEvents instEvents
     * @author 蝉鸣
     */
    @Override
    public void handle(List<BpmInstEvent> instEvents) {
        log.info("发送钉钉通知");
    }


    /**
     * 功能描述:
     * 〈封装消息体〉
     * @param bpmInstNodeBO bpmInstNodeBO
     * @param auditUserIds auditUserIds
     * @return 正常返回:{@link MsgBaseBO}
     * @author 蝉鸣
     */
    private MsgBaseBO getBaseBO(BpmInstNodeBO bpmInstNodeBO, List<Long> auditUserIds) {
        MsgBaseBO msgBaseBO = new MsgBaseBO();
        msgBaseBO.setModuleId(bpmInstNodeBO.getModuleId());
        msgBaseBO.setDataId(bpmInstNodeBO.getDataId());
        msgBaseBO.setMsgFlag(MsgFlagEnum.AUDIT_TODO.getValue());
        msgBaseBO.setMsgType(MsgTypeEnum.INIT.getValue());
        msgBaseBO.setMsgTitle(bpmInstNodeBO.getInstProcessName());
        msgBaseBO.setMsgBody(bpmInstNodeBO.getDataName());
        msgBaseBO.setMsgUserIds(auditUserIds);
        return msgBaseBO;
    }


    /**
     * 功能描述:
     * 〈封装消息体〉
     * @param bpmInstNodeBO bpmInstNodeBO
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    private List<Long> getAuditUserIds(BpmInstNodeBO bpmInstNodeBO) {
        NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, bpmInstNodeBO.getAuditDataType());
        NodeAuditDataService nodeAuditService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
        if(ObjectUtil.isEmpty(nodeAuditService)){
            return CollUtil.newArrayList();
        }
        IBpmInstNodeAuditService instNodeAuditService = SpringContextHolderUtil.getBean(IBpmInstNodeAuditService.class);
        List<BpmInstNodeAudit> auditList = instNodeAuditService.lambdaQuery().eq(BpmInstNodeAudit::getInstNodeId, bpmInstNodeBO.getId()).list();
        List<Long> auditDataIds = auditList.stream().map(BpmInstNodeAudit::getAuditDataId).toList();
        if(CollUtil.isEmpty(auditDataIds)){
            return CollUtil.newArrayList();
        }
        return nodeAuditService.getAuditDataToUserIds(auditDataIds);
    }

    /**
     * 功能描述:
     * 〈封装消息体〉
     * @param instEvent instEvent
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    private List<Long> getRelUserIds(BpmInstEvent instEvent) {
        List<Long> relUserIds = CollUtil.newArrayList();
        IBpmInstEventRelService instEventRelService = SpringContextHolderUtil.getBean(IBpmInstEventRelService.class);
        List<BpmInstEventRel> eventRelList = instEventRelService.lambdaQuery()
                .eq(BpmInstEventRel::getInstEventId, instEvent.getId())
                .eq(BpmInstEventRel::getEventType, EventTypeEnum.SYS.getValue())
                .list();
        if(CollUtil.isEmpty(eventRelList)){
            return relUserIds;
        }
        List<Long> ids = eventRelList.stream().map(BpmInstEventRel::getRelDataId).map(Long::parseLong).distinct().toList();
        List<OrgMemberBO> relBOS = remoteOrgMemberService.getOrgMemberByIds(ids).getData();
        if(CollUtil.isEmpty(relBOS)){
            return relUserIds;
        }
        List<Long> userIds = relBOS.stream().map(OrgMemberBO::getUserId).filter(ObjectUtil::isNotEmpty).distinct().toList();
        relUserIds.addAll(userIds);
        return relUserIds;
    }
}
