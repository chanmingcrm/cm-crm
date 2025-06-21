package com.platform.mesh.bpm.biz.soa.node.audit.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.soa.node.audit.NodeAuditService;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 定时节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeAuditJointFactoryImpl implements NodeAuditService {

    private final static Logger log = LoggerFactory.getLogger(NodeAuditJointFactoryImpl.class);

    /**
     * 功能描述:
     * 〈节点通过类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeAuditFlagEnum nodeAudit() {
        return NodeAuditFlagEnum.SIGN_JOINT;
    }

    /**
     * 功能描述:
     * 〈节点校验是否通过:会签是指多个审批人同时收到审批单，需要所有审批人都同意才能进入下一审批环节。〉
     * @param auditPass auditPass
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodePassBO check(List<Integer> auditPass) {
        BpmInstNodePassBO nodeAuditBO = new BpmInstNodePassBO();
        //转化枚举
        if(CollUtil.isEmpty(auditPass)) {
            return nodeAuditBO;
        }
        List<Integer> initList = auditPass.stream().filter(audit -> {
            NodePassEnum enumByValue = BaseEnum.getEnumByValue(NodePassEnum.class, audit);
            //使用code对比
            return !enumByValue.getCode().equals(NodePassEnum.INIT.getCode());
        }).toList();
        //当所有参与人员都参与审批了,无论意见是通过或者驳回则节点已经完成审批
        if(ObjectUtil.equals(initList.size(),auditPass.size())) {
            nodeAuditBO.setCanNext(Boolean.TRUE);
        }else{
            nodeAuditBO.setCanNext(Boolean.FALSE);
        }

        List<Integer> passList = auditPass.stream().filter(audit -> {
            NodePassEnum enumByValue = BaseEnum.getEnumByValue(NodePassEnum.class, audit);
            //使用code对比
            return enumByValue.getCode().equals(NodePassEnum.PASS.getCode());
        }).toList();
        //当所有参与人员都审批通过则auditPass为NodePassEnum.PASS
        if(ObjectUtil.equals(passList.size(),auditPass.size())) {
            nodeAuditBO.setCanPass(Boolean.TRUE);
        }else{
            nodeAuditBO.setCanPass(Boolean.FALSE);
        }
        return nodeAuditBO;
    }


}
