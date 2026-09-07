package com.platform.mesh.bpm.biz.soa.node.type.impl;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.soa.node.run.factory.NodeRunFactory;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 开始节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeTypeStartFactoryImpl implements NodeTypeService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeTypeStartFactoryImpl.class);

    @Autowired
    private NodeRunFactory<BpmInstNode> nodeRunFactory;


    /**
     * 功能描述:
     * 〈节点类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.START_NODE;
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
        //设置流程运行中
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        BpmInstProcess bpmInstProcess = instProcessService.getById(instNode.getInstProcessId());
        bpmInstProcess.setRunFlag(ProcessRunEnum.RUNNING.getValue());
        instProcessService.updateById(bpmInstProcess);
        //节点子项服务
        IBpmInstNodeSubService instNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        //执行节点子项
        Integer runNodeFlag = instNodeSubService.runNodeSub(instNode);
        instNode.setRunFlag(runNodeFlag);
        //修改节点
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        instNodeService.updateById(instNode);
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
