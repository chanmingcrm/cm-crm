package com.platform.mesh.bpm.biz.pipe.flow.factory;

import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.action.service.IBpmInstActionService;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.pipe.flow.pipe.FlowPipe;
import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 流程管道
 * @author 蝉鸣
 */
@Service
public class FlowPipeDefault extends FlowPipe<BpmInstNode,Long> {

    private final static Logger log = LoggerFactory.getLogger(FlowPipeDefault.class);

    @Autowired
    private IBpmInstActionService flowInstActionService;

    @Autowired
    private IBpmInstEventService flowInstEventService;

    /**
     * 功能描述:
     * 〈加载时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onLoad(Long instNodeId) {
        //执行加载动作
        //执行加载动作事件
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                ,ActionTypeEnum.ON_LOAD.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }

    /**
     * 功能描述:
     * 〈开始时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onStart(Long instNodeId) {
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                , ActionTypeEnum.ON_START.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }

    /**
     * 功能描述:
     * 〈执行时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onProcess(Long instNodeId) {
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                , ActionTypeEnum.ON_PROCESS.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }

    /**
     * 功能描述:
     * 〈成功时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onSuccess(Long instNodeId) {
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                , ActionTypeEnum.ON_SUCCESS.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }

    /**
     * 功能描述:
     * 〈失败时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onError(Long instNodeId) {
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                , ActionTypeEnum.ON_ERROR.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }

    /**
     * 功能描述:
     * 〈结束时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onEnd(Long instNodeId) {
        List<BpmInstAction> bpmInstActions = flowInstActionService.selectActionsByInstNodeIdAndNodeType(instNodeId
                , ActionTypeEnum.ON_END.getValue());
        flowInstActionService.handleInstAction(bpmInstActions);
        return new BpmInstNode().setId(instNodeId);
    }
}
