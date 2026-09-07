package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditAddDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditDelDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.manual.BpmInstNodeAuditServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点审批信息
 * @author 蝉鸣
 */
public interface IBpmInstNodeAuditService extends IService<BpmInstNodeAudit> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeAuditServiceManual}
     * @author 蝉鸣
     */
    BpmInstNodeAuditServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈根据实例节点ID获取节点审批信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstNodeSub>}
     * @author 蝉鸣
     */
    List<BpmInstNodeAudit> selectNodeAuditsByNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈处理当前审批节点〉
     * @param instNodeId instNodeId
     * @param auditDataId auditDataId
     * @param auditPass auditPass
     * @author 蝉鸣
     */
    Boolean handleNodeAudit(Long instNodeId,Long auditDataId,Integer auditPass);

    /**
     * 功能描述:
     * 〈校验当前审批节点是否通过〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    BpmInstNodePassBO checkInstNodeAuditPass(Long instNodeId);

    /**
     * 功能描述:
     * 〈根据审批数据类型获取审批人信息〉
     * @param auditDataType auditDataType
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    List<Long> getAuditDataIds(Integer auditDataType, String auditDataIds);

    /**
     * 功能描述:
     * 〈添加当前节点审批信息〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean addInstNodeAudit(BpmInstNodeAuditAddDTO addDTO);

    /**
     * 功能描述:
     * 〈删除当前节点审批信息〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean delInstNodeAudit(BpmInstNodeAuditDelDTO delDTO);
}

