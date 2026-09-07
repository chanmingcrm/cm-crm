package com.platform.mesh.upms.biz.captcha.service.impl;

import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.spring.plugins.RedisResourceStore;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.captcha.tianai.constant.CaptchaConst;
import com.platform.mesh.core.constants.SymbolConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @description 资源存储
 * @author 蝉鸣
 */
@Service
public class RedissonResourceStore extends RedisResourceStore {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public RedissonResourceStore(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void init(ImageCaptchaResourceManager resourceManager) {
    }

    /**
     * 功能描述:
     * 〈添加资源〉
     * @param type type
     * @param resource resource
     * @author 蝉鸣
     */
    @Override
    public void addResource(String type, Resource resource) {
        String jsonStr = JSONUtil.toJsonStr(resource);
        addHashObject(CaptchaConst.RESOURCE,type, jsonStr);
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @param type type
     * @param template template
     * @author 蝉鸣
     */
    @Override
    public void addTemplate(String type, ResourceMap template) {
        String jsonStr = JSONUtil.toJsonStr(template);
        addHashObject(CaptchaConst.TEMPLATE,type, jsonStr);
    }

    /**
     * 功能描述:
     * 〈添加随机获取资源〉
     * @param type type
     * @param tag tag
     * @author 蝉鸣
     */
    @Override
    public List<Resource> randomGetResourceByTypeAndTag(String type, String tag, Integer quantity) {
        List<Object> values = redisTemplate.opsForHash().values(CaptchaConst.RESOURCE.concat(SymbolConst.COLON).concat(type));
        //随机获取
        int random = new Random().nextInt(values.size());
        return CollUtil.newArrayList(JSONUtil.toBean(JSONUtil.toJsonStr(values.get(random)),Resource.class));
    }

    /**
     * 功能描述:
     * 〈添加随机获取模板〉
     * @param type type
     * @param tag tag
     * @author 蝉鸣
     */
    @Override
    public List<ResourceMap> randomGetTemplateByTypeAndTag(String type, String tag, Integer quantity) {
        List<Object> values = redisTemplate.opsForHash().values(CaptchaConst.TEMPLATE.concat(SymbolConst.COLON).concat(type));
        int random = new Random().nextInt(values.size());
        return CollUtil.newArrayList(JSONUtil.toBean(JSONUtil.toJsonStr(values.get(random)),ResourceMap.class));
    }

    /**
     * 功能描述:
     * 〈清除所有模板〉
     * @author 蝉鸣
     */
    @Override
    public void clearAllTemplates() {
        redisTemplate.delete(CaptchaConst.TEMPLATE);
    }

    /**
     * 功能描述:
     * 〈清除所有资源〉
     * @author 蝉鸣
     */
    @Override
    public void clearAllResources() {
        redisTemplate.delete(CaptchaConst.RESOURCE);
    }

    /**
     * 功能描述:
     * 〈保存信息〉
     * @param type type
     * @param jsonStr jsonStr
     * @author 蝉鸣
     */
    private void addHashObject(String resource,String type,String jsonStr) {
        String hashKey;
        if(type.contains(SymbolConst.COLON)){
            List<String> list = StrUtil.split(type, SymbolConst.COLON).stream().map(StrUtil::trim).toList();
            type = CollUtil.getFirst(list);
            hashKey = CollUtil.getLast(list);
        }else{
            hashKey = type;
        }
        String key = resource.concat(SymbolConst.COLON).concat(type);
        redisTemplate.opsForHash().put(key, hashKey, jsonStr);
    }
}
