package com.platform.mesh.bpm.biz.soa.node.pass.factory;

import com.platform.mesh.bpm.biz.soa.node.pass.NodePassService;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.NodeRunService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 节点通过工厂
 * @author 蝉鸣
 */
@Service
public class NodePassFactory<T> implements InitializingBean {

    @Autowired
    private List<NodePassService<T>> nodePassServiceList;

    private final Map<NodePassEnum, NodePassService<T>> nodePassMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param nodePass nodePass
     * @return 正常返回:{@link NodePassService<T>}
     * @author 蝉鸣
     */
    public NodePassService<T> getNodePassService(NodePassEnum nodePass){
        return nodePassMaps.get(nodePass);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NodePassService<T> service : nodePassServiceList){
            nodePassMaps.put(service.nodePass(), service);
        }
    }
}
