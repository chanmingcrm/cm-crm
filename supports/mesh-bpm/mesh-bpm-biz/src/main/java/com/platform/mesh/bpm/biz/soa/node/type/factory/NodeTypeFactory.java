package com.platform.mesh.bpm.biz.soa.node.type.factory;

import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 节点类型工厂
 * @author 蝉鸣
 */
@Service
public class NodeTypeFactory<T> implements InitializingBean {

    @Autowired
    private List<NodeTypeService<T>> nodeTypeServiceList;

    private final Map<NodeTypeEnum, NodeTypeService<T>> nodeTypeMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程实现〉
     * @param nodeType nodeType
     * @return 正常返回:{@link NodeTypeService<T>}
     * @author 蝉鸣
     */
    public NodeTypeService<T> getNodeService(NodeTypeEnum nodeType){
        return nodeTypeMaps.get(nodeType);
    }


    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NodeTypeService<T> service : nodeTypeServiceList){
            nodeTypeMaps.put(service.nodeType(), service);
        }
    }
}
