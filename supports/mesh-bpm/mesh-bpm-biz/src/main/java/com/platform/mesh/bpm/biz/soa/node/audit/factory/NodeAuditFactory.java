package com.platform.mesh.bpm.biz.soa.node.audit.factory;

import com.platform.mesh.bpm.biz.soa.node.audit.NodeAuditService;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.NodePassService;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
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
public class NodeAuditFactory implements InitializingBean {

    @Autowired
    private List<NodeAuditService> nodeAuditServiceList;

    private final Map<NodeAuditFlagEnum, NodeAuditService> nodeAuditMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈根绝类型获取对应的流程实现〉
     * @param nodeAudit nodeAudit
     * @return 正常返回:{@link NodeAuditService}
     * @author 蝉鸣
     */
    public NodeAuditService getNodeAuditService(NodeAuditFlagEnum nodeAudit){
        return nodeAuditMaps.get(nodeAudit);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NodeAuditService service : nodeAuditServiceList){
            nodeAuditMaps.put(service.nodeAudit(), service);
        }
    }
}
