package com.platform.mesh.netty.server.soa.msg;


import com.platform.mesh.netty.server.domain.bo.CcMsgBO;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
public interface CcMsgExecuteService {

    /**
     * 功能描述:
     * 〈创建房间〉
     * @author 蝉鸣
     */
    default void createGroup(CcMsgBO ccMsgBO){}

    /**
     * 功能描述:
     * 〈加入房间〉
     * @author 蝉鸣
     */
    default void joinGroup(CcMsgBO ccMsgBO){}

    /**
     * 功能描述:
     * 〈离开房间〉
     * @author 蝉鸣
     */
    default void leaveGroup(CcMsgBO ccMsgBO){}

    /**
     * 功能描述:
     * 〈发送消息〉
     * @author 蝉鸣
     */
    default void msgSend(CcMsgBO ccMsgBO){}

}
