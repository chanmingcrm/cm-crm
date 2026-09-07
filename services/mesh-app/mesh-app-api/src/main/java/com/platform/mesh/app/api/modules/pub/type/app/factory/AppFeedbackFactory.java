package com.platform.mesh.app.api.modules.pub.type.app.factory;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.app.api.modules.pub.type.app.AppFeedbackService;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 审批回调工厂
 * @author 蝉鸣
 */
@Service
public class AppFeedbackFactory implements InitializingBean {

    @Autowired(required = false)
    private List<AppFeedbackService> actionServiceList;

    @Getter
    private final Map<String, AppFeedbackService> actionTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程回调实现〉
     * @param actionName actionName
     * @return 正常返回:{@link AppFeedbackService}
     * @author 蝉鸣
     */
    public AppFeedbackService getFeedbackService(String actionName){
        return actionTypeMaps.get(actionName);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        if(CollUtil.isEmpty(actionServiceList)){
            return;
        }
        for (AppFeedbackService service : actionServiceList){
            actionTypeMaps.put(service.actionName(), service);
        }
    }
}
