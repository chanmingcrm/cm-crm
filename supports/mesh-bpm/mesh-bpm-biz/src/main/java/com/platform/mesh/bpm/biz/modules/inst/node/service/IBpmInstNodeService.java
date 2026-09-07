package com.platform.mesh.bpm.biz.modules.inst.node.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.dto.BpmInstNodeHandleDTO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVarVO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.manual.BpmInstNodeServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点信息
 * @author 蝉鸣
 */
public interface IBpmInstNodeService extends IService<BpmInstNode> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeServiceManual}
     * @author 蝉鸣
     */
    BpmInstNodeServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    List<BpmInstNode> selectNodesByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNodeVO>}
     * @author 蝉鸣
     */
    List<BpmInstNodeVO> selectInstNodeByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @param instProcessId instProcessId
     * @param runFlag runFlag
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    List<BpmInstNodeVO> selectNodeVOByInstProcessIdAndRunFlag(Long instProcessId, Integer runFlag);

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @param instProcessId instProcessId
     * @param runFlag runFlag
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    List<BpmInstNode> selectNodeByInstProcessIdAndRunFlag(Long instProcessId, Integer runFlag);

    /**
     * 功能描述:
     * 〈获取下一批节点〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    List<BpmInstNode> selectNextNode(BpmInstProcess bpmInstProcess);

    /**
     * 功能描述:
     * 〈获取下一批节点〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    List<BpmInstNode> selectNextNode(List<BpmInstNode> bpmInstNodes);

    /**
     * 功能描述:
     * 〈获取当前节点需要的变量信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeVarVO}
     * @author 蝉鸣
     */
    BpmInstNodeVarVO getInstNodeVar(Long instNodeId);

    /**
     * 功能描述:
     * 〈执行节点〉
     * @param handleDTO handleDTO
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    BpmInstNode handleInstNode(BpmInstNodeHandleDTO handleDTO);

    /**
     * 功能描述:
     * 〈执行节点〉
     * @param bpmInstNodes bpmInstNodes
     * @author 蝉鸣
     */
    void handleTargetNode(List<BpmInstNode> bpmInstNodes);

    /**
     * 功能描述:
     * 〈去目标节点〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    Boolean gotoTargetNode(Long instNodeId);

    /**
     * 功能描述:
     * 〈重置节点变量〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    void clearNodeVar(BpmInstNode instNode);

    /**
     * 功能描述:
     * 〈获取实例节点关联信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeBO}
     * @author 蝉鸣
     */
    BpmInstNodeBO getInstNodeData(Long instNodeId);

    /**
     * 功能描述:
     * 〈发送审批回调消息〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    void sendBpmMsg(BpmInstNode instNode);
}

