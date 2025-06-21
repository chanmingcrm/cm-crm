package com.platform.mesh.bpm.biz.soa.node.type.impl;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.soa.node.run.factory.NodeRunFactory;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 定时节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeTypeTimeFactoryImpl implements NodeTypeService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeTypeTimeFactoryImpl.class);

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
        return NodeTypeEnum.TIME_NODE;
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
        //节点运行中
        instNode.setRunFlag(NodeRunEnum.RUNNING.getValue());
        instNodeService.updateById(instNode);
        //节点子项服务
        IBpmInstNodeSubService instNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        //执行节点子项
        instNodeSubService.runNodeSub(instNode);
        //执行运行节点标识
        nodeRunFactory.getNodeRunService(NodeRunEnum.RUNNING).handle(instNode);
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
        //之后转化校验定时是否满足条件
        instNodePassBO.setCanNext(Boolean.FALSE);
        instNodePassBO.setCanPass(Boolean.FALSE);
        return instNodePassBO;
    }

}
