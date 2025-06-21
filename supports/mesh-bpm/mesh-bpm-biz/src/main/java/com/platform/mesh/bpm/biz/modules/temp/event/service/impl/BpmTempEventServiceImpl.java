package com.platform.mesh.bpm.biz.modules.temp.event.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.po.BpmTempEvent;
import com.platform.mesh.bpm.biz.modules.temp.event.mapper.BpmTempEventMapper;
import com.platform.mesh.bpm.biz.modules.temp.event.service.IBpmTempEventService;
import com.platform.mesh.bpm.biz.modules.temp.event.service.manual.BpmTempEventServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 事件信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempEventServiceImpl extends ServiceImpl<BpmTempEventMapper, BpmTempEvent> implements IBpmTempEventService {


    @Autowired
    private BpmTempEventServiceManual bpmTempEventServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempEvent> selectEventsByTempProcessIdId(Long tempProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempEvent::getTempProcessId,tempProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempEvent> selectEventByTempNodeId(Long tempNodeId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempEvent::getTempNodeId,tempNodeId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前动作下事件信息〉
     * @param tempActionId tempActionId
     * @return 正常返回:{@link List<BpmTempEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempEvent> selectEventByTempActionId(Long tempActionId) {
        //根据实例动作ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempEvent::getTempActionId,tempActionId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param bpmTempEvent bpmTempEvent
     * @return 正常返回:{@link BpmTempEvent}
     * @author 蝉鸣
     */
    @Override
    public BpmTempEvent addEventTemp(BpmTempEvent bpmTempEvent) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempEventId tempEventId
     * @author 蝉鸣
     */
    @Override
    public void deleteEventByTempEventId(Long tempEventId) {
        //删除事件信息
        this.removeById(tempEventId);
    }
}

