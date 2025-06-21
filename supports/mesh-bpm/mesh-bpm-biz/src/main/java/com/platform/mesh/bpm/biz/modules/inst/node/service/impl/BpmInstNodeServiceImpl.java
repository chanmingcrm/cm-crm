package com.platform.mesh.bpm.biz.modules.inst.node.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.bo.BpmInstNodeBO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.dto.BpmInstNodeHandleDTO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVarVO;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.exception.InstNodeExceptionEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.mapper.BpmInstNodeMapper;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.node.service.manual.BpmInstNodeServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.bo.BpmInstNodePassBO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstNodeServiceImpl extends ServiceImpl<BpmInstNodeMapper, BpmInstNode> implements IBpmInstNodeService {


    @Autowired
    private BpmInstNodeServiceManual bpmInstNodeServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeServiceManual getServiceManual() {
        return bpmInstNodeServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNode> selectNodesByInstProcessId(Long instProcessId) {
        //根据流程实例ID查询流程所有节点实例
        return lambdaQuery().eq(BpmInstNode::getInstProcessId, instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @return 正常返回:{@link List<BpmInstNodeVO>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNodeVO> selectInstNodeByInstProcessId(Long instProcessId) {
        return this.getBaseMapper().selectInstNodeByInstProcessId(instProcessId);
    }

    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @param instProcessId instProcessId
     * @param runFlag runFlag
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNode> selectNodeByInstProcessIdAndRunFlag(Long instProcessId, Integer runFlag) {
        //查询当前流程运行节点
        return lambdaQuery()
                .eq(BpmInstNode::getInstProcessId, instProcessId)
                .eq(BpmInstNode::getRunFlag, runFlag)
                .list();
    }

    /**
     * 功能描述:
     * 〈获取下一批节点〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNode> selectNextNode(BpmInstProcess bpmInstProcess) {
        //如果当前流程是未执行,则查询开始顶层开始节点
        if(ProcessRunEnum.STAND.getValue().equals(bpmInstProcess.getRunFlag())){
            return lambdaQuery()
                    .eq(BpmInstNode::getInstProcessId, bpmInstProcess.getId())
                    .eq(BpmInstNode::getNodeFlag, NodeTypeEnum.START_NODE.getValue())
                    .list();
        }
        //如果当前流程是执行中,则查询执行中节点
        if(ProcessRunEnum.RUNNING.getValue().equals(bpmInstProcess.getRunFlag())){
            //查询执行中的节点
            return this.selectNodeByInstProcessIdAndRunFlag(bpmInstProcess.getId(), NodeRunEnum.RUNNING.getValue());
        }
        //如果当前流程是已结束,则返回空
        return CollUtil.newArrayList();
    }

    /**
     * 功能描述:
     * 〈获取下一批节点〉
     * @return 正常返回:{@link List<BpmInstNode>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstNode> selectNextNode(List<BpmInstNode> bpmInstNodes) {
        if(CollUtil.isEmpty(bpmInstNodes)){
            return CollUtil.newArrayList();
        }
        List<Long> nextNodeIds = bpmInstNodeServiceManual.selectNextNodeIds(bpmInstNodes);
        if(CollUtil.isEmpty(nextNodeIds)){
            return CollUtil.newArrayList();
        }
        return listByIds(nextNodeIds);
    }

    /**
     * 功能描述:
     * 〈获取当前节点需要的变量信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeVarVO}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeVarVO getInstNodeVar(Long instNodeId) {
        BpmInstNode bpmInstNode = getById(instNodeId);
        if(ObjectUtil.isEmpty(bpmInstNode)){
            return new BpmInstNodeVarVO();
        }
        return bpmInstNodeServiceManual.getInstNodeVar(bpmInstNode);
    }

    /**
     * 功能描述:
     * 〈执行节点〉
     * @param handleDTO handleDTO
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode handleInstNode(BpmInstNodeHandleDTO handleDTO) {
        //目前需要手动应答节点为审批和定时节点:之后参数需要改变附带变量参数
        BpmInstNode bpmInstNode = getById(handleDTO.getNodeId());
        if(ObjectUtil.isEmpty(bpmInstNode)){
            //获取字段名称
            String fieldName = ObjFieldUtil.getFieldName(BpmInstNode::getId);
            throw InstNodeExceptionEnum.ADD_NO_ARGS.getBaseException(CollUtil.newArrayList(fieldName));
        }
        //设置节点审批人审批信息
        //如果节点审批完成：即需要参与审批的人员已完成审批则节点视为完成,触发节点流转操作
        //如果未完成：例如串签，多人审批还有人员未审批则不进行流转操作
        BpmInstNodePassBO passBO = bpmInstNodeServiceManual.passInstNode(bpmInstNode,handleDTO.getAuditAccountId(),handleDTO.getAuditPass());
        if(passBO.getCanPass()){
            //工作则修改状态
            bpmInstNode.setPassFlag(NodePassEnum.PASS.getValue());
        }else{
            bpmInstNode.setPassFlag(passBO.getAuditPass());
        }
        //依据审批类型重置审批结果
        Map<String, String> params = handleDTO.getParams();
        params.put(ObjFieldUtil.getFieldName(BpmInstNodeHandleDTO::getAuditPass),bpmInstNode.getPassFlag().toString());
        //将变量设置符合条件
        bpmInstNodeServiceManual.setLineVarValue(bpmInstNode,params);
        //执行流转节点
        if(passBO.getCanNext()){
            bpmInstNode.setRunFlag(NodeRunEnum.END.getValue());
            bpmInstNode.setOutFlag(InstNodeOutEnum.HANDLE_OUT.getValue());
            handleTargetNode(CollUtil.newArrayList(bpmInstNode));
        }
        return bpmInstNode;
    }

    /**
     * 功能描述:
     * 〈执行节点〉
     * @param bpmInstNodes bpmInstNodes
     * @author 蝉鸣
     */
    @Override
    public void handleTargetNode(List<BpmInstNode> bpmInstNodes){
        if(CollUtil.isEmpty(bpmInstNodes)){
            return;
        }
        //执行节点
        List<BpmInstNode> passNodes = bpmInstNodeServiceManual.handleInstNode(bpmInstNodes);
        //查询一下节点
        List<BpmInstNode> nextNodes = this.selectNextNode(passNodes);
        List<BpmInstNode> instNodes = nextNodes.stream().filter(node -> NodeTypeEnum.END_NODE.getValue().equals(node.getNodeFlag())).toList();
        if(CollUtil.isEmpty(instNodes)){
            //不存在结束节点递归执行
            handleTargetNode(nextNodes);
        }else{
            //如果存在结束节点则其他节点不再执行
            handleTargetNode(instNodes);

        }

    }

    /**
     * 功能描述:
     * 〈去目标节点〉
     * @param nodeId nodeId
     * @return 正常返回:{@link BpmInstNode}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNode gotoTargetNode(Long nodeId){
        BpmInstNode instNode = getById(nodeId);
        bpmInstNodeServiceManual.inInstNode(instNode);
        return instNode;
    }

    /**
     * 功能描述:
     * 〈重置节点变量〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    @Override
    public void clearNodeVar(BpmInstNode instNode) {
        bpmInstNodeServiceManual.clearNodeVar(instNode);
    }

    /**
     * 功能描述:
     * 〈获取实例节点关联信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link BpmInstNodeBO}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeBO getInstNodeData(Long instNodeId) {
        return this.getBaseMapper().getInstNodeData(instNodeId);
    }
}

