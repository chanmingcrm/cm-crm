package com.platform.mesh.bpm.biz.soa.node.type;


import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;

/**
 * @description 节点工厂
 * @author 蝉鸣
 */
public interface NodeTypeService<T> {

    /**
     * 功能描述:
     * 〈节点类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    NodeTypeEnum nodeType();

    /**
     * 功能描述:
     * 〈节点处理〉
     * @param classType classType
     * @return 正常返回:{@link T}
     * @author 蝉鸣
     */
    T handle(T classType);

    /**
     * 功能描述:
     * 〈手动处理节点通过状态〉
     * @param instNode instNode
     * @param auditAccountId auditAccountId
     * @param auditPass auditPass
     * @author 蝉鸣
     */
    BpmInstNodePassBO passInstNode(BpmInstNode instNode, Long auditAccountId, Integer auditPass);
}
