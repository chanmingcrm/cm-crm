package com.platform.mesh.bpm.biz.modules.inst.event.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.mapper.BpmInstEventMapper;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.modules.inst.event.service.manual.BpmInstEventServiceManual;
import com.platform.mesh.bpm.biz.soa.event.rel.domain.bo.EventRelBO;
import com.platform.mesh.core.constants.StrConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 动作信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstEventServiceImpl extends ServiceImpl<BpmInstEventMapper, BpmInstEvent> implements IBpmInstEventService {


    @Autowired
    private BpmInstEventServiceManual bpmInstEventServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstEvent> selectEventsByInstProcessIdId(Long instProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstEvent::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstEvent> selectEventByInstNodeId(Long instNodeId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstEvent::getInstNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前动作下事件信息〉
     * @param instActionId instActionId
     * @return 正常返回:{@link List<BpmInstEvent>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstEvent> selectEventByInstActionId(Long instActionId) {
        //根据实例动作ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstEvent::getInstActionId,instActionId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param bpmInstEvent bpmInstEvent
     * @return 正常返回:{@link BpmInstEvent}
     * @author 蝉鸣
     */
    @Override
    public BpmInstEvent addEventInst(BpmInstEvent bpmInstEvent) {
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

    /**
     * 功能描述:
     * 〈获取关联对象〉
     * @param relDataType relDataType
     * @param relData relData
     * @author 蝉鸣
     */
    @Override
    public List<EventRelBO> getRelData(Integer relDataType, String relData) {
        List<EventRelBO> relBOS = CollUtil.newArrayList();
        JSONArray jsonArray = JSONUtil.parseArray(relData);
        jsonArray.forEach(json->{
            EventRelBO relBO = new EventRelBO();
            JSONObject jsonObject = JSONUtil.parseObj(json);
            if(jsonObject.containsKey(StrConst.ID)){
                relBO.setRelDataId(jsonObject.get(StrConst.ID).toString());
            }
            if(jsonObject.containsKey(StrConst.NAME)){
                relBO.setRelDataName(jsonObject.get(StrConst.NAME).toString());
            }
            if(StrUtil.isNotBlank(relData)){
                relBOS.add(relBO);
            }
        });
        if(CollUtil.isEmpty(relBOS)){
            return CollUtil.newArrayList();
        }
        return bpmInstEventServiceManual.getRelData(relDataType,relBOS);
    }
}

