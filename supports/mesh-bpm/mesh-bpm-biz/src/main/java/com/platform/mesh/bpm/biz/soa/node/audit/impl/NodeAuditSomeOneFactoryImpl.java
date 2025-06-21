package com.platform.mesh.bpm.biz.soa.node.audit.impl;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.soa.node.audit.NodeAuditService;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.core.constants.NumberConst;
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
public class NodeAuditSomeOneFactoryImpl implements NodeAuditService {

    private final static Logger log = LoggerFactory.getLogger(NodeAuditSomeOneFactoryImpl.class);

    /**
     * 功能描述:
     * 〈节点通过类型〉
     * @return 正常返回:{@link NodePassEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeAuditFlagEnum nodeAudit() {
        return NodeAuditFlagEnum.SIGN_SOMEONE;
    }

    /**
     * 功能描述:
     * 〈节点校验是否通过:或签是指同一个审批节点设置多个人，只要其中任意一个人审批通过即可进入下一节点。〉
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
        //当其中一个进行审批了则视为节点已经处理
        if(initList.size() >= NumberConst.NUM_1) {
            nodeAuditBO.setCanNext(Boolean.TRUE);
        }else{
            nodeAuditBO.setCanNext(Boolean.FALSE);
        }

        List<Integer> passList = auditPass.stream().filter(audit -> {
            NodePassEnum enumByValue = BaseEnum.getEnumByValue(NodePassEnum.class, audit);
            //使用code对比
            return enumByValue.getCode().equals(NodePassEnum.PASS.getCode());
        }).toList();
        //当其中一个通过则视为为NodePassEnum.PASS
        if(passList.size() >= NumberConst.NUM_1) {
            nodeAuditBO.setCanPass(Boolean.TRUE);
        }else{
            nodeAuditBO.setCanPass(Boolean.FALSE);
        }
        return nodeAuditBO;
    }


}
