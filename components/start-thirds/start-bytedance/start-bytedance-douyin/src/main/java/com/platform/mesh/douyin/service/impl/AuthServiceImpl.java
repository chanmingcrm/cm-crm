package com.platform.mesh.douyin.service.impl;

import com.bytedance.ads.ApiClient;
import com.bytedance.ads.api.Oauth2AdvertiserGetApi;
import com.bytedance.ads.api.Oauth2AppAccessTokenApi;
import com.bytedance.ads.model.Oauth2AdvertiserGetResponse;
import com.bytedance.ads.model.Oauth2AdvertiserGetResponseDataListInner;
import com.bytedance.ads.model.Oauth2AppAccessTokenRequest;
import com.bytedance.ads.model.Oauth2AppAccessTokenResponse;
import com.douyin.openapi.client.Client;
import com.douyin.openapi.client.models.OauthClientTokenRequest;
import com.douyin.openapi.client.models.OauthClientTokenResponse;
import com.douyin.openapi.client.models.OauthClientTokenResponseData;
import com.douyin.openapi.credential.models.Config;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.douyin.constants.DouYinApiConst;
import com.platform.mesh.douyin.constants.DouYinConst;
import com.platform.mesh.douyin.service.IAuthService;
import com.platform.mesh.redis.service.RedissonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @description  授权接口
 * @author 蝉鸣
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    /**
     * 功能描述:
     * 〈获取授权地址〉
     * @param appId appId
     * @author 蝉鸣
     */
    @Override
    public String getAuthUrl(String appId) {
        return String.format(DouYinApiConst.OCEAN_ENGINE_AUTH_URL,appId);
    }

    /**
     * 功能描述:
     * 〈获取回调参数〉
     * @param callBackUrl callBackUrl
     * @author 蝉鸣
     */
    @Override
    public String getCallBackUrl(String callBackUrl) {
        return "";
    }

    /**
     * 功能描述:
     * 〈获取token〉
     * @param clientKey clientKey
     * @param clientSecret clientSecret
     * @param grantType grantType
     * @author 蝉鸣
     */
    @Override
    public String getClientToken(String clientKey, String clientSecret, String grantType){
        String cacheKey = DouYinConst.DOU_YIN_CACHE_KEY.concat(SymbolConst.COLON).concat(clientKey);
        if(RedissonUtil.hasKey(cacheKey)){
            Object cacheObject = RedissonUtil.getCacheObject(cacheKey);
            return cacheObject.toString();
        }
        try {
            Config config = new Config().setClientKey(clientKey).setClientSecret(clientSecret);
            Client client = new Client(config);
            OauthClientTokenRequest sdkRequest = new OauthClientTokenRequest();
            sdkRequest.setClientKey(clientKey);
            sdkRequest.setClientSecret(clientSecret);
            sdkRequest.setGrantType(grantType);
            OauthClientTokenResponse sdkResponse = client.OauthClientToken(sdkRequest);
            OauthClientTokenResponseData data = sdkResponse.getData();
            //缓存token
            RedissonUtil.setCacheObject(cacheKey,data.getAccessToken(), Duration.ofSeconds(data.getExpiresIn()));
            return data.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取token〉
     * @param appId appId
     * @param secret secret
     * @param authCode authCode
     * @author 蝉鸣
     */
    @Override
    public String getAccessToken(Long appId, String secret, String authCode){
        Oauth2AppAccessTokenRequest oauth2AppAccessTokenRequest = new Oauth2AppAccessTokenRequest();
        oauth2AppAccessTokenRequest.setAppId(appId);
        oauth2AppAccessTokenRequest.setSecret(secret);
        Oauth2AppAccessTokenApi api = new Oauth2AppAccessTokenApi();
        ApiClient apiClient = api.getApiClient();
        apiClient.addDefaultHeader("Access-Token", "");
        apiClient.setDebugging(true);
        apiClient.setConnectTimeout(NumberConst.NUM_3 * NumberConst.NUM_10000);
        api.setApiClient(apiClient);
        try{
            Oauth2AppAccessTokenResponse response = api.openApiOauth2AppAccessTokenPost(oauth2AppAccessTokenRequest);
            System.out.println(response);
            assert response.getData() != null;
            return response.getData().getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取授权账户ID〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    @Override
    public List<Long> getAdvertiser(String accessToken){
        Oauth2AdvertiserGetApi api = new Oauth2AdvertiserGetApi();
        ApiClient apiClient = api.getApiClient();
        apiClient.addDefaultHeader("Access-Token", accessToken);
        apiClient.setDebugging(true);
        api.setApiClient(apiClient);
        try{
            Oauth2AdvertiserGetResponse response = api.openApiOauth2AdvertiserGetGet(accessToken);
            System.out.println(response);
            assert response.getData() != null;
            assert response.getData().getList() != null;
            return response.getData().getList().stream().map(Oauth2AdvertiserGetResponseDataListInner::getAdvertiserId).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
