package com.platform.mesh.bpm.biz.soa.action;


import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;

import java.util.List;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
public interface ActionService<T> {

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link ActionTypeEnum}
     * @author 蝉鸣
     */
    ActionTypeEnum actionType();

    /**
     * 功能描述:
     * 〈动作处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);

    /**
     * 功能描述:
     * 〈动作处理〉
     * @param classType classType
     * @author 蝉鸣
     */
    void handle(List<T> classType);
}
