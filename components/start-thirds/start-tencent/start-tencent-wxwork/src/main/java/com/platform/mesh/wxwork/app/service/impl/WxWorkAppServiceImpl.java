package com.platform.mesh.wxwork.app.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.wxwork.app.domain.JsSignBO;
import com.platform.mesh.wxwork.app.service.IWxWorkAppService;
import com.platform.mesh.wxwork.contants.WxWorkConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 企微机器人消息发送服务
 * @author 蝉鸣
 */
@Service
public class WxWorkAppServiceImpl implements IWxWorkAppService {

    private final static Logger log = LoggerFactory.getLogger(WxWorkAppServiceImpl.class);

    /**
     * 功能描述:
     * 〈获取token〉
     * @param corpId corpId
     * @param corpSecret corpSecret
     * @author 蝉鸣
     */
    @Override
    public String getToken(String corpId,String corpSecret) {
        String uri = WxWorkConst.WX_WORK_INNER_APP_TOKEN_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put("corpid",corpId);
        map.put("corpsecret",corpSecret);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        String accessToken = response.get("access_token").toString();
        log.info(accessToken);
        return accessToken;
    }


    /**
     * 功能描述:
     * 〈获取配置了客户联系功能的成员列表〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    @Override
    public List<String> getFollowUser(String accessToken) {
        String uri = WxWorkConst.WX_WORK_INNER_APP_FOLLOW_USER_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token",accessToken);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject response = restTemplate.getForObject(serverUrl, JSONObject.class);
        assert response != null;
        JSONArray jsonArray = JSONUtil.parseArray(response.get("follow_user"));
        return JSONUtil.toList(jsonArray, String.class);
    }

    /**
     * 功能描述:
     * 〈批量获取客户详情〉
     * @param accessToken accessToken
     * @param useridList useridList
     * @author 蝉鸣
     */
    @Override
    public Object getExternalContact(String accessToken, List<String> useridList, String cursor, Integer limit) {
        if(ObjectUtil.isEmpty(limit) || limit <= NumberConst.NUM_0 || limit >= NumberConst.NUM_100){
            limit = NumberConst.NUM_100;
        }
        String uri = WxWorkConst.WX_WORK_INNER_APP_EXTERNAL_CONTACT_BATCH_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token",accessToken);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        //请求体参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userid_list",useridList);
        paramsMap.put("cursor",cursor);
        paramsMap.put("limit",limit);
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        return restTemplate.postForObject(serverUrl, httpEntity, Object.class);
    }

    /**
     * 功能描述:
     * 〈批量获取企业的 jsapi_ticket〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    @Override
    public Object getJsapiTicket(String accessToken,Integer ticketType) {
        String uri;
        HashMap<String, Object> map = new HashMap<>();
        if(YesOrNoEnum.YES.getValue().equals(ticketType)){
            uri = WxWorkConst.WX_WORK_JSAPI_TICKET_COM;
        }else{
            uri = WxWorkConst.WX_WORK_JSAPI_TICKET;
            map.put("type","agent_config");
        }
        map.put("access_token",accessToken);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        //请求体参数
        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        return restTemplate.getForObject(serverUrl, Object.class, httpEntity);
    }

    /**
     * 功能描述:
     * 〈批量获取企业的 jsapi_sign〉
     * @param ticket ticket
     * @param url url
     * @author 蝉鸣
     */
    @Override
    public JsSignBO getJsapiSign(String ticket, String url) {
        String nonceStr = IdUtil.fastUUID();
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String format = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s",
                ticket, nonceStr, timestamp, url);
        // SHA-1加密并转为小写
        JsSignBO jsSignBO = new JsSignBO();
        jsSignBO.setTicket(ticket);
        jsSignBO.setNonceStr(nonceStr);
        jsSignBO.setTimestamp(timestamp);
        jsSignBO.setSign(DigestUtil.sha1Hex(format).toLowerCase());
        return jsSignBO;
    }


}
