package com.platform.mesh.bpm.biz.modules.inst.node.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.service.IBpmInstLineService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeLineVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVarVO;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.vo.BpmInstVariableVO;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.IBpmInstVariableService;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.domain.po.BpmInstVarValue;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.IBpmInstVarValueService;
import com.platform.mesh.bpm.biz.pipe.flow.executor.FlowExecutor;
import com.platform.mesh.bpm.biz.pipe.flow.factory.FlowPipeDefault;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.NodeTypeService;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.type.factory.NodeTypeFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.utils.function.FutureHandleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstNodeServiceManual {

    @Autowired
    private IBpmInstLineService bpmInstLineService;

    @Autowired
    private IBpmInstVariableService bpmInstVariableService;

    @Autowired
    private IBpmInstVarValueService bpmInstVarValueService;

    @Autowired
    private NodeTypeFactory<BpmInstNode> nodeTypeFactory;

    @Autowired
    private FlowExecutor flowExecutor;

    @Autowired
    private FlowPipeDefault flowPipeDefault;

    /**
     * 功能描述:
     * 〈执行节点〉
     * @param bpmInstNodes bpmInstNodes
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    public List<BpmInstNode> handleInstNode(List<BpmInstNode> bpmInstNodes) {
        if(CollUtil.isEmpty(bpmInstNodes)){
            return bpmInstNodes;
        }
        //将节点按照类型分类
        Map<Integer, List<BpmInstNode>> instNodeMap = bpmInstNodes
                .stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.getNodeFlag()))
                .collect(Collectors.groupingBy(BpmInstNode::getNodeFlag));
        //执行节点
        instNodeMap.forEach((key,value)->{
            //执行节点信息，可直接执行的不需要将节点再放入工厂中

            //按照不同的分类调用不同的工厂服务
            NodeTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeTypeEnum.class, key);
            NodeTypeService<BpmInstNode> nodeTypeService = nodeTypeFactory.getNodeService(enumByValue);
            FutureHandleUtil.runWithResult(value, nodeTypeService::handle);
        });
        //返回执行结束的节点信息
        return bpmInstNodes.stream()
                .filter(item-> NodeRunEnum.END.getValue().equals(item.getRunFlag()))
                .collect(Collectors.toList());
    }

    /**
     * 功能描述:
     * 〈进入节点〉
     * @param bpmInstNode bpmInstNode
     * @author 蝉鸣
     */
    public void inInstNode(BpmInstNode bpmInstNode){
        //执行加载事件
        flowExecutor.initExecutor(flowPipeDefault, bpmInstNode.getId());
    }

    /**
     * 功能描述:
     * 〈节点中〉
     * @param bpmInstNode bpmInstNode
     * @author 蝉鸣
     */
    public void midInstNode(BpmInstNode bpmInstNode) {
        //执行加载事件
        flowExecutor.midExecutor(flowPipeDefault, bpmInstNode.getId());
    }

    /**
     * 功能描述:
     * 〈流出节点〉
     * @param bpmInstNode bpmInstNode
     * @author 蝉鸣
     */
    public void outInstNode(BpmInstNode bpmInstNode){
        //执行结束动作
        flowExecutor.endExecutor(flowPipeDefault, bpmInstNode.getId());
    }

    /**
     * 功能描述:
     * 〈查询一下批节点ids〉
     * @param bpmInstNodes bpmInstNodes
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public List<Long> selectNextNodeIds(List<BpmInstNode> bpmInstNodes) {
        //只查询同级连线下一级数据
        List<Long> outNodes = CollUtil.newArrayList();
        if(CollUtil.isEmpty(bpmInstNodes)){
            return outNodes;
        }
        //查询执行中节点的连线
        List<Long> instNodeIds = bpmInstNodes.stream().map(BpmInstNode::getId).collect(Collectors.toList());
        if(CollUtil.isEmpty(instNodeIds)){
            return outNodes;
        }
        //查询节点下有哪些线
        Map<Long, List<BpmInstLine>> inLineMap = new HashMap<>();
        List<BpmInstLine> outLines = bpmInstLineService.lambdaQuery()
                .in(BpmInstLine::getInstInNodeId,instNodeIds)
                .list();
        //查询可通过线
        outLines = outLines.stream().filter(item->bpmInstLineService.checkLinePass(item.getId())).collect(Collectors.toList());
        if(CollUtil.isEmpty(outLines)){
            return outNodes;
        }else{
            inLineMap = outLines.stream().collect(Collectors.groupingBy(BpmInstLine::getInstInNodeId));
        }
        //批量节点按照单节点处理，因为存在多线协同处理
        for (BpmInstNode bpmInstNode : bpmInstNodes) {
            //有下一步连线
            if(     CollUtil.isNotEmpty(inLineMap)
                    && inLineMap.containsKey(bpmInstNode.getId())
                    && CollUtil.isNotEmpty(inLineMap.get(bpmInstNode.getId()))
            ){
                List<BpmInstLine> bpmInstLines = inLineMap.get(bpmInstNode.getId());
                List<Long> outList = bpmInstLines.stream().map(BpmInstLine::getInstOutNodeId).toList();
                outNodes.addAll(outList);
            }
        }
        //获取线的出线节点
        return outNodes;
    }

    /**
     * 功能描述:
     * 〈设置线上变量值〉
     * @param varMap varMap
     * @author 蝉鸣
     */
    public void setLineVarValue(BpmInstNode instNode, Map<String, String> varMap) {
        //删除节点旧数据
        clearNodeVar(instNode);
        //保存新的变量数据
        List<BpmInstVarValue> varValues = CollUtil.newArrayList();
        varMap.forEach((item,value)->{
            BpmInstVarValue instVarValue = new BpmInstVarValue();
            instVarValue.setInstProcessId(instNode.getInstProcessId());
            instVarValue.setInstNodeId(instNode.getId());
            instVarValue.setVariableName(item);
            instVarValue.setVariableValue(value);
            varValues.add(instVarValue);
        });
        bpmInstVarValueService.saveBatch(varValues);
    }

    /**
     * 功能描述:
     * 〈获取当前节点需要的变量信息〉
     * @param instNode instNode
     * @return 正常返回:{@link BpmInstNodeVarVO}
     * @author 蝉鸣
     */
    public BpmInstNodeVarVO getInstNodeVar(BpmInstNode instNode) {
        BpmInstNodeVarVO nodeVarVO = BeanUtil.copyProperties(instNode, BpmInstNodeVarVO.class);
        //获取当前节点所有出线
        List<BpmInstLine> outLines = bpmInstLineService.lambdaQuery()
                .eq(BpmInstLine::getInstInNodeId,instNode.getId())
                .list();
        if (CollUtil.isEmpty(outLines)) {
            return nodeVarVO;
        }
        List<Long> lineIds = outLines.stream().map(BpmInstLine::getId).toList();
        //获取当前出线上所需要的变量
        Map<Long, List<BpmInstVariable>> lineVarMap = new HashMap<>();
        List<String> allVars = CollUtil.newArrayList();
        List<BpmInstVariable> bpmInstVariables = bpmInstVariableService.selectVariableByInstLineIds(lineIds);
        //变量根据所在线分组
        if (CollUtil.isNotEmpty(bpmInstVariables)) {
            lineVarMap = bpmInstVariables.stream().collect(Collectors.groupingBy(BpmInstVariable::getInstLineId));
            allVars = bpmInstVariables.stream().map(BpmInstVariable::getVariableName).toList();
        }
        //封装数据
        Map<Long, List<BpmInstVariable>> finalLineVarMap = lineVarMap;
        List<BpmInstNodeLineVO> nodeLineVOS = outLines.stream().map(line -> {
            BpmInstNodeLineVO nodeLineVO = BeanUtil.copyProperties(instNode, BpmInstNodeLineVO.class);
            if(ObjectUtil.isNotEmpty(finalLineVarMap)&& finalLineVarMap.containsKey(line.getId()) ){
                List<BpmInstVariable> instVariables = finalLineVarMap.get(line.getId());
                List<BpmInstVariableVO> instVariableVOS = BeanUtil.copyToList(instVariables, BpmInstVariableVO.class);
                nodeLineVO.setNodeLineVarVOS(instVariableVOS);
            }
            return nodeLineVO;
        }).toList();
        //所有变量
        nodeVarVO.setNodeOutVars(allVars);
        //所有线附带变量
        nodeVarVO.setNodeOutLineVOS(nodeLineVOS);
        return nodeVarVO;
    }

    /**
     * 功能描述:
     * 〈重置节点变量〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    public void clearNodeVar(BpmInstNode instNode) {
        bpmInstVarValueService.lambdaUpdate()
                .eq(BpmInstVarValue::getInstProcessId,instNode.getInstProcessId())
                .eq(BpmInstVarValue::getInstNodeId,instNode.getId())
                .remove();
    }

    /**
     * 功能描述:
     * 〈审批节点〉
     * @param instNode instNode
     * @param auditAccountId auditAccountId
     * @param auditPass auditPass
     * @author 蝉鸣
     */
    public BpmInstNodePassBO passInstNode(BpmInstNode instNode, Long auditAccountId, Integer auditPass) {
        //按照不同的分类调用不同的工厂服务
        NodeTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeTypeEnum.class, instNode.getNodeFlag());
        NodeTypeService<BpmInstNode> nodeTypeService = nodeTypeFactory.getNodeService(enumByValue);
        return nodeTypeService.passInstNode(instNode, auditAccountId, auditPass);
    }
}

