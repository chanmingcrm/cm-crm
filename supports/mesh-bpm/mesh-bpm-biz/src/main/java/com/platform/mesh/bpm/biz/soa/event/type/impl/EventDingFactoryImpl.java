package com.platform.mesh.bpm.biz.soa.event.type.impl;

import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.enums.InstEventHandleEnum;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.soa.event.type.EventTypeService;
import com.platform.mesh.bpm.biz.soa.event.type.enums.EventTypeEnum;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDingFactoryImpl implements EventTypeService<BpmInstEvent> {

    private final static Logger log = LoggerFactory.getLogger(EventDingFactoryImpl.class);

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link EventTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public EventTypeEnum eventType() {
        return EventTypeEnum.DING_TALK;
    }

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param instEvent instEvent
     * @return 正常返回:{@link BpmInstEvent}
     * @author 蝉鸣
     */
    @Override
    public BpmInstEvent handle(BpmInstEvent instEvent) {
        //使用工具类避免循环服务依赖
        IBpmInstEventService instEventService = SpringContextHolderUtil.getBean(IBpmInstEventService.class);
        instEvent.setHandleFlag(InstEventHandleEnum.DONE.getValue());
        instEventService.updateById(instEvent);
        return instEvent;
    }

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param instEvents instEvents
     * @author 蝉鸣
     */
    @Override
    public void handle(List<BpmInstEvent> instEvents) {
        log.info("发送钉钉通知");
    }

}
