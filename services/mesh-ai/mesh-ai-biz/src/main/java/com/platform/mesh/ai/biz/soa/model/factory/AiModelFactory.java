package com.platform.mesh.ai.biz.soa.model.factory;

import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
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
public class AiModelFactory implements InitializingBean {

    @Autowired
    private List<AiModelService> aiModelServiceList;

    private final Map<ModelFlagEnum, AiModelService> aiModelMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的AI实现〉
     * @param modelFlagEnum aiModelEnum
     * @return 正常返回:{@link AiModelService}
     * @author 蝉鸣
     */
    public AiModelService getAiModelService(ModelFlagEnum modelFlagEnum){
        return aiModelMaps.get(modelFlagEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (AiModelService service : aiModelServiceList){
            aiModelMaps.put(service.aiModel(), service);
        }
    }
}
