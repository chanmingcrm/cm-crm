package com.platform.mesh.bpm.biz.logic.type.factory;

import com.platform.mesh.bpm.biz.logic.type.LogicTypeService;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 逻辑类型工厂
 * @author 蝉鸣
 */
@Service
public class LogicTypeFactory implements InitializingBean {

    @Autowired
    private List<LogicTypeService> logicTypeService;

    private final Map<LogicTypeEnum, LogicTypeService> futureTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param logicType logicType
     * @return 正常返回:{@link LogicTypeService}
     * @author 蝉鸣
     */
    public LogicTypeService getLogicTypeService(LogicTypeEnum logicType){
        return futureTypeMaps.get(logicType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (LogicTypeService service : logicTypeService){
            futureTypeMaps.put(service.logicType(), service);
        }
    }
}
