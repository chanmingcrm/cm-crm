package com.platform.mesh.upms.biz.soa.org.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.uaa.api.modules.tenant.domain.TenantClientBO;
import com.platform.mesh.upms.api.modules.sys.account.enums.SourceFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.domain.po.OrgLevel;
import com.platform.mesh.upms.biz.modules.org.level.enums.LevelFlagEnum;
import com.platform.mesh.upms.biz.modules.org.level.service.IOrgLevelService;
import com.platform.mesh.upms.biz.modules.sys.user.enums.AdminFlagEnum;
import com.platform.mesh.upms.biz.soa.org.ThirdOrgService;
import com.platform.mesh.upms.biz.soa.org.constant.WxWorkConst;
import com.platform.mesh.upms.biz.soa.org.domain.bo.SyncUserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description AI智能体工厂实现
 * @author 蝉鸣
 */
@Service
public class WxWorkFactoryImpl implements ThirdOrgService {

    private static final Logger log = LoggerFactory.getLogger(WxWorkFactoryImpl.class);

    @Autowired
    private IOrgLevelService orgLevelService;

    @Override
    public SourceFlagEnum sourceFlag() {
        return SourceFlagEnum.WX_WORK;
    }

    /**
     * 功能描述:
     * 〈同步部门〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    @Override
    public List<OrgLevel> syncDeptList(TenantClientBO clientBO) {
        String accessToken = getAccessToken(clientBO);
        return getDeptList(accessToken);
    }

    /**
     * 功能描述:
     * 〈同步用户〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    @Override
    public List<SyncUserBO> syncUserList(TenantClientBO clientBO) {
        String accessToken = getAccessToken(clientBO);
        //获取同步的部门列表
        List<OrgLevel> orgLevels = orgLevelService.lambdaQuery().eq(OrgLevel::getLevelSource, this.sourceFlag().getValue()).list();
        if(CollUtil.isEmpty(orgLevels)){
            return CollUtil.newArrayList();
        }
        return getUserList(accessToken,orgLevels);
    }

    /**
     * 功能描述:
     * 〈获取请求Token对象〉
     * @param clientBO clientBO
     * @author 蝉鸣
     */
    public String getAccessToken(TenantClientBO clientBO) {
        String uri = WxWorkConst.TOKEN_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put(WxWorkConst.CORP_ID,clientBO.getClientId());
        map.put(WxWorkConst.CORP_SECRET,clientBO.getClientSecret());
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        String accessToken = response.get(WxWorkConst.ACCESS_TOKEN).toString();
        log.info(accessToken);
        return accessToken;
    }

    /**
     * 功能描述:
     * 〈获取组织对象〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    public List<OrgLevel> getDeptList(String accessToken) {
        String uri = WxWorkConst.DEPT_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put(WxWorkConst.ACCESS_TOKEN,accessToken);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        log.info(response.toString());
        return getOrgLevelList(response);

    }

    /**
     * 功能描述:
     * 〈获取组织对象〉
     * @param response response
     * @author 蝉鸣
     */
    public List<OrgLevel> getOrgLevelList(JSONObject response) {
        List<OrgLevel> orgLevels = CollUtil.newArrayList();
        Object errcode = response.get(WxWorkConst.ERR_CODE);
        if(ObjectUtil.isEmpty(response) || ObjectUtil.isEmpty(errcode) || !errcode.toString().equals(NumberConst.NUM_0.toString())){
            return orgLevels;
        }
        Object deptObj = response.get(WxWorkConst.DEPARTMENT);
        JSONArray deptArray = JSONUtil.parseArray(deptObj.toString());
        for (Object object : deptArray) {
            JSONObject dept = JSONUtil.parseObj(object);
            OrgLevel orgLevel = new OrgLevel();
            orgLevel.setId(IdUtil.getSnowflakeNextId());
            orgLevel.setRootId(NumberConst.NUM_0.longValue());
            orgLevel.setParentId(Long.valueOf(dept.get(WxWorkConst.PARENT_ID).toString()));
            orgLevel.setLevelName(dept.get(WxWorkConst.NAME).toString());
            orgLevel.setThirdId(dept.get(WxWorkConst.ID).toString());
            orgLevel.setLevelFlag(LevelFlagEnum.DEPT.getValue());
            orgLevel.setLevelSource(this.sourceFlag().getValue());
            orgLevels.add(orgLevel);
        }
        //重置父ID
        if(CollUtil.isEmpty(orgLevels)){
            return orgLevels;
        }
        Map<String, Long> thirdMap = orgLevels.stream().collect(Collectors.toMap(OrgLevel::getThirdId, OrgLevel::getId, (v1, v2) -> v1));
        for (OrgLevel orgLevel : orgLevels) {
            if(thirdMap.containsKey(orgLevel.getParentId().toString())){
                orgLevel.setParentId(thirdMap.get(orgLevel.getParentId().toString()));
            }
        }
        return orgLevels;
    }

    /**
     * 功能描述:
     * 〈获取人员对象〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    public List<SyncUserBO> getUserList(String accessToken,List<OrgLevel> orgLevels) {
        List<SyncUserBO> userBOList = CollUtil.newArrayList();
        if(CollUtil.isEmpty(orgLevels)){
            return userBOList;
        }
        for (OrgLevel orgLevel : orgLevels) {
            List<SyncUserBO> userList = getUserList(accessToken, orgLevel.getThirdId());
            userBOList.addAll(userList);
        }
        return userBOList;
    }

    /**
     * 功能描述:
     * 〈获取人员对象〉
     * @param accessToken accessToken
     * @author 蝉鸣
     */
    public List<SyncUserBO> getUserList(String accessToken,String thirdId) {
        if(ObjectUtil.isEmpty(thirdId)){
            return CollUtil.newArrayList();
        }
        String uri = WxWorkConst.USER_URL;
        HashMap<String, Object> map = new HashMap<>();
        map.put(WxWorkConst.ACCESS_TOKEN,accessToken);
        map.put(WxWorkConst.DEPARTMENT_ID, thirdId);
        String serverUrl = HttpUtil.urlWithForm(uri, map, Charset.defaultCharset(), Boolean.FALSE);

        HashMap<String, Object> paramsMap = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(paramsMap, header);
        JSONObject response = restTemplate.postForObject(serverUrl, httpEntity, JSONObject.class);
        assert response != null;
        log.info(response.toString());
        return getUserBOList(response);
    }

    /**
     * 功能描述:
     * 〈获取人员对象〉
     * @param response response
     * @author 蝉鸣
     */
    public List<SyncUserBO> getUserBOList(JSONObject response) {
        List<SyncUserBO> userBOList = CollUtil.newArrayList();
        Object errcode = response.get(WxWorkConst.ERR_CODE);
        if(ObjectUtil.isEmpty(response) || ObjectUtil.isEmpty(errcode) || !errcode.toString().equals(NumberConst.NUM_0.toString())){
            return userBOList;
        }
        Object deptObj = response.get(WxWorkConst.USER_LIST);
        JSONArray deptArray = JSONUtil.parseArray(deptObj.toString());
        for (Object object : deptArray) {
            if(ObjectUtil.isEmpty(object)){
                continue;
            }
            JSONObject user = JSONUtil.parseObj(object);
            SyncUserBO userBO = new SyncUserBO();
            userBO.setUserId(getSafeStr(user,WxWorkConst.USERID));
            userBO.setUnionId(getSafeStr(user,WxWorkConst.USERID));
            userBO.setName(getSafeStr(user,WxWorkConst.NAME));
            userBO.setPhone(getSafeStr(user,WxWorkConst.MOBILE));
            userBO.setEmail(getSafeStr(user,WxWorkConst.EMAIL));
            userBO.setAvatar(getSafeStr(user,WxWorkConst.AVATAR));
            userBO.setSourceFlag(this.sourceFlag().getValue());
            userBO.setIsAdmin(AdminFlagEnum.COMMON.getValue());
            userBO.setLevelIds(getSafeStrList(user,WxWorkConst.DEPARTMENT));
            if(StrUtil.isNotBlank(userBO.getPhone())){
                userBOList.add(userBO);
            }
        }
        return userBOList;
    }

    /**
     * 功能描述:
     * 〈获取字符串〉
     * @param jsonObject jsonObject
     * @param key key
     * @author 蝉鸣
     */
    private String getSafeStr(JSONObject jsonObject, String key) {
        try {
            Object value = jsonObject.get(key);
            return ObjectUtil.isNotEmpty(value) ? value.toString() : StrUtil.EMPTY;
        } catch (Exception e) {
            return StrUtil.EMPTY;
        }
    }

    /**
     * 功能描述:
     * 〈获取字符串列表〉
     * @param jsonObject jsonObject
     * @param key key
     * @author 蝉鸣
     */
    private List<String> getSafeStrList(JSONObject jsonObject, String key) {
        try {
            Object value = jsonObject.get(key);
            if (ObjectUtil.isNotEmpty(value)) {
                return JSONUtil.toList(JSONUtil.parseArray(value), String.class);
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return CollUtil.newArrayList();
    }
}
