package com.platform.mesh.douyin.service;

import java.util.List;

/**
 * @description 授权接口
 * @author 蝉鸣
 */
public interface IAuthService {

    /**
     * 功能描述:
     * 〈获取授权地址〉
     * @param appId appId
     * @author 蝉鸣
     */
    String getAuthUrl(String appId);

    /**
     * 功能描述:
     * 〈获取回调参数〉
     * @param callBackUrl callBackUrl
     * @author 蝉鸣
     */
    String getCallBackUrl(String callBackUrl);

    /**
     * 功能描述:
     * 〈获取token〉
     * @param clientKey clientKey
     * @param clientSecret clientSecret
     * @param grantType grantType
     * @author 蝉鸣
     */
    String getClientToken(String clientKey, String clientSecret, String grantType);

    /**
     * 功能描述:
     * 〈获取token〉
     * @param appId appId
     * @param secret secret
     * @param authCode authCode
     * @author 蝉鸣
     */
    String getAccessToken(Long appId, String secret, String authCode);

    /**
     * 功能描述:
     * 〈获取授权账户ID〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    List<Long> getAdvertiser(String accessToken);

}
