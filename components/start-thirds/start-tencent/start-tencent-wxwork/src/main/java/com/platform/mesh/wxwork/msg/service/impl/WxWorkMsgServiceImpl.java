package com.platform.mesh.wxwork.msg.service.impl;

import cn.hutool.json.JSONObject;
import com.platform.mesh.wxwork.msg.service.WxWorkMsgService;
import com.platform.mesh.wxwork.msg.service.manual.MsgSendServiceManual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 企微机器人消息发送服务
 * @author 蝉鸣
 */
@Service
public class WxWorkMsgServiceImpl implements WxWorkMsgService {

    private final static Logger log = LoggerFactory.getLogger(WxWorkMsgServiceImpl.class);

    @Autowired
    private MsgSendServiceManual msgSendServiceManual;


    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param fileType fileType
     * @param paramsMap paramsMap
     * @author 蝉鸣
     */
    @Override
    public void sendAppMsg(String key, String fileType, JSONObject paramsMap) {
        msgSendServiceManual.sendMsg(key,fileType,paramsMap);
        log.info("测试");
    }

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param msgType msgType
     * @param filePath filePath
     * @author 蝉鸣
     */
    @Override
    public void sendAppMsg(String key ,String msgType,String filePath) {
//        robotSendServiceManual.sendMsg(key,msgType,filePath);
        log.info("测试");
    }

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param fileType fileType
     * @param paramsMap paramsMap
     * @author 蝉鸣
     */
    @Override
    public void sendRobotMsg(String key, String fileType, JSONObject paramsMap) {
        msgSendServiceManual.sendMsg(key,fileType,paramsMap);
        log.info("测试");
    }

    /**
     * 功能描述:
     * 〈发送消息〉
     * @param key key
     * @param msgType msgType
     * @param filePath filePath
     * @author 蝉鸣
     */
    @Override
    public void sendRobotMsg(String key ,String msgType,String filePath) {
//        robotSendServiceManual.sendMsg(key,msgType,filePath);
        log.info("测试");
    }

}
