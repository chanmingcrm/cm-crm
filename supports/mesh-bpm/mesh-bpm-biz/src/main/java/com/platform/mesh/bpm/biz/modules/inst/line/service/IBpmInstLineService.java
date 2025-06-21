package com.platform.mesh.bpm.biz.modules.inst.line.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.service.manual.BpmInstLineServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程线信息
 * @author 蝉鸣
 */
public interface IBpmInstLineService extends IService<BpmInstLine> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstLineServiceManual}
     * @author 蝉鸣
     */
    BpmInstLineServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取实例下线信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    List<BpmInstLine> selectLinesByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取实例下线信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    List<BpmInstLine> selectInLineByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈获取出节点线信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    List<BpmInstLine> selectOutLineByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈校验当前线是否通过〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    Boolean checkLinePass(Long instLineId);

}

