package com.platform.mesh.bpm.biz.soa.node.type.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.run.factory.NodeRunFactory;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 审批节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeTypeAuditFactoryImpl implements NodeTypeService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeTypeAuditFactoryImpl.class);

    @Autowired
    private NodeRunFactory<BpmInstNode> nodeRunFactory;

    @Autowired
    private IBpmInstNodeAuditService bpmInstNodeAuditService;

    /**
     * 功能描述:
     * 〈节点类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.AUDIT_NODE;
    }

    /**
     * 功能描述:
     * 〈单节点处理〉
     * @param instNode instNode
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode handle(BpmInstNode instNode) {
        //使用此种方式避免循环依赖
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        IBpmInstNodeSubService instNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        //如果节点不是结束状态则需要执行进入节点和运行中的逻辑，避免节点进入节点，多次执行加载和运行逻辑
        if( !instNode.getRunFlag().equals(NodeRunEnum.END.getValue())){
            //重置当前节点下的变量，防止根据变量自动执行
            instNodeService.clearNodeVar(instNode);
            //执行载入节点逻辑
            nodeRunFactory.getNodeRunService(NodeRunEnum.STAND).handle(instNode);
            //节点节点运行中逻辑
            instNode.setRunFlag(NodeRunEnum.RUNNING.getValue());
            instNodeService.updateById(instNode);
            //执行运行节点标识
            nodeRunFactory.getNodeRunService(NodeRunEnum.RUNNING).handle(instNode);
            //执行节点子项
            instNodeSubService.runNodeSub(instNode);
        }
        //判断节点是否可以通过，如果通过设置节点结束状态
        List<BpmInstNode> bpmInstNodes = instNodeService.selectNextNode(CollUtil.newArrayList(instNode));
        if(CollUtil.isNotEmpty(bpmInstNodes)){
            //如果审批节点存在可执行下级节点则视为当前节点可以通过
            instNode.setRunFlag(NodeRunEnum.END.getValue());
            if(ObjectUtil.isEmpty(instNode.getOutFlag())){
                instNode.setOutFlag(InstNodeOutEnum.AUTO_OUT_VAR.getValue());
            }
            if(ObjectUtil.isEmpty(instNode.getPassFlag())){
                instNode.setPassFlag(NodePassEnum.PASS.getValue());
            }
            instNodeService.updateById(instNode);
        }
        if(NodeRunEnum.END.getValue().equals(instNode.getRunFlag())){
            nodeRunFactory.getNodeRunService(NodeRunEnum.END).handle(instNode);
        }
        return instNode;
    }

    /**
     * 功能描述:
     * 〈手动处理节点通过状态〉
     * @param instNode instNode
     * @param auditAccountId auditAccountId
     * @param auditPass auditPass
     * @author 蝉鸣
     */
    public BpmInstNodePassBO passInstNode(BpmInstNode instNode, Long auditAccountId, Integer auditPass){
        //如果是元状态则视为不需要限制人员审批，只校验auditPass参数
        if(NodeAuditFlagEnum.INIT.getValue().equals(instNode.getAuditFlag())){
            BpmInstNodePassBO nodeAuditBO = new BpmInstNodePassBO();
            nodeAuditBO.setCanPass(Boolean.TRUE);
            nodeAuditBO.setCanNext(Boolean.TRUE);
            nodeAuditBO.setAuditPass(NodePassEnum.PASS.getValue());
            return nodeAuditBO;
        }
        //处理当前审批
        bpmInstNodeAuditService.handleNodeAudit(instNode.getId(), auditAccountId, auditPass);
        //校验当前节点是否可以通过
        return bpmInstNodeAuditService.checkInstNodeAuditPass(instNode.getId());
    }

}
