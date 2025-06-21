package com.platform.mesh.bpm.biz.modules.inst.nodesub.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.manual.BpmInstNodeSubServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点子项信息
 * @author 蝉鸣
 */
public interface IBpmInstNodeSubService extends IService<BpmInstNodeSub> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeSubServiceManual}
     * @author 蝉鸣
     */
    BpmInstNodeSubServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈根据实例节点ID获取节点子项〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstNodeSub>}
     * @author 蝉鸣
     */
    List<BpmInstNodeSub> selectNodeSubsByNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈根据实例流程ID获取节点子项〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    BpmInstNodeSub selectNodeSubByChildProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈运行当前节点下的子项流程〉
     * @param instNode instNode
     * @return 正常返回:{@link Integer}
     * @author 蝉鸣
     */
    Integer runNodeSub(BpmInstNode instNode);
}

