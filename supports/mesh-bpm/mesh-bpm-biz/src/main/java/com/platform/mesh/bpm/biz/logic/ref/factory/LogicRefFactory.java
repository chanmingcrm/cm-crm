package com.platform.mesh.bpm.biz.logic.ref.factory;

import com.platform.mesh.bpm.biz.logic.ref.LogicRefService;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 逻辑关系工厂
 * @author 蝉鸣
 */
@Service
public class LogicRefFactory implements InitializingBean {

    @Autowired
    private List<LogicRefService> logicRefServiceList;

    private final Map<LogicRefEnum, LogicRefService> futureTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程实现〉
     * @param refType refType
     * @return 正常返回:{@link LogicRefService}
     * @author 蝉鸣
     */
    public LogicRefService getLogicRefService(LogicRefEnum refType){
        return futureTypeMaps.get(refType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (LogicRefService service : logicRefServiceList){
            futureTypeMaps.put(service.refType(), service);
        }
    }
}
