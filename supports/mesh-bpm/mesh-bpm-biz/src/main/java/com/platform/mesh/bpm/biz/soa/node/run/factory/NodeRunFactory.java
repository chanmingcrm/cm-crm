package com.platform.mesh.bpm.biz.soa.node.run.factory;

import com.platform.mesh.bpm.biz.soa.node.run.NodeRunService;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 节点运行工厂
 * @author 蝉鸣
 */
@Service
public class NodeRunFactory<T> implements InitializingBean {

    @Autowired
    private List<NodeRunService<T>> nodeRunServiceList;

    private final Map<NodeRunEnum, NodeRunService<T>> nodeRunMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param nodeRun nodeRun
     * @return 正常返回:{@link NodeRunService<T>}
     * @author 蝉鸣
     */
    public NodeRunService<T> getNodeRunService(NodeRunEnum nodeRun){
        return nodeRunMaps.get(nodeRun);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NodeRunService<T> service : nodeRunServiceList){
            nodeRunMaps.put(service.nodeRun(), service);
        }
    }
}
