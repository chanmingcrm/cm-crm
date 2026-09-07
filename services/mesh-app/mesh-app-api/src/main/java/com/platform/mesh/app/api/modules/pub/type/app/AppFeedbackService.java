package com.platform.mesh.app.api.modules.pub.type.app;


import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
public interface AppFeedbackService {

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String actionName();

    /**
     * 功能描述:
     * 〈添加处理〉
     * @param msgAppBO msgAppBO
     * @author 蝉鸣
     */
    default void syncName(MsgAppBO msgAppBO){}


}
