package com.platform.mesh.bpm.biz.soa.event.type.factory;

import com.platform.mesh.bpm.biz.soa.event.type.EventTypeService;
import com.platform.mesh.bpm.biz.soa.event.type.enums.EventTypeEnum;
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
public class EventTypeFactory<T> implements InitializingBean {

    @Autowired
    private List<EventTypeService<T>> eventTypeServiceList;

    private final Map<EventTypeEnum, EventTypeService<T>> futureTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程实现〉
     * @param eventType eventType
     * @return 正常返回:{@link EventTypeService <T>}
     * @author 蝉鸣
     */
    public EventTypeService<T> getEventService(EventTypeEnum eventType){
        return futureTypeMaps.get(eventType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (EventTypeService<T> service : eventTypeServiceList){
            futureTypeMaps.put(service.eventType(), service);
        }
    }
}
