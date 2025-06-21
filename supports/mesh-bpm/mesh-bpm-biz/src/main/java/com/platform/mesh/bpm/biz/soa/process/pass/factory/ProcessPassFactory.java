package com.platform.mesh.bpm.biz.soa.process.pass.factory;

import com.platform.mesh.bpm.biz.soa.process.pass.ProcessPassService;
import com.platform.mesh.bpm.biz.soa.process.pass.enums.ProcessPassEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 流程通过工厂
 * @author 蝉鸣
 */
@Service
public class ProcessPassFactory<T> implements InitializingBean {

    @Autowired
    private List<ProcessPassService<T>> processPassServiceList;

    private final Map<ProcessPassEnum, ProcessPassService<T>> processPassMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param processPass processPass
     * @return 正常返回:{@link ProcessPassService<T>}
     * @author 蝉鸣
     */
    public ProcessPassService<T> getProcessPassService(ProcessPassEnum processPass){
        return processPassMaps.get(processPass);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (ProcessPassService<T> service : processPassServiceList){
            processPassMaps.put(service.processPass(), service);
        }
    }
}
