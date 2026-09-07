package com.platform.mesh.upms.api.pub.upms;


import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
public interface UpmsFeedbackService {

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String actionName();

    /**
     * 功能描述:
     * 〈初始化租户〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void initTenant(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈初始化租户应用〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void initTenantApp(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈初始化租户流程〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void initTenantBpm(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈添加处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void syncDictName(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈添加处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void syncUserName(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈添加处理〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void syncOrgName(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈转移数据〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void transOrgData(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈删除流程关联数据〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void delBpmDataRel(MsgUpmsBO msgUpmsBO){}

    /**
     * 功能描述:
     * 〈删除第三方关联数据〉
     * @param msgUpmsBO msgUpmsBO
     * @author 蝉鸣
     */
    default void delCrmSyncThirdDataRel(MsgUpmsBO msgUpmsBO){}


}
