package com.platform.mesh.bpm.biz.soa.event.rel;


import com.platform.mesh.bpm.biz.soa.event.rel.domain.bo.EventRelBO;
import com.platform.mesh.bpm.biz.soa.event.rel.enums.EventRelEnum;

import java.util.List;

/**
 * @description 事件工厂
 * @author 蝉鸣
 */
public interface EventRelService {

    /**
     * 功能描述:
     * 〈事件类型〉
     * @return 正常返回:{@link EventRelEnum}
     * @author 蝉鸣
     */
    EventRelEnum eventRel();

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param relBOS relBOS
     * @author 蝉鸣
     */
    List<EventRelBO> handle(List<EventRelBO> relBOS);
}
