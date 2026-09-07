package com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditAddDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.dto.BpmInstNodeAuditDelDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.exception.InstNodeAuditExceptionEnum;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.mapper.BpmInstNodeAuditMapper;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.manual.BpmInstNodeAuditServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.soa.process.run.factory.ProcessRunFactory;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.AuditPassEnum;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点审批信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstNodeAuditServiceImpl extends ServiceImpl<BpmInstNodeAuditMapper, BpmInstNodeAudit> implements IBpmInstNodeAuditService {


    @Autowired
    private BpmInstNodeAuditServiceManual bpmInstNodeAuditServiceManual;

    @Autowired
    private ProcessRunFactory<BpmInstProcess> processRunFactory;


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeAuditServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeAuditServiceManual getServiceManual() {
        return bpmInstNodeAuditServiceManual;
    }

    /**
     * 功能描述:
     * 〈根据实例节点ID获取节点审批信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstNodeSub>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNodeAudit> selectNodeAuditsByNodeId(Long instNodeId) {
        return this.lambdaQuery()
                .eq(BpmInstNodeAudit::getInstNodeId, instNodeId)
                .list();
    }

    /**
     * 功能描述:
     * 〈处理当前审批节点〉
     * @param instNodeId instNodeId
     * @param auditAccountId auditAccountId
     * @param auditPass auditPass
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean handleNodeAudit(Long instNodeId, Long auditAccountId, Integer auditPass) {
        //查询当前审批所有处理信息
        List<BpmInstNodeAudit> nodeAudits = this.lambdaQuery()
                .eq(BpmInstNodeAudit::getInstNodeId, instNodeId)
                .list();
        if(CollUtil.isEmpty(nodeAudits)){
            return Boolean.FALSE;
        }
        //获取可以审批的节点信息
        List<BpmInstNodeAudit> nodeAudit = bpmInstNodeAuditServiceManual.getNodeCanAudit(nodeAudits,auditAccountId);
        if(CollUtil.isEmpty(nodeAudit)){
            return Boolean.FALSE;
        }
        List<BpmInstNodeAudit> audits = nodeAudit.stream().map(audit -> audit.setAuditPass(auditPass)).toList();
        //更新审批信息
        this.updateBatchById(audits);
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈校验当前审批节点是否通过〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodePassBO checkInstNodeAuditPass(Long instNodeId) {
        List<BpmInstNodeAudit> nodeAudits = this.lambdaQuery()
                .eq(BpmInstNodeAudit::getInstNodeId, instNodeId)
                .orderByDesc(BpmInstNodeAudit::getCreateTime)
                .list();
        return bpmInstNodeAuditServiceManual.checkInstNodeAuditPass(nodeAudits);
    }

    /**
     * 功能描述:
     * 〈根据审批数据类型获取审批人信息〉
     * @param auditDataType auditDataType
     * @param auditDataIds auditDataIds
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    @Override
    public List<Long> getAuditDataIds(Integer auditDataType, String auditDataIds) {
        List<Long> ids;
        if(CharSequenceUtil.isEmpty(auditDataIds)){
            ids = CollUtil.newArrayList();
        }else{
            ids = Arrays.stream(auditDataIds.split(SymbolConst.COMMA)).filter(NumberUtil::isNumber).map(Long::parseLong).distinct().toList();
        }
        return bpmInstNodeAuditServiceManual.getAuditDataIds(auditDataType,ids);
    }

    /**
     * 功能描述:
     * 〈添加当前节点审批信息〉
     * @param addDTO addDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Boolean addInstNodeAudit(BpmInstNodeAuditAddDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getAuditDataIds())){
            return Boolean.FALSE;
        }
        BpmInstNode instNode = bpmInstNodeAuditServiceManual.getInstNodeById(addDTO.getInstNodeId());
        if(ObjectUtil.isEmpty(instNode)){
            throw InstNodeAuditExceptionEnum.ADD_NO_INVALID_INST_NODE.getBaseException();
        }
        List<BpmInstNodeAudit> nodeAudits = CollUtil.newArrayList();
        int order = NumberConst.NUM_0;
        addDTO.getAuditDataIds().forEach(auditDataId -> {
            BpmInstNodeAudit nodeAudit = new BpmInstNodeAudit();
            BeanUtil.copyProperties(instNode,nodeAudit, ObjFieldUtil.ignoreDefault());
            nodeAudit.setAuditPass(AuditPassEnum.INIT.getValue());
            nodeAudit.setAuditOrder(order+NumberConst.NUM_1);
            nodeAudit.setAuditDataId(auditDataId);
            nodeAudit.setInstNodeId(instNode.getId());
            nodeAudit.setScopeUserId(instNode.getScopeUserId());
            nodeAudit.setScopeOrgId(instNode.getScopeOrgId());
            nodeAudits.add(nodeAudit);
        });
        this.saveBatch(nodeAudits);
        //如果当前节点是运行中，则发送消息给审批人提醒审批
        bpmInstNodeAuditServiceManual.sendAuditMsg(instNode, addDTO.getAuditDataIds());
        return Boolean.TRUE;
    }

    /**
     * 功能描述:
     * 〈删除当前节点审批信息〉
     * @param delDTO delDTO
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean delInstNodeAudit(BpmInstNodeAuditDelDTO delDTO) {
        if(CollUtil.isEmpty(delDTO.getAuditDataIds())){
            return Boolean.FALSE;
        }
        this.lambdaUpdate()
                .eq(BpmInstNodeAudit::getInstNodeId, delDTO.getInstNodeId())
                .in(BpmInstNodeAudit::getAuditDataId, delDTO.getAuditDataIds())
                .remove();
        return Boolean.TRUE;
    }
}

