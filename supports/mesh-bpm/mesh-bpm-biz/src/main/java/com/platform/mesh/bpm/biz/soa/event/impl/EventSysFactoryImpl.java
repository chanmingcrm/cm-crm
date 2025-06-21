package com.platform.mesh.bpm.biz.soa.event.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.enums.InstEventHandleEnum;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.soa.event.EventService;
import com.platform.mesh.bpm.biz.soa.event.enums.EventTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.factory.NodeAuditDataFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.api.modules.msg.enums.MsgFlagEnum;
import com.platform.mesh.upms.api.modules.msg.enums.MsgTypeEnum;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSysFactoryImpl implements EventService<BpmInstEvent> {

    private final static Logger log = LoggerFactory.getLogger(EventSysFactoryImpl.class);

    @Autowired
    private NodeAuditDataFactory nodeAuditDataFactory;

    @Autowired
    private RemoteMsgService remoteMsgService;

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
        NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, bpmInstNodeBO.getAuditDataType());
        NodeAuditDataService nodeAuditService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
        if(ObjectUtil.isNotEmpty(nodeAuditService)){
            IBpmInstNodeAuditService instNodeAuditService = SpringContextHolderUtil.getBean(IBpmInstNodeAuditService.class);
            List<BpmInstNodeAudit> auditList = instNodeAuditService.lambdaQuery().eq(BpmInstNodeAudit::getInstNodeId, instEvent.getInstNodeId()).list();
            List<Long> auditDataIds = auditList.stream().map(BpmInstNodeAudit::getAuditDataId).toList();
            if(CollUtil.isNotEmpty(auditDataIds)){
                List<Long> auditUserIds = nodeAuditService.getAuditDataToUserIds(auditDataIds);
                //给目标人员发送站内信
                MsgBaseBO msgBaseBO = this.getBaseBO(bpmInstNodeBO, auditUserIds);
                remoteMsgService.sendMsg(msgBaseBO);
            }
        }
        //使用工具类避免循环服务依赖
        IBpmInstEventService instEventService = SpringContextHolderUtil.getBean(IBpmInstEventService.class);
        instEvent.setHandleFlag(InstEventHandleEnum.DONE.getValue());
        instEventService.updateById(instEvent);
        return instEvent;
    }

    /**
     * 功能描述:
     * 〈封装消息体〉
     * @param bpmInstNodeBO bpmInstNodeBO
     * @param auditUserIds auditUserIds
     * @return 正常返回:{@link MsgBaseBO}
     * @author 蝉鸣
     */
    @NotNull
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
     * 〈事件处理〉
     * @param instEvents instEvents
     * @author 蝉鸣
     */
    @Override
    public void handle(List<BpmInstEvent> instEvents) {
        log.info("发送钉钉通知");
    }

}
