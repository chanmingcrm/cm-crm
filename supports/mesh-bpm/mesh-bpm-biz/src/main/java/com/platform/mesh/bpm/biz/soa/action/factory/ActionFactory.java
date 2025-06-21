package com.platform.mesh.bpm.biz.soa.action.factory;

import com.platform.mesh.bpm.biz.soa.action.ActionService;
import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 动作工厂
 * @author 蝉鸣
 */
@Service
public class ActionFactory<T> implements InitializingBean {

    @Autowired
    private List<ActionService<T>> actionServiceList;

    private final Map<ActionTypeEnum, ActionService<T>> actionTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param actionType actionType
     * @return 正常返回:{@link ActionService<T>}
     * @author 蝉鸣
     */
    public ActionService<T> getActionService(ActionTypeEnum actionType){
        return actionTypeMaps.get(actionType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (ActionService<T> service : actionServiceList){
            actionTypeMaps.put(service.actionType(), service);
        }
    }
}
