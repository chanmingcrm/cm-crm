package com.platform.mesh.bpm.biz.soa.node.run.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.soa.node.run.NodeRunService;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.factory.ProcessTypeFactory;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 定时节点工厂实现
 * @author 蝉鸣
 */
@Service
public class NodeRunEndFactoryImpl implements NodeRunService<BpmInstNode> {

    private final static Logger log = LoggerFactory.getLogger(NodeRunEndFactoryImpl.class);

    /**
     * 功能描述:
     * 〈节点运行类型〉
     * @return 正常返回:{@link NodeTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public NodeRunEnum nodeRun() {
        return NodeRunEnum.END;
    }

    /**
     * 功能描述:
     * 〈单节点处理〉
     * @param instNode instNode
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode handle(BpmInstNode instNode) {
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        //执行当前节点流出
        instNodeService.getServiceManual().outInstNode(instNode);
        //节点结束移入历史记录
        ProcessTypeFactory processTypeFactory = SpringContextHolderUtil.getBean(ProcessTypeFactory.class);
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //初始化实例节点
            processTypeService.addHist(instNode);
        }
        //设置下一节点运行中
        List<BpmInstNode> nextNodes = instNodeService.selectNextNode(CollUtil.newArrayList(instNode));
        if(CollUtil.isEmpty(nextNodes)){
            return instNode;
        }
        nextNodes.forEach(item->item.setRunFlag(NodeRunEnum.RUNNING.getValue()));
        instNodeService.updateBatchById(nextNodes);
        //发送消息
        //发送审批回调消息
        instNodeService.sendBpmMsg(instNode);
        return instNode;
    }


}
