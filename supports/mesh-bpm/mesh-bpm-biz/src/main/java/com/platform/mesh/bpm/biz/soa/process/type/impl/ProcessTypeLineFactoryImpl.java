package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.vo.BpmInstLineVO;
import com.platform.mesh.bpm.biz.modules.inst.line.service.IBpmInstLineService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.po.BpmTempLine;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.vo.BpmTempLineVO;
import com.platform.mesh.bpm.biz.modules.temp.line.service.IBpmTempLineService;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.dto.BpmTempNodeDTO;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.node.service.IBpmTempNodeService;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class ProcessTypeLineFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeLineFactoryImpl.class);

    @Autowired
    private IBpmTempLineService bpmTempLineService;

    @Autowired
    private IBpmInstLineService bpmInstLineService;

    @Autowired
    private IBpmTempNodeService bpmTempNodeService;

    @Autowired
    private IBpmInstNodeService bpmInstNodeService;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.LINE;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getLineDTOs())){
            return;
        }
        List<BpmTempNodeDTO> nodeDTOs = addDTO.getNodeDTOs();
        Map<String, BpmTempNodeDTO> nodeDTOMap = nodeDTOs.stream().collect(Collectors.toMap(BpmTempNodeDTO::getNodeHash, Function.identity()));

        List<BpmTempLine> bpmTempLines = addDTO.getLineDTOs().stream().map(lineDTO -> {
            //生成ID
            Long lineId = IdUtil.getSnowflake().nextId();
            lineDTO.setLineId(lineId);
            BpmTempLine bpmTempLine = BeanUtil.copyProperties(lineDTO, BpmTempLine.class);
            bpmTempLine.setId(lineId);

            bpmTempLine.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //赋值入节点ID
            if (nodeDTOMap.containsKey(bpmTempLine.getTempInNodeHash())) {
                BpmTempNodeDTO bpmTempNodeDTO = nodeDTOMap.get(bpmTempLine.getTempInNodeHash());
                bpmTempLine.setTempInNodeId(bpmTempNodeDTO.getNodeId());
            }
            //赋值出节点ID
            if (nodeDTOMap.containsKey(bpmTempLine.getTempOutNodeHash())) {
                BpmTempNodeDTO bpmTempNodeDTO = nodeDTOMap.get(bpmTempLine.getTempOutNodeHash());
                bpmTempLine.setTempOutNodeId(bpmTempNodeDTO.getNodeId());
            }
            return bpmTempLine;
        }).toList();

        bpmTempLineService.saveBatch(bpmTempLines);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempLine> tempLines = bpmTempLineService.lambdaQuery().eq(BpmTempLine::getTempProcessId,processVO.getId()).list();
        List<BpmTempLineVO> tempLineVOS = BeanUtil.copyToList(tempLines, BpmTempLineVO.class);
        getVO.setLineVOs(tempLineVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        bpmTempLineService.lambdaUpdate()
                .eq(BpmTempLine::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化线");
        //查询实例节点
        List<BpmInstNode> bpmInstNodes = bpmInstNodeService.selectNodesByInstProcessId(instProcess.getId());
        Map<Long, BpmInstNode> instNodeMap = bpmInstNodes.stream().collect(Collectors.toMap(BpmInstNode::getTempNodeId, Function.identity()));
        //获取模板线
        List<BpmTempLine> tempLines = bpmTempLineService.selectLinesByTemplateId(instProcess.getTempProcessId());
        //转化实例线
        List<BpmInstLine> bpmInstLines = tempLines.stream().map(item -> {
            BpmInstLine bpmInstLine = new BpmInstLine();
            BeanUtil.copyProperties(item, bpmInstLine, ObjFieldUtil.ignoreDefault());
            //设置流程实例ID
            bpmInstLine.setInstProcessId(instProcess.getId());
            bpmInstLine.setTempLineId(item.getId());
            //设置实例进入-节点
            if(instNodeMap.containsKey(item.getTempInNodeId())){
                BpmInstNode bpmInstNode = instNodeMap.get(item.getTempInNodeId());
                bpmInstLine.setInstInNodeId(bpmInstNode.getId());
            }
            //设置实例流出-节点
            if(instNodeMap.containsKey(item.getTempOutNodeId())){
                BpmInstNode bpmInstNode = instNodeMap.get(item.getTempOutNodeId());
                bpmInstLine.setInstOutNodeId(bpmInstNode.getId());
            }
            //设置实例进入pass状态
            bpmInstLine.setInstInLinePass(item.getTempInLinePass());
            //设置实例流出pass状态
            bpmInstLine.setInstOutLinePass(item.getTempOutLinePass());
            return bpmInstLine;
        }).toList();
        bpmInstLineService.saveBatch(bpmInstLines);
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

    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @author 蝉鸣
     */
    @Override
    public void getInst(BpmInstProcessDesignVO getVO) {
        BpmInstProcessVO processVO = getVO.getProcessVO();
        List<BpmInstLine> instLines = bpmInstLineService.lambdaQuery().eq(BpmInstLine::getInstProcessId,processVO.getId()).list();
        List<BpmInstLineVO> instLineVOS = BeanUtil.copyToList(instLines, BpmInstLineVO.class);
        getVO.setLineVOs(instLineVOS);
    }

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    @Override
    public void getHist(BpmHistProcessInfoVO getVO) {

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
        List<BpmTempLine> bpmTempLines = bpmTempLineService.selectLinesByTemplateId(sourceProcess.getId());
        if(CollUtil.isEmpty(bpmTempLines)){
            return;
        }
        List<BpmTempNode> bpmTempNodes = bpmTempNodeService.selectNodesByTemplateId(targetProcess.getId());
        if(CollUtil.isEmpty(bpmTempNodes)){
            return;
        }
        Map<String, BpmTempNode> nodeMap = bpmTempNodes.stream().collect(Collectors.toMap(BpmTempNode::getNodeHash, Function.identity()));
        bpmTempLines.forEach(bpmTempLine -> {
            bpmTempLine.setId(null);
            bpmTempLine.setTempProcessId(targetProcess.getId());
            bpmTempLine.setCreateTime(LocalDateTime.now());
            bpmTempLine.setCreateUserId(targetProcess.getCreateUserId());
            bpmTempLine.setUpdateTime(LocalDateTime.now());
            bpmTempLine.setUpdateUserId(targetProcess.getUpdateUserId());
            bpmTempLine.setScopeOrgId(targetProcess.getScopeOrgId());
            bpmTempLine.setScopeUserId(targetProcess.getScopeUserId());
            if(nodeMap.containsKey(bpmTempLine.getTempInNodeHash())){
                BpmTempNode bpmTempNode = nodeMap.get(bpmTempLine.getTempInNodeHash());
                bpmTempLine.setTempInNodeId(bpmTempNode.getId());
            }
            if(nodeMap.containsKey(bpmTempLine.getTempOutNodeHash())){
                BpmTempNode bpmTempNode = nodeMap.get(bpmTempLine.getTempOutNodeHash());
                bpmTempLine.setTempOutNodeId(bpmTempNode.getId());
            }
        });
        bpmTempLineService.saveBatch(bpmTempLines);
    }
}
