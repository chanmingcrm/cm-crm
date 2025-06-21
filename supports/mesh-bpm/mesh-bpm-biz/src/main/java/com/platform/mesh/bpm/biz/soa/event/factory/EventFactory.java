package com.platform.mesh.bpm.biz.soa.event.factory;

import com.platform.mesh.bpm.biz.soa.event.EventService;
import com.platform.mesh.bpm.biz.soa.event.enums.EventTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 事件工厂
 * @author 蝉鸣
 */
@Service
public class EventFactory<T> implements InitializingBean {

    @Autowired
    private List<EventService<T>> eventServiceList;

    private final Map<EventTypeEnum, EventService<T>> futureTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param eventType eventType
     * @return 正常返回:{@link EventService<T>}
     * @author 蝉鸣
     */
    public EventService<T> getEventService(EventTypeEnum eventType){
        return futureTypeMaps.get(eventType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (EventService<T> service : eventServiceList){
            futureTypeMaps.put(service.eventType(), service);
        }
    }
}
