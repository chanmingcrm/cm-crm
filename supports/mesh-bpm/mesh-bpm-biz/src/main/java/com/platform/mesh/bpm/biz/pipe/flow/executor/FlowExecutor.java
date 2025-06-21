package com.platform.mesh.bpm.biz.pipe.flow.executor;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.pipe.flow.pipe.FlowPipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 流程管道执行
 * @author 蝉鸣
 */
@Service
public class FlowExecutor {

    private final static Logger log = LoggerFactory.getLogger(FlowExecutor.class);

    public <R extends BpmInstNode,C extends Long> void  initExecutor(FlowPipe<R,C> flowPipe, C param){
        flowPipe.init(param);
    }

    public <R extends BpmInstNode,C extends Long> void  midExecutor(FlowPipe<R,C> flowPipe, C param){
        flowPipe.mid(param);
    }

    public <R extends BpmInstNode,C extends Long> void  endExecutor(FlowPipe<R,C> flowPipe, C param){
        flowPipe.end(param);
    }
}
