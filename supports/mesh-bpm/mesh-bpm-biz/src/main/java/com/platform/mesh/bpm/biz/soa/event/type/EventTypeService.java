package com.platform.mesh.bpm.biz.soa.event.type;


import com.platform.mesh.bpm.biz.soa.event.type.enums.EventTypeEnum;

import java.util.List;

/**
 * @description 事件工厂
 * @author 蝉鸣
 */
public interface EventTypeService<T> {

    /**
     * 功能描述:
     * 〈事件类型〉
     * @return 正常返回:{@link EventTypeEnum}
     * @author 蝉鸣
     */
    EventTypeEnum eventType();

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);

    /**
     * 功能描述:
     * 〈事件处理〉
     * @param classType classType
     * @author 蝉鸣
     */
    void handle(List<T> classType);
}
