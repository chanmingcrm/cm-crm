package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.IBpmInstVariableService;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.po.BpmInstVarRefer;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.vo.BpmInstVarReferVO;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.service.IBpmInstVarReferService;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.dto.BpmTempLineDTO;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.po.BpmTempLine;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.dto.BpmTempVariableDTO;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.po.BpmTempVariable;
import com.platform.mesh.bpm.biz.modules.temp.variable.service.IBpmTempVariableService;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.po.BpmTempVarRefer;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.vo.BpmTempVarReferVO;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.service.IBpmTempVarReferService;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.core.enums.logic.ref.LogicRefEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
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
public class ProcessTypeVarReferFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeVarReferFactoryImpl.class);

    @Autowired
    private IBpmTempVarReferService bpmTempVarReferService;

    @Autowired
    private IBpmInstVarReferService bpmInstVarReferService;

    @Autowired
    private IBpmTempVariableService bpmTempVariableService;

    @Autowired
    private IBpmInstVariableService bpmInstVariableService;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.VAR_REFER;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getVariableDTOs())){
            return;
        }
        if(CollUtil.isEmpty(addDTO.getVarReferDTOs())){
            return;
        }
        Map<String, BpmTempLineDTO> lineDTOMap = addDTO.getLineDTOs()
                .stream().collect(Collectors.toMap(BpmTempLineDTO::getLineHash, Function.identity()));

        Map<String, BpmTempVariableDTO> variableDTOMap = addDTO.getVariableDTOs()
                .stream().collect(Collectors.toMap(BpmTempVariableDTO::getVariableHash, Function.identity()));

        List<BpmTempVarRefer> bpmTempVarRefers = addDTO.getVarReferDTOs().stream().map(varReferDTO -> {
            BpmTempVarRefer bpmTempVarRefer = new BpmTempVarRefer();
            BeanUtil.copyProperties(varReferDTO, bpmTempVarRefer, ObjFieldUtil.ignoreDefault());
            bpmTempVarRefer.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //赋值线ID
            if (lineDTOMap.containsKey(varReferDTO.getTempLineHash())) {
                BpmTempLineDTO bpmTempLineDTO = lineDTOMap.get(varReferDTO.getTempLineHash());
                bpmTempVarRefer.setTempLineId(bpmTempLineDTO.getLineId());
            }
            //赋值变量ID
            if (variableDTOMap.containsKey(varReferDTO.getTempVariableHash())) {
                BpmTempVariableDTO bpmTempVariableDTO = variableDTOMap.get(varReferDTO.getTempVariableHash());
                bpmTempVarRefer.setVariableName(bpmTempVariableDTO.getVariableName());
                bpmTempVarRefer.setTempVariableId(bpmTempVariableDTO.getVariableId());
            }
            return bpmTempVarRefer;
        }).toList();
        bpmTempVarReferService.saveBatch(bpmTempVarRefers);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempVarRefer> tempVarRefers = bpmTempVarReferService.lambdaQuery().eq(BpmTempVarRefer::getTempProcessId,processVO.getId()).list();
        List<BpmTempVarReferVO> tempVarReferVOS = BeanUtil.copyToList(tempVarRefers, BpmTempVarReferVO.class);
        getVO.setVarReferVOs(tempVarReferVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        bpmTempVarReferService.lambdaUpdate()
                .eq(BpmTempVarRefer::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化变量参照");
        List<BpmInstVariable> bpmInstVariables = bpmInstVariableService.selectVariablesByInstProcessIdId(instProcess.getId());
        Map<Long, BpmInstVariable> instVarReferMap = bpmInstVariables.stream().collect(Collectors.toMap(BpmInstVariable::getTempVariableId, Function.identity()));
        //获取模板动作
        List<BpmTempVarRefer> tempVarRefers = bpmTempVarReferService.selectVarReferByTempProcessIdId(instProcess.getTempProcessId());
        //转化实例动作
        List<BpmInstVarRefer> bpmInstVarRefers = tempVarRefers.stream().map(item -> {
            BpmInstVarRefer bpmInstVarRefer = BeanUtil.copyProperties(item, BpmInstVarRefer.class
                    , ObjFieldUtil.getFieldName(BpmInstVarRefer::getId));
            //设置流程实例ID
            bpmInstVarRefer.setInstProcessId(instProcess.getId());
            //设置流程变量默认类型
            if(ObjectUtil.isEmpty(item.getVariableType())){
                bpmInstVarRefer.setVariableType(LogicTypeEnum.AND.getValue());
            }
            if(ObjectUtil.isEmpty(item.getVariableRef())){
                bpmInstVarRefer.setVariableRef(LogicRefEnum.EQ.getValue());
            }
            //设置实例动作-节点
            if(instVarReferMap.containsKey(item.getTempVariableId())){
                BpmInstVariable bpmInstVariable = instVarReferMap.get(item.getTempVariableId());
                bpmInstVarRefer.setInstLineId(bpmInstVariable.getInstLineId());
                bpmInstVarRefer.setInstVariableId(bpmInstVariable.getId());
                bpmInstVarRefer.setTempVarReferId(item.getId());
            }
            return bpmInstVarRefer;
        }).toList();
        bpmInstVarReferService.saveBatch(bpmInstVarRefers);
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
        List<BpmInstVarRefer> instVarRefers = bpmInstVarReferService.lambdaQuery().eq(BpmInstVarRefer::getInstProcessId,processVO.getId()).list();
        List<BpmInstVarReferVO> instVarReferVOS = BeanUtil.copyToList(instVarRefers, BpmInstVarReferVO.class);
        getVO.setVarReferVOs(instVarReferVOS);
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
        List<BpmTempVarRefer> tempVarRefers = bpmTempVarReferService.selectVarReferByTempProcessIdId(sourceProcess.getId());
        if(CollUtil.isEmpty(tempVarRefers)){
            return;
        }
        List<BpmTempVariable> bpmTempVariables = bpmTempVariableService.selectVariablesByTempProcessIdId(targetProcess.getId());
        if(CollUtil.isEmpty(bpmTempVariables)){
            return;
        }
        Map<String, BpmTempVariable> varMap = bpmTempVariables.stream().collect(Collectors.toMap(BpmTempVariable::getVariableHash, Function.identity()));
        tempVarRefers.forEach(tempVarRefer -> {
            tempVarRefer.setId(null);
            tempVarRefer.setTempProcessId(targetProcess.getId());
            tempVarRefer.setCreateTime(LocalDateTime.now());
            tempVarRefer.setCreateUserId(targetProcess.getCreateUserId());
            tempVarRefer.setUpdateTime(LocalDateTime.now());
            tempVarRefer.setUpdateUserId(targetProcess.getUpdateUserId());
            tempVarRefer.setScopeOrgId(targetProcess.getScopeOrgId());
            tempVarRefer.setScopeUserId(targetProcess.getScopeUserId());
            if(varMap.containsKey(tempVarRefer.getTempVariableHash())){
                BpmTempVariable bpmTempVariable = varMap.get(tempVarRefer.getTempVariableHash());
                tempVarRefer.setTempVariableId(bpmTempVariable.getId());
                tempVarRefer.setTempLineId(bpmTempVariable.getTempLineId());
            }
        });
        bpmTempVarReferService.saveBatch(tempVarRefers);
    }
}
