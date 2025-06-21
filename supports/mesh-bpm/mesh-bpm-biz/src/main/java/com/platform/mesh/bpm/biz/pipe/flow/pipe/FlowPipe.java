package com.platform.mesh.bpm.biz.pipe.flow.pipe;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.pipe.flow.IFlowPipe;

/**
 * @description 流程管道抽象
 * @author 蝉鸣
 */
public abstract class FlowPipe<R extends BpmInstNode,C extends Long> implements IFlowPipe<R,C> {

    /**
     * 功能描述:
     * 〈初始化执行〉
     * @param instNodeId instNodeId
     * @author 蝉鸣
     */
    public void init(C instNodeId){
        try{
            //加载后处理
            BpmInstNode l = onLoad(instNodeId);
            //开始阶段处理
            BpmInstNode t = onStart(instNodeId);
        }catch (Exception exception){
            BpmInstNode s = onError(instNodeId);
        }
    }

    /**
     * 功能描述:
     * 〈运行中执行〉
     * @param instNodeId instNodeId
     * @author 蝉鸣
     */
    public void mid(C instNodeId){
        try{
            //执行中处理
            Boolean pipe = pipe(instNodeId);
            if(pipe){
                BpmInstNode s = onSuccess(instNodeId);
            }else{
                BpmInstNode s = onError(instNodeId);
            }
        }catch (Exception exception){
            BpmInstNode s = onError(instNodeId);
        }
    }

    /**
     * 功能描述:
     * 〈结束后执行〉
     * @param instNodeId instNodeId
     * @author 蝉鸣
     */
    public void end(C instNodeId){
        try{
            //执行后处理
            BpmInstNode s = onEnd(instNodeId);
        }catch (Exception exception){
            BpmInstNode s = onError(instNodeId);
        }


    }

    /**
     * 功能描述:
     * 〈执行中过程〉
     * @param instNodeId instNodeId
     * @author 蝉鸣
     */
    private Boolean pipe(C instNodeId){
        //执行多个步骤，暂时示例一个
        BpmInstNode s =onProcess(instNodeId);
        return Boolean.TRUE;
    }

}
