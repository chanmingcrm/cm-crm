package com.platform.mesh.wxwork.msg.service.manual;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.wxwork.contants.WxWorkConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description 企微机器人消息发送服务
 * @author 蝉鸣
 */
@Service
public class MsgSendServiceManual {

    private final static Logger log = LoggerFactory.getLogger(MsgSendServiceManual.class);

    @Autowired
    private MsgTempServiceManual msgTempServiceManual;

    /**
     * 功能描述:
     * 〈机器人发送消息〉
     * @author 蝉鸣
     */
    public void sendMsg(String key ,String msgType,JSONObject entries){
        //获取发送配置地址
        String serverUrl = getUrl(key);
        //构造发送参数信息
        Map<String, Object> paramMap = getParamMap(msgType);
        //获取发送类型模板
        paramMap.put(msgType,entries);
        //发送信息
        sendMsg(serverUrl,paramMap);
    }

    /**
     * 功能描述:
     * 〈机器人发送文件消息〉
     * @author 蝉鸣
     */
    public void sendMsg(String key ,String msgType,String filePath){
        //上传文件获取mediaId
        String mediaId = sendFileMsg(key,msgType,filePath);
        JSONObject entries = msgTempServiceManual.packFileMsg(mediaId);
        //发送消息
        sendMsg(key,msgType,entries);
    }

    /**
     * 功能描述:
     * 〈获取Url〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String getUrl(String key){
        String uri = WxWorkConst.ROBOT_SEND_MSG_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put(WxWorkConst.KEY,key);
        return HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);
    }

    /**
     * 功能描述:
     * 〈获取Url〉
     * @param  key 机器人webhook key
     * @param  fileType 文件类型<file:普通文件,voice:音频文件>
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String getUrl(String key,String fileType){
        String uri = WxWorkConst.ROBOT_UPLOAD_FILE_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put(WxWorkConst.KEY,key);
        map.put(WxWorkConst.TYPE,fileType);
        return HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);
    }

    /**
     * 功能描述:
     * 〈获取初始参数〉
     * @param  msgType 消息类型
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String,Object> getParamMap(String msgType){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(WxWorkConst.MSG_TYPE,msgType);
        JSONObject entries = JSONUtil.createObj();
        paramsMap.put(msgType,entries);
        return paramsMap;
    }

    /**
     * 功能描述:
     * 〈获取初始参数〉
     * @param  serverUrl 发送Url路径
     * @param  paramsMap 参数
     * @author 蝉鸣
     */
    public void sendMsg(String serverUrl,Map<String,Object> paramsMap){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
    }

    /**
     * 功能描述:
     * 〈获取文件上传mediaId〉
     * @param  key 机器人webhook key
     * @param  msgType 消息类型
     * @param  filePath 文件路径
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String sendFileMsg(String key,String msgType,String filePath){
        String uploadUrl = getUrl(key,msgType);
        // 上传文件获取mediaId
        JSONObject jsonObject = uploadFile(uploadUrl, new File(filePath));
        return Objects.requireNonNull(jsonObject).get(WxWorkConst.MEDIA_ID).toString();
    }

    /**
     * 功能描述:
     * 〈上传文件〉
     * @param  serverUrl 上传地址
     * @param  file 文件对象
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public JSONObject uploadFile(String serverUrl,File file){
        RestTemplate restTemplate = new RestTemplate();
        // 上传文件
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(file);
        paramsMap.add(WxWorkConst.FILE, resource);
        paramsMap.add(WxWorkConst.FILENAME, resource.getFilename());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(paramsMap, headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(serverUrl, requestEntity, JSONObject.class);
        return response.getBody();
    }


}
