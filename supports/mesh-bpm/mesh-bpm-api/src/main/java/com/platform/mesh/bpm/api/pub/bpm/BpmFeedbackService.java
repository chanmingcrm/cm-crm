package com.platform.mesh.bpm.api.pub.bpm;


import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
public interface BpmFeedbackService {

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
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void add(MsgBpmBO msgBpmBO){}

    /**
     * 功能描述:
     * 〈修改处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void edit(MsgBpmBO msgBpmBO){}

    /**
     * 功能描述:
     * 〈删除处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void del(MsgBpmBO msgBpmBO){}

    /**
     * 功能描述:
     * 〈作废/删除处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void cancel(MsgBpmBO msgBpmBO){}

    /**
     * 功能描述:
     * 〈转化处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void transData(MsgBpmBO msgBpmBO){}

    /**
     * 功能描述:
     * 〈阶段流程处理〉
     * @param msgBpmBO msgBpmBO
     * @author 蝉鸣
     */
    default void processStage(MsgBpmBO msgBpmBO){}

}
