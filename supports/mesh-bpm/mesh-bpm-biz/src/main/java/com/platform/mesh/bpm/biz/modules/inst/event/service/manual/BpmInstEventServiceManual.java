package com.platform.mesh.bpm.biz.modules.inst.event.service.manual;


import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.enums.InstEventHandleEnum;
import com.platform.mesh.bpm.biz.soa.event.EventService;
import com.platform.mesh.bpm.biz.soa.event.enums.EventTypeEnum;
import com.platform.mesh.bpm.biz.soa.event.factory.EventFactory;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstEventServiceManual {

    @Autowired
    EventFactory<BpmInstEvent> eventFactory;

    /**
     * 功能描述:
     * 〈执行实例事件信息〉
     * @param bpmInstEvents bpmInstEvents
     * @author 蝉鸣
     */
    public void handleInstEvent(List<BpmInstEvent> bpmInstEvents) {
        //将节点按照类型分类
        Map<Integer, List<BpmInstEvent>> instEventMap = bpmInstEvents
                .stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.getEventType()))
                .filter(item -> InstEventHandleEnum.UNDO.getValue().equals(item.getHandleFlag()))
                .collect(Collectors.groupingBy(BpmInstEvent::getEventType));
        //执行节点
        instEventMap.forEach((key,value)->{
            //执行节点信息，可直接执行的不需要将节点再放入工厂中

            //按照不同的分类调用不同的工厂服务
            EventTypeEnum enumByValue = BaseEnum.getEnumByValue(EventTypeEnum.class, key);
            EventService<BpmInstEvent> eventService = eventFactory.getEventService(enumByValue);
            FutureHandleUtil.runWithResult(value,eventService::handle);
        });

    }
}

