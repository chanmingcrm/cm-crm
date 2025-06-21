package com.platform.mesh.bpm.biz.soa.node.run.impl;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.run.NodeRunService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 结束节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeRunStandFactoryImpl implements NodeRunService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeRunStandFactoryImpl.class);

    /**
     * 功能描述:
     * 〈节点运行类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeRunEnum nodeRun() {
        return NodeRunEnum.STAND;
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
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        //执行当前节点流出
        instNodeService.getServiceManual().inInstNode(instNode);
        return instNode;
    }

}
