package com.platform.mesh.ai.biz.soa.store.factory;

import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description AI平台工厂
 * @author 蝉鸣
 */
@Service
public class AiStoreFactory implements InitializingBean {

    @Autowired
    private List<AiStoreService> aiStoreServiceList;

    private final Map<StoreFlagEnum, AiStoreService> aiStoreMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的AI实现〉
     * @param storeFlagEnum storeFlagEnum
     * @return 正常返回:{@link AiStoreService}
     * @author 蝉鸣
     */
    public AiStoreService getAiStoreService(StoreFlagEnum storeFlagEnum){
        return aiStoreMaps.get(storeFlagEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (AiStoreService service : aiStoreServiceList){
            aiStoreMaps.put(service.aiStore(), service);
        }
    }
}
