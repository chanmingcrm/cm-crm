package com.platform.mesh.bpm.biz.modules.temp.event.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.po.BpmTempEvent;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 事件信息
 * @author 蝉鸣
 */
public interface IBpmTempEventService extends IService<BpmTempEvent> {

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    List<BpmTempEvent> selectEventsByTempProcessIdId(Long tempProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    List<BpmTempEvent> selectEventByTempNodeId(Long tempNodeId);

    /**
     * 功能描述:
     * 〈获取当前动作下事件信息〉
     * @param tempActionId tempActionId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    List<BpmTempEvent> selectEventByTempActionId(Long tempActionId);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param tempEvent tempEvent
     * @return 正常返回:{@link BpmTempEvent}
     * @author 蝉鸣
     */
    BpmTempEvent addEventTemp(BpmTempEvent tempEvent);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempEventId tempEventId
     * @author 蝉鸣
     */
    void deleteEventByTempEventId(Long tempEventId);

}

