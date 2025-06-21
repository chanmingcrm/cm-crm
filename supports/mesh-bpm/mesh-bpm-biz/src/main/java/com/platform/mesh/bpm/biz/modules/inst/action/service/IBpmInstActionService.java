package com.platform.mesh.bpm.biz.modules.inst.action.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.action.service.manual.BpmInstActionServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 动作信息
 * @author 蝉鸣
 */
public interface IBpmInstActionService extends IService<BpmInstAction> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstActionServiceManual}
     * @author 蝉鸣
     */
    BpmInstActionServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取实例下动作信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    List<BpmInstAction> selectActionsByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    List<BpmInstAction> selectActionsByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param instNodeId instNodeId
     * @param actionType actionType
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    List<BpmInstAction> selectActionsByInstNodeIdAndNodeType(Long instNodeId, Integer actionType);

    /**
     * 功能描述:
     * 〈添加动作信息〉
     * @param instAction instAction
     * @return 正常返回:{@link BpmInstAction}
     * @author 蝉鸣
     */
    BpmInstAction addActionInst(BpmInstAction instAction);

    /**
     * 功能描述:
     * 〈删除动作信息〉
     * @param actionId actionId
     * @author 蝉鸣
     */
    void deleteActionInstActionId(Long actionId);

    /**
     * 功能描述:
     * 〈执行当前动作信息〉
     * @param bpmInstAction bpmInstAction
     * @author 蝉鸣
     */
    void handleInstAction(BpmInstAction bpmInstAction);

    /**
     * 功能描述:
     * 〈执行当前动作信息〉
     * @param bpmInstActions bpmInstActions
     * @author 蝉鸣
     */
    void handleInstAction(List<BpmInstAction> bpmInstActions);

}

