package com.platform.mesh.bpm.biz.soa.process.type.factory;

import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 流程类型工厂
 * @author 蝉鸣
 */
@Service
public class ProcessTypeFactory implements InitializingBean {

    @Autowired
    private List<ProcessTypeService> processTypeServiceList;

    private final Map<ProcessTypeEnum, ProcessTypeService> ProcessTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param processType processType
     * @return 正常返回:{@link ProcessTypeService}
     * @author 蝉鸣
     */
    public ProcessTypeService getProcessTypeService(ProcessTypeEnum processType){
        return ProcessTypeMaps.get(processType);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (ProcessTypeService service : processTypeServiceList){
            ProcessTypeMaps.put(service.processType(), service);
        }
    }
}
