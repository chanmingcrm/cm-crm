package com.platform.mesh.bpm.biz.soa.event.rel.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.bpm.biz.soa.event.rel.EventRelService;
import com.platform.mesh.bpm.biz.soa.event.rel.domain.bo.EventRelBO;
import com.platform.mesh.bpm.biz.soa.event.rel.enums.EventRelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRelInitFactoryImpl implements EventRelService {

    private final static Logger log = LoggerFactory.getLogger(EventRelInitFactoryImpl.class);

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link EventRelEnum}
     * @author 蝉鸣
     */
    @Override
    public EventRelEnum eventRel() {
        return EventRelEnum.INIT;
    }

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param relBOS relBOS
     * @author 蝉鸣
     */
    @Override
    public List<EventRelBO> handle(List<EventRelBO> relBOS) {
        if(CollUtil.isEmpty(relBOS)){
            return CollUtil.newArrayList();
        }
        return relBOS;
    }


}
