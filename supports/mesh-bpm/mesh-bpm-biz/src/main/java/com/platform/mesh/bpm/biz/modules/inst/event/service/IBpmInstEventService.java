package com.platform.mesh.bpm.biz.modules.inst.event.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.soa.event.rel.domain.bo.EventRelBO;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 动作信息
 * @author 蝉鸣
 */
public interface IBpmInstEventService extends IService<BpmInstEvent> {

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    List<BpmInstEvent> selectEventsByInstProcessIdId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    List<BpmInstEvent> selectEventByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈获取当前动作下事件信息〉
     * @param instActionId instActionId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    List<BpmInstEvent> selectEventByInstActionId(Long instActionId);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param instEvent instEvent
     * @return 正常返回:{@link BpmInstEvent}
     * @author 蝉鸣
     */
    BpmInstEvent addEventInst(BpmInstEvent instEvent);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instEventId instEventId
     * @author 蝉鸣
     */
    void deleteEventByInstEventId(Long instEventId);

    /**
     * 功能描述:
     * 〈执行当前事件信息〉
     * @param bpmInstEvents bpmInstEvents
     * @author 蝉鸣
     */
    void handleInstEvent(List<BpmInstEvent> bpmInstEvents);

    /**
     * 功能描述:
     * 〈获取关联对象〉
     * @param relDataType relDataType
     * @param relData relData
     * @author 蝉鸣
     */
    List<EventRelBO> getRelData(Integer relDataType, String relData);
}

