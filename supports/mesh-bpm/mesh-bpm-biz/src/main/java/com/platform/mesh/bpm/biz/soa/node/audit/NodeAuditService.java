package com.platform.mesh.bpm.biz.soa.node.audit;


import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;

import java.util.List;

/**
 * @description 节点通过工厂
 * @author 蝉鸣
 */
public interface NodeAuditService {

    /**
     * 功能描述:
     * 〈节点审批类型〉
     * @return 正常返回:{@link NodeAuditFlagEnum}
     * @author 蝉鸣
     */
    NodeAuditFlagEnum nodeAudit();

    /**
     * 功能描述:
     * 〈节点处理〉
     * @param auditPass auditPass
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    BpmInstNodePassBO check(List<Integer> auditPass);
}
