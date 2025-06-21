package com.platform.mesh.bpm.biz.soa.node.pass.impl;

import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.action.factory.ActionFactory;
import com.platform.mesh.bpm.biz.soa.node.pass.NodePassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 开始节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodePassUnPassFactoryImpl implements NodePassService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodePassUnPassFactoryImpl.class);

    @Autowired
    private ActionFactory<BpmInstAction> actionFactory;

    @Autowired
    private ActionFactory<BpmInstLine> lineFactory;

    @Autowired
    private ActionFactory<BpmInstVariable> variableFactory;

    @Autowired
    private ActionFactory<BpmInstEvent> eventFactory;

    /**
     * 功能描述:
     * 〈节点通过类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    @Override
    public NodePassEnum nodePass() {
        return NodePassEnum.UN_PASS;
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
        return instNode;
    }

}
