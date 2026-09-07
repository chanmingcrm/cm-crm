package com.platform.mesh.bpm.biz.soa.node.auditdata.factory;

import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 节点人员处理类型工厂
 * @author 蝉鸣
 */
@Service
public class NodeAuditDataFactory implements InitializingBean {

    @Autowired
    private List<NodeAuditDataService> nodeAuditDataServiceList;

    private final Map<NodeAuditDataTypeEnum, NodeAuditDataService> nodeAuditDataMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根据类型获取对应的流程实现〉
     * @param nodeAuditData nodeAuditData
     * @return 正常返回:{@link NodeAuditDataService}
     * @author 蝉鸣
     */
    public NodeAuditDataService getNodeAuditService(NodeAuditDataTypeEnum nodeAuditData){
        return nodeAuditDataMaps.get(nodeAuditData);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NodeAuditDataService service : nodeAuditDataServiceList){
            nodeAuditDataMaps.put(service.nodeAuditData(), service);
        }
    }
}
