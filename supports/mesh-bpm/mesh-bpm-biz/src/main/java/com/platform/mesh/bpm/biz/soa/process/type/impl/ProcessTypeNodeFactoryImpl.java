package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.dto.BpmDataFormNodeRelDTO;
import com.platform.mesh.bpm.biz.modules.data.noderel.domain.vo.BpmDataFormNodeRelVO;
import com.platform.mesh.bpm.biz.modules.data.noderel.service.IBpmDataFormNodeRelService;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.po.BpmHistNode;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import com.platform.mesh.bpm.biz.modules.hist.node.service.IBpmHistNodeService;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeInEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.enums.InstNodeOutEnum;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.vo.BpmTempNodeVO;
import com.platform.mesh.bpm.biz.modules.temp.node.service.IBpmTempNodeService;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessEditDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.process.enums.ProcessFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.audit.enums.NodeAuditFlagEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.domain.vo.NodeAuditDataVO;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.factory.NodeAuditDataFactory;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service
public class ProcessTypeNodeFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeNodeFactoryImpl.class);

    @Autowired
    private IBpmTempNodeService bpmTempNodeService;

    @Autowired
    private IBpmInstNodeService bpmInstNodeService;

    @Autowired
    private IBpmHistNodeService bpmHistNodeService;

    @Autowired
    private IBpmDataFormNodeRelService bpmDataFormNodeRelService;

    @Autowired
    private IBpmInstNodeAuditService bpmInstNodeAuditService;

    @Autowired
    private NodeAuditDataFactory nodeAuditDataFactory;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.NODE;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getNodeDTOs())){
            return;
        }
        BpmTempProcessEditDTO processDTO = addDTO.getProcessDTO();
        List<BpmDataFormNodeRelDTO> nodeRelDTOS = CollUtil.newArrayList();
        //转换对象
        List<BpmTempNode> tempNodes = addDTO.getNodeDTOs().stream().map(nodeDTO -> {
            //生成Id
            Long nodeId = IdUtil.getSnowflake().nextId();
            nodeDTO.setNodeId(nodeId);
            nodeDTO.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //转换对象
            BpmTempNode bpmTempNode = BeanUtil.copyProperties(nodeDTO, BpmTempNode.class);
            bpmTempNode.setId(nodeId);
            //添加默认审批类型
            if(processDTO.getProcessFlag().equals(ProcessFlagEnum.STAGE.getValue())
                    && ObjectUtil.isEmpty(bpmTempNode.getAuditFlag())){
                bpmTempNode.setAuditFlag(NodeAuditFlagEnum.INIT.getValue());
            }
            //设置节点与表单关系
            BpmDataFormNodeRelDTO nodeFormRelDTO = nodeDTO.getNodeFormRelDTO();
            if(ObjectUtil.isEmpty(nodeFormRelDTO) || ObjectUtil.isEmpty(nodeFormRelDTO.getFormId())){
                //返回节点信息
                return bpmTempNode;
            }
            nodeFormRelDTO.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            nodeFormRelDTO.setProcessName(addDTO.getProcessDTO().getProcessName());
            nodeFormRelDTO.setProcessVersion(addDTO.getProcessDTO().getProcessVersion());
            nodeFormRelDTO.setTempNodeId(nodeId);
            nodeRelDTOS.add(nodeFormRelDTO);
            //返回节点信息
            return bpmTempNode;
        }).toList();
        //批量增加节点信息
        bpmTempNodeService.saveBatch(tempNodes);
        //增加节点与表单关系
        bpmDataFormNodeRelService.batchAddDataFormNodeRel(nodeRelDTOS);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        if(ObjectUtil.isEmpty(processVO) || ObjectUtil.isEmpty(processVO.getId())){
            return;
        }
        List<BpmTempNode> tempNodes = bpmTempNodeService.lambdaQuery().eq(BpmTempNode::getTempProcessId,processVO.getId()).list();
        //获取节点下表单配置
        List<Long> nodeIds = tempNodes.stream().map(BpmTempNode::getId).toList();
        List<BpmDataFormNodeRelVO> nodeRelList = bpmDataFormNodeRelService.getDataFormNodeRelInfoByNodeId(nodeIds);
        //获取图片信息
//        Map<Long, DocFileVO> docFileMap = new HashMap<>();
//        List<Long> fileIds = tempNodes.stream().map(BpmTempNode::getNodeSvg).filter(NumberUtil::isNumber).map(Long::parseLong).distinct().toList();
//        Result<List<DocFileVO>> docFileResult = remoteDocService.getDocFiles(fileIds);
//        Optional<List<DocFileVO>> docFileVOS = ResultUtil.of(docFileResult).getData();
//        if (docFileVOS.isPresent()) {
//             docFileMap = docFileVOS.get().stream().collect(Collectors.toMap(DocFileVO::getId, Function.identity()));
//        }
        List<BpmTempNodeVO> tempNodeVOS = CollUtil.newArrayList();
        //遍历节点信息
        for (BpmTempNode tempNode : tempNodes) {
            BpmTempNodeVO tempNodeVO = BeanUtil.copyProperties(tempNode, BpmTempNodeVO.class);
            //获取节点下表单配置
            if (CollUtil.isNotEmpty(nodeRelList)) {
                Map<Long, BpmDataFormNodeRelVO> relVOMap = nodeRelList.stream().collect(Collectors.toMap(BpmDataFormNodeRelVO::getTempNodeId, Function.identity(), (v1, v2) -> v2));
                if (relVOMap.containsKey(tempNodeVO.getId())) {
                    BpmDataFormNodeRelVO nodeRelVO = relVOMap.get(tempNodeVO.getId());
                    tempNodeVO.setNodeFormRelVO(nodeRelVO);
                }
            }
            //设置处理人信息
            if(ObjectUtil.isNotEmpty(tempNode.getAuditDataType())){
                NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, tempNode.getAuditDataType());
                NodeAuditDataService nodeAuditService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
                if(ObjectUtil.isNotEmpty(nodeAuditService) && ObjectUtil.isNotEmpty(tempNode.getAuditDataIds())){
                    List<Long> auditDataIds = Arrays.stream(tempNode.getAuditDataIds().split(SymbolConst.COMMA)).filter(NumberUtil::isNumber).map(Long::parseLong).toList();
                    List<NodeAuditDataVO> auditDataVO = nodeAuditService.getAuditDataVO(auditDataIds);
                    tempNodeVO.setAuditDataVO(auditDataVO);
                }
            }
//            //设置图片信息
//            String nodeSvg = tempNode.getNodeSvg();
//            if(ObjectUtil.isNotEmpty(nodeSvg) && NumberUtil.isNumber(nodeSvg)){
//                if(ObjectUtil.isNotEmpty(docFileMap) && docFileMap.containsKey(Long.parseLong(nodeSvg))){
//                    tempNodeVO.setNodeSvgFile(docFileMap.get(Long.parseLong(nodeSvg)));
//                }
//            }
            tempNodeVOS.add(tempNodeVO);
        }
        getVO.setNodeVOs(tempNodeVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        bpmTempNodeService.lambdaUpdate()
                .eq(BpmTempNode::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化节点");
        //获取模板节点
        List<BpmTempNode> tempNodes = bpmTempNodeService.selectNodesByTemplateId(instProcess.getTempProcessId());
        //收集节点审批信息
        List<BpmInstNodeAudit> auditList = CollUtil.newArrayList();
        //转化实例节点
        List<BpmInstNode> bpmInstNodes = tempNodes.stream().map(item -> {
            BpmInstNode bpmInstNode = new BpmInstNode();
            BeanUtil.copyProperties(item, bpmInstNode, ObjFieldUtil.ignoreDefault());
            //生成Id
            Long nodeId = IdUtil.getSnowflake().nextId();
            bpmInstNode.setId(nodeId);
            //设置流程实例ID
            bpmInstNode.setInstProcessId(instProcess.getId());
            //设置流程模板节点ID
            bpmInstNode.setTempNodeId(item.getId());
            //设置运行状态
            bpmInstNode.setRunFlag(NodeRunEnum.INIT.getValue());
            bpmInstNode.setInFlag(InstNodeInEnum.INIT.getValue());
            bpmInstNode.setOutFlag(InstNodeOutEnum.INIT.getValue());
            bpmInstNode.setPassFlag(NodePassEnum.INIT.getValue());
            //如果是阶段流程，并且是父流程，并且审批标识空值则赋值初始化状态，初始化状态只用判断通过参数，无需其他条件
            if(instProcess.getProcessFlag().equals(ProcessFlagEnum.STAGE.getValue())
                && ObjectUtil.isEmpty(item.getAuditFlag())){
                bpmInstNode.setAuditFlag(NodeAuditFlagEnum.INIT.getValue());
            }
            //节点审批信息
            List<Long> auditDataIds = bpmInstNodeAuditService.getAuditDataIds(item.getAuditDataType(),item.getAuditDataIds());
            if(CollUtil.isNotEmpty(auditDataIds)){
                auditDataIds.forEach(auditDataId -> {
                    BpmInstNodeAudit nodeAudit = new BpmInstNodeAudit();
                    BeanUtil.copyProperties(bpmInstNode, nodeAudit, ObjFieldUtil.ignoreDefault());
                    nodeAudit.setInstNodeId(bpmInstNode.getId());
                    nodeAudit.setAuditPass(NodePassEnum.INIT.getValue());
                    nodeAudit.setAuditDataId(auditDataId);
                    auditList.add(nodeAudit);
                });
            }
            return bpmInstNode;
        }).toList();
        //设置节点实例信息
        bpmInstNodeService.saveBatch(bpmInstNodes);
        //设置节点审批人员信息
        bpmInstNodeAuditService.saveBatch(auditList);
    }

    /**
     * 功能描述:
     * 〈添加流程实例子项〉
     * @param addSubDTO addSubDTO
     * @author 蝉鸣
     */
    @Override
    public void addInstSub(BpmInstNodeSubDTO addSubDTO){

    }

    /**
     * 功能描述:
     * 〈添加历史记录〉
     * @param instNode instNode
     * @author 蝉鸣
     */
    @Override
    public void addHist(BpmInstNode instNode){
        //节点
        BpmHistNode bpmHistNode = new BpmHistNode();
        BeanUtil.copyProperties(instNode, bpmHistNode, ObjFieldUtil.ignoreDefault());
        //赋值实例节点ID
        bpmHistNode.setInstNodeId(instNode.getId());
        bpmHistNodeService.save(bpmHistNode);
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @author 蝉鸣
     */
    @Override
    public void getInst(BpmInstProcessDesignVO getVO) {
        BpmInstProcessVO processVO = getVO.getProcessVO();
        if(ObjectUtil.isEmpty(processVO.getId())){
            return;
        }
        List<BpmInstNodeVO> instNodeVOS = bpmInstNodeService.selectInstNodeByInstProcessId(processVO.getId());
        //获取节点下表单配置
        List<Long> tempNodeIds = instNodeVOS.stream().map(BpmInstNodeVO::getTempNodeId).toList();
        List<Long> instNodeIds = instNodeVOS.stream().map(BpmInstNodeVO::getId).toList();
        List<BpmDataFormNodeRelVO> nodeRelList = bpmDataFormNodeRelService.getDataFormNodeRelInfoByNodeId(tempNodeIds);
        Map<Long, List<Long>> auditMap = new HashMap<>();
        if(CollUtil.isNotEmpty(instNodeIds)){
            List<BpmInstNodeAudit> nodeAuditList = bpmInstNodeAuditService.lambdaQuery().in(BpmInstNodeAudit::getInstNodeId, instNodeIds).list();
            auditMap = nodeAuditList.stream().collect(Collectors.groupingBy(BpmInstNodeAudit::getInstNodeId, Collectors.mapping(BpmInstNodeAudit::getAuditDataId,Collectors.toList())));
        }
        Map<Long, BpmDataFormNodeRelVO> relVOMap = nodeRelList.stream().collect(Collectors.toMap(BpmDataFormNodeRelVO::getTempNodeId, Function.identity(), (v1, v2) -> v2));
        for (BpmInstNodeVO instNodeVO : instNodeVOS) {
            //关联表单信息
            if (CollUtil.isNotEmpty(relVOMap) && relVOMap.containsKey(instNodeVO.getTempNodeId())) {
                BpmDataFormNodeRelVO nodeRelVO = relVOMap.get(instNodeVO.getTempNodeId());
                instNodeVO.setNodeFormRelVO(nodeRelVO);
            }
            //关联审批人信息
            if (CollUtil.isNotEmpty(auditMap) && auditMap.containsKey(instNodeVO.getId())) {
                instNodeVO.setAuditDataIds(auditMap.get(instNodeVO.getId()));
            }

        }
        getVO.setNodeVOs(instNodeVOS);
    }

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    @Override
    public void getHist(BpmHistProcessInfoVO getVO) {
        List<BpmHistNodeVO> histNodeVOs = bpmHistNodeService.selectHistNodeByInstProcessId(getVO.getInstProcessId());
        getVO.setNodeVOs(histNodeVOs);
    }

    /**
     * 功能描述:
     * 〈拷贝流程模板〉
     * @param sourceProcess sourceProcess
     * @param targetProcess targetProcess
     * @author 蝉鸣
     */
    @Override
    public void copyTemp(BpmTempProcess sourceProcess, BpmTempProcess targetProcess) {
        List<BpmTempNode> bpmTempNodes = bpmTempNodeService.selectNodesByTemplateId(sourceProcess.getId());
        if(CollUtil.isEmpty(bpmTempNodes)){
            return;
        }
        bpmTempNodes.forEach(bpmTempNode -> {
            bpmTempNode.setId(null);
            bpmTempNode.setTempProcessId(targetProcess.getId());
            bpmTempNode.setCreateTime(LocalDateTime.now());
            bpmTempNode.setCreateUserId(targetProcess.getCreateUserId());
            bpmTempNode.setUpdateTime(LocalDateTime.now());
            bpmTempNode.setUpdateUserId(targetProcess.getUpdateUserId());
            bpmTempNode.setScopeOrgId(targetProcess.getScopeOrgId());
            bpmTempNode.setScopeUserId(targetProcess.getScopeUserId());
        });
        bpmTempNodeService.saveBatch(bpmTempNodes);
    }
}
