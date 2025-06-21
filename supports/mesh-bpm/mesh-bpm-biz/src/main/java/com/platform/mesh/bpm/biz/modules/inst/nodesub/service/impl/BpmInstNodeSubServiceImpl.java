package com.platform.mesh.bpm.biz.modules.inst.nodesub.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.mapper.BpmInstNodeSubMapper;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.manual.BpmInstNodeSubServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import com.platform.mesh.bpm.biz.soa.process.run.factory.ProcessRunFactory;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点子项信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstNodeSubServiceImpl extends ServiceImpl<BpmInstNodeSubMapper, BpmInstNodeSub> implements IBpmInstNodeSubService {


    @Autowired
    private BpmInstNodeSubServiceManual bpmTempNodeServiceManual;

    @Autowired
    private ProcessRunFactory<BpmInstProcess> processRunFactory;


    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeSubServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeSubServiceManual getServiceManual() {
        return bpmTempNodeServiceManual;
    }


    /**
     * 功能描述:
     * 〈根据实例节点ID获取节点子项〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstNodeSub>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNodeSub> selectNodeSubsByNodeId(Long instNodeId) {
        return this.lambdaQuery()
                .eq(BpmInstNodeSub::getInstNodeId,instNodeId)
                .list();
    }

    /**
     * 功能描述:
     * 〈根据实例流程ID获取节点子项〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstNodeSub}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeSub selectNodeSubByChildProcessId(Long instProcessId) {
        return this.lambdaQuery()
                .eq(BpmInstNodeSub::getInstChildProcessId, instProcessId)
                .one();
    }

    /**
     * 功能描述:
     * 〈运行当前节点下的子项流程〉
     * @param instNode instNode
     * @return 正常返回:{@link Integer}
     * @author 蝉鸣
     */
    @Override
    public Integer runNodeSub(BpmInstNode instNode) {
        //当前节点运行状态
        Integer nodeRunFlag = NodeRunEnum.RUNNING.getValue();
        //查询当前节点是否有子流程
        List<BpmInstNodeSub> nodeSubs = selectNodeSubsByNodeId(instNode.getId()).stream()
                .filter(item-> !ProcessRunEnum.END.getValue().equals(item.getChildProcessRunFlag()))
                .toList();
        if(CollUtil.isEmpty(nodeSubs)){
            return NodeRunEnum.END.getValue();
        }
        //运行节点子项
        nodeSubs.forEach(nodeSub->{
            //根据流程状态执行节点子项流程
            ProcessRunEnum processRunEnum = BaseEnum.getEnumByValue(ProcessRunEnum.class, nodeSub.getChildProcessRunFlag());
            IBpmInstProcessService bpmInstProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
            BpmInstProcess subProcess = bpmInstProcessService.getById(nodeSub.getInstChildProcessId());
            ProcessRunService<BpmInstProcess> processRunService = processRunFactory.getProcessRunService(processRunEnum);
            FutureHandleUtil.runWithResult(CollUtil.newArrayList(subProcess), processRunService::handle);
        });
        //再次查询当前节点是否有未执行子流程
        nodeSubs = selectNodeSubsByNodeId(instNode.getId()).stream()
                .filter(item-> !ProcessRunEnum.END.getValue().equals(item.getChildProcessRunFlag()))
                .toList();
        if(CollUtil.isEmpty(nodeSubs)){
            nodeRunFlag = NodeRunEnum.END.getValue();
        }
        return nodeRunFlag;
    }
}

