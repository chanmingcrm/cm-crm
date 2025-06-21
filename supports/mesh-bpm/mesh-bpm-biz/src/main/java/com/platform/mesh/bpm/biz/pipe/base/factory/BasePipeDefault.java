package com.platform.mesh.bpm.biz.pipe.base.factory;

import cn.hutool.core.util.StrUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.pipe.base.pipe.BasePipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 基础管道
 * @author 蝉鸣
 */
public class BasePipeDefault extends BasePipe<BpmInstNode,Long> {

    private final static Logger log = LoggerFactory.getLogger(BasePipeDefault.class);

    public BasePipeDefault(Long instNodeId){
        super(instNodeId);
    }

    /**
     * 功能描述:
     * 〈加载时执行〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode onLoad(Long instNodeId) {
        return new BpmInstNode();
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
        BpmInstNode demo = new BpmInstNode();
        return new BpmInstNode();
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
        String[] a = {"a","b","c","d","e","f","g","h","i","j"};
        return new BpmInstNode();
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
        return new BpmInstNode();
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
        return new BpmInstNode();
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
        return new BpmInstNode();
    }

}
