package com.platform.mesh.bpm.biz.modules.hist.event.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.hist.event.mapper.BpmHistEventMapper;
import com.platform.mesh.bpm.biz.modules.hist.event.service.IBpmHistEventService;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.service.manual.BpmInstEventServiceManual;
import com.platform.mesh.bpm.biz.modules.hist.event.domain.po.BpmHistEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 动作信息
 * @author 蝉鸣
 */
@Service()
public class BpmHistEventServiceImpl extends ServiceImpl<BpmHistEventMapper, BpmHistEvent> implements IBpmHistEventService {


    @Autowired
    private BpmInstEventServiceManual bpmInstEventServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmHistEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmHistEvent> selectEventsByInstProcessIdId(Long instProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmHistEvent::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmHistEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmHistEvent> selectEventByInstNodeId(Long instNodeId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmHistEvent::getInstNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前动作下事件信息〉
     * @param instActionId instActionId
     * @return 正常返回:{@link List<BpmHistEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmHistEvent> selectEventByInstActionId(Long instActionId) {
        //根据实例动作ID查询所有的事件
        return this.lambdaQuery().eq(BpmHistEvent::getInstActionId,instActionId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param bpmHistEvent bpmHistEvent
     * @return 正常返回:{@link BpmHistEvent}
     * @author 蝉鸣
     */
    @Override
    public BpmHistEvent addEventInst(BpmHistEvent bpmHistEvent) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instEventId instEventId
     * @author 蝉鸣
     */
    @Override
    public void deleteEventByInstEventId(Long instEventId) {
        //删除事件信息
        this.removeById(instEventId);
    }

    /**
     * 功能描述:
     * 〈执行当前事件信息〉
     * @param bpmInstEvents bpmInstEvents
     * @author 蝉鸣
     */
    @Override
    public void handleInstEvent(List<BpmInstEvent> bpmInstEvents) {
        //校验事件
        if(CollUtil.isEmpty(bpmInstEvents)){
            return;
        }
        //执行实例事件信息
        bpmInstEventServiceManual.handleInstEvent(bpmInstEvents);
    }
}

