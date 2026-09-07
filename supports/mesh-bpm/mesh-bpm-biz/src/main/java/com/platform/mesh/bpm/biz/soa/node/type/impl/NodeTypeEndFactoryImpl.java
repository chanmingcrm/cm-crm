package com.platform.mesh.bpm.biz.soa.node.type.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.soa.node.run.factory.NodeRunFactory;
import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.run.factory.ProcessRunFactory;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 结束节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeTypeEndFactoryImpl implements NodeTypeService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeTypeEndFactoryImpl.class);

    @Autowired
    private NodeRunFactory<BpmInstNode> nodeRunFactory;

    @Autowired
    private ProcessRunFactory<BpmInstProcess> processRunFactory;

    /**
     * 功能描述:
     * 〈节点类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.END_NODE;
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
        nodeRunFactory.getNodeRunService(NodeRunEnum.STAND).handle(instNode);
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        //设置结束节点已经结束
        instNode.setRunFlag(NodeRunEnum.END.getValue());
        instNodeService.updateById(instNode);
        //设置流程结束
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        BpmInstProcess bpmInstProcess = instProcessService.getById(instNode.getInstProcessId());
        bpmInstProcess.setRunFlag(ProcessRunEnum.END.getValue());
        //获取当前流程结束Pass状态
        if(ProcessPassEnum.INIT.getValue().equals(bpmInstProcess.getPassFlag())){
            bpmInstProcess.setPassFlag(ProcessPassEnum.PASS.getValue());
        }
        instProcessService.updateById(bpmInstProcess);
        //执行流程结束逻辑
        ProcessRunService<BpmInstProcess> processRunService = processRunFactory.getProcessRunService(ProcessRunEnum.END);
        processRunService.handle(bpmInstProcess);
        //设置流程下所有执行中的节点为初始状态
        instNodeService.lambdaUpdate()
                .set(BpmInstNode::getRunFlag,NodeRunEnum.INIT.getValue())
                .eq(BpmInstNode::getInstProcessId,instNode.getInstProcessId())
                .eq(BpmInstNode::getRunFlag,NodeRunEnum.RUNNING.getValue())
                .update();
        //查询当前流程父节点
        IBpmInstNodeSubService instNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        BpmInstNodeSub nodeSub = instNodeSubService.selectNodeSubByChildProcessId(instNode.getInstProcessId());
        if(ObjectUtil.isNotEmpty(nodeSub)){
            //设置父节点子项运行以及通过状态
            nodeSub.setChildProcessRunFlag(ProcessRunEnum.END.getValue());
            nodeSub.setChildProcessPassFlag(ProcessPassEnum.PASS.getValue());
            instNodeSubService.updateById(nodeSub);
            //当前子流程已结束，执行父节点
            BpmInstNode bpmInstNode = instNodeService.getById(nodeSub.getInstNodeId());
            instNodeService.handleTargetNode(CollUtil.newArrayList(bpmInstNode));
        }
        //执行运行节点标识
        nodeRunFactory.getNodeRunService(NodeRunEnum.RUNNING).handle(instNode);
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
        BpmInstNodePassBO instNodePassBO = new BpmInstNodePassBO();
        instNodePassBO.setCanNext(Boolean.TRUE);
        instNodePassBO.setCanPass(Boolean.TRUE);
        return instNodePassBO;
    }

}
