package com.platform.mesh.wxwork.msg.service;

import cn.hutool.json.JSONObject;

/**
 * @description 企微机器人消息发送服务
 * @author 蝉鸣
 */
public interface WxWorkMsgService {


    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param msgType msgType
     * @param paramsMap paramsMap
     * @author 蝉鸣
     */
    void sendAppMsg(String key,String msgType, JSONObject paramsMap);

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param fileType fileType
     * @param filePath filePath
     * @author 蝉鸣
     */
    void sendAppMsg(String key ,String fileType,String filePath);

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param msgType msgType
     * @param paramsMap paramsMap
     * @author 蝉鸣
     */
    void sendRobotMsg(String key,String msgType, JSONObject paramsMap);

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param fileType fileType
     * @param filePath filePath
     * @author 蝉鸣
     */
    void sendRobotMsg(String key ,String fileType,String filePath);
}
