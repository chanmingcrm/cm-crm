package com.platform.mesh.bpm.biz.soa.process.run.factory;

import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 流程运行工厂
 * @author 蝉鸣
 */
@Service
public class ProcessRunFactory<T> implements InitializingBean {

    @Autowired
    private List<ProcessRunService<T>> processRunServiceList;

    private final Map<ProcessRunEnum, ProcessRunService<T>> processRunMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程实现〉
     * @param nodeRun nodeRun
     * @return 正常返回:{@link ProcessRunService<T>}
     * @author 蝉鸣
     */
    public ProcessRunService<T> getProcessRunService(ProcessRunEnum nodeRun){
        return processRunMaps.get(nodeRun);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (ProcessRunService<T> service : processRunServiceList){
            processRunMaps.put(service.processRun(), service);
        }
    }
}
