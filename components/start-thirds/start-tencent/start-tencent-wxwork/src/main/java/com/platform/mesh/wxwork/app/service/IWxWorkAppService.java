package com.platform.mesh.wxwork.app.service;

import java.util.List;

/**
 * @description 企微机器人消息发送服务
 * @author 蝉鸣
 */
public interface IWxWorkAppService {


    /**
     * 功能描述:
     * 〈获取token〉
     * @param corpId corpId
     * @param corpSecret corpSecret
     * @author 蝉鸣
     */
    String getToken(String corpId,String corpSecret);


    /**
     * 功能描述:
     * 〈获取配置了客户联系功能的成员列表〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    List<String> getFollowUser(String accessToken);

    /**
     * 功能描述:
     * 〈批量获取客户详情〉
     * @param accessToken accessToken
     * @param useridList useridList
     * @author 蝉鸣
     */
    Object getExternalContact(String accessToken, List<String> useridList, String cursor, Integer limit);

    /**
     * 功能描述:
     * 〈批量获取企业的 jsapi_ticket〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    Object getJsapiTicket(String accessToken,Integer ticketType);

    /**
     * 功能描述:
     * 〈批量获取企业的 jsapi_sign 签名〉
     * @param ticket ticket
     * @param url url
     * @author 蝉鸣
     */
    Object getJsapiSign(String ticket, String url);
}
