package com.platform.mesh.bpm.biz.pipe.base.pipe;

import com.platform.mesh.bpm.biz.pipe.base.IBasePipe;

/**
 * @description 基础管道抽象
 * @author 蝉鸣
 */
public abstract class BasePipe <R,C> implements IBasePipe<R,C> {
    private final C processId;

    public BasePipe(C processId){
        this.processId = processId;
        R t = onLoad(processId);
    }

    /**
     * 功能描述:
     * 〈这个抽象对象通过本方法启动，而控制程序方法调用的具体过程就是从这里开始〉
     * @author 蝉鸣
     */
    public void executor(){
        R t = onStart(processId);
        Boolean process = process();
        if(process){
            R s = onSuccess(processId);
        }else{
            R s = onError(processId);
        }
        R s = onEnd(processId);

    }

    /**
     * 功能描述:
     * 〈执行过程〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    private Boolean process(){
        //执行多个步骤，暂时示例一个
        R s =onProcess(processId);
        return Boolean.TRUE;
    }

}
