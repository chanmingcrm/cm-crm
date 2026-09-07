package com.platform.mesh.bpm.biz.soa.event.rel.factory;

import com.platform.mesh.bpm.biz.soa.event.rel.EventRelService;
import com.platform.mesh.bpm.biz.soa.event.rel.enums.EventRelEnum;
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
public class EventRelFactory implements InitializingBean {

    @Autowired
    private List<EventRelService> eventRelServiceList;

    private final Map<EventRelEnum, EventRelService> futureTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的关联实现〉
     * @param eventType eventType
     * @return 正常返回:{@link EventRelService}
     * @author 蝉鸣
     */
    public EventRelService getEventRelService(EventRelEnum eventType){
        return futureTypeMaps.get(eventType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (EventRelService service : eventRelServiceList){
            futureTypeMaps.put(service.eventRel(), service);
        }
    }
}
