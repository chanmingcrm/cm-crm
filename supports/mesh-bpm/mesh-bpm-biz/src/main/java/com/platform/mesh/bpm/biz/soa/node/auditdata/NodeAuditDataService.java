package com.platform.mesh.bpm.biz.soa.node.auditdata;


import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessAuditVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.domain.vo.NodeAuditDataVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @description 节点通过工厂
 * @author 蝉鸣
 */
public interface NodeAuditDataService {

    /**
     * 功能描述:
     * 〈节点审批类型〉
     * @return 正常返回:{@link NodeAuditDataTypeEnum}
     * @author 蝉鸣
     */
    NodeAuditDataTypeEnum nodeAuditData();

    /**
     * 功能描述:
     * 〈节点处理〉
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<Long> getAuditDataToUserIds(List<Long> auditDataIds);

    /**
     * 功能描述:
     * 〈获取审批数据范围〉
     * @param processTodoBO processTodoBO
     * @author 蝉鸣
     */
    void getAuditDataScopeBO(BpmInstProcessTodoBO processTodoBO);

    /**
     * 功能描述:
     * 〈节点处理VO对象〉
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    List<NodeAuditDataVO> getAuditDataVO(List<Long> auditDataIds);

    /**
     * 功能描述:
     * 〈获取当前账户节点处理权限〉
     * @param auditDataIds auditDataIds
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstProcessAuditVO>}
     * @author 蝉鸣
     */
    Boolean getCanAudit(List<Long> auditDataIds,Long accountId);

    /**
     * 功能描述:
     * 〈获取可以审批的节点〉
     * @param nodeAudits nodeAudits
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstNodeAudit>}
     * @author 蝉鸣
     */
    List<BpmInstNodeAudit> getCanAuditNode(List<BpmInstNodeAudit> nodeAudits, Long accountId);
}
