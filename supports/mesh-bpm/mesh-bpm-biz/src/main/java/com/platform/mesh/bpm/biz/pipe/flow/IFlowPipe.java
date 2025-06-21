package com.platform.mesh.bpm.biz.pipe.flow;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.pipe.base.IBasePipe;

/**
 * @description 流程管道
 * @author 蝉鸣
 */
public interface IFlowPipe<R extends BpmInstNode, C extends Long> extends IBasePipe<R,C> {

      /**
       * 功能描述:
       * 〈加载时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onLoad(C instNodeId);
      /**
       * 功能描述:
       * 〈开始时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onStart(C instNodeId);
      /**
       * 功能描述:
       * 〈执行时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onProcess(C instNodeId);
      /**
       * 功能描述:
       * 〈成功时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onSuccess(C instNodeId);
      /**
       * 功能描述:
       * 〈错误时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onError(C instNodeId);
      /**
       * 功能描述:
       * 〈结束时执行〉
       * @param instNodeId instNodeId
       * @return 正常返回:{@link R}
       * @author 蝉鸣
       */
      R onEnd(C instNodeId);
}
