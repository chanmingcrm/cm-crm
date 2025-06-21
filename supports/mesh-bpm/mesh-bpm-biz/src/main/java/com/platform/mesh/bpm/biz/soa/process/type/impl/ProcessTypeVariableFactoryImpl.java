package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.service.IBpmInstLineService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.vo.BpmInstVariableVO;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.IBpmInstVariableService;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.dto.BpmTempLineDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.po.BpmTempVariable;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.vo.BpmTempVariableVO;
import com.platform.mesh.bpm.biz.modules.temp.variable.service.IBpmTempVariableService;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.core.enums.logic.type.LogicTypeEnum;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProcessTypeVariableFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeVariableFactoryImpl.class);

    @Autowired
    private IBpmTempVariableService bpmTempVariableService;

    @Autowired
    private IBpmInstVariableService bpmInstVariableService;

    @Autowired
    private IBpmInstLineService bpmInstLineService;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.VARIABLE;
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
        Map<String, BpmTempLineDTO> lineDTOMap = addDTO.getLineDTOs()
                .stream().collect(Collectors.toMap(BpmTempLineDTO::getLineHash, Function.identity()));

        List<BpmTempVariable> BpmTempVariables = addDTO.getVariableDTOs().stream().map(variableDTO -> {
            //生成ID
            Long variableId = IdUtil.getSnowflake().nextId();
            variableDTO.setVariableId(variableId);
            BpmTempVariable bpmTempVariable = BeanUtil.copyProperties(variableDTO, BpmTempVariable.class);
            bpmTempVariable.setId(variableId);
            bpmTempVariable.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //赋值线ID
            if (lineDTOMap.containsKey(bpmTempVariable.getTempLineHash())) {
                BpmTempLineDTO bpmTempLineDTO = lineDTOMap.get(bpmTempVariable.getTempLineHash());
                bpmTempVariable.setTempLineId(bpmTempLineDTO.getLineId());
            }
            return bpmTempVariable;
        }).toList();
        bpmTempVariableService.saveBatch(BpmTempVariables);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempVariable> tempVariables = bpmTempVariableService.lambdaQuery().eq(BpmTempVariable::getTempProcessId,processVO.getId()).list();
        List<BpmTempVariableVO> tempVariableVOS = BeanUtil.copyToList(tempVariables, BpmTempVariableVO.class);
        getVO.setVariableVOs(tempVariableVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        bpmTempVariableService.lambdaUpdate()
                .eq(BpmTempVariable::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化变量");
        //获取线实例
        List<BpmInstLine> bpmInstLines = bpmInstLineService.selectLinesByInstProcessId(instProcess.getId());
        Map<Long, BpmInstLine> instLineMap = bpmInstLines.stream().collect(Collectors.toMap(BpmInstLine::getTempLineId, Function.identity()));
        //获取模板动作
        List<BpmTempVariable> tempVariables = bpmTempVariableService.selectVariablesByTempProcessIdId(instProcess.getTempProcessId());
        //转化实例动作
        List<BpmInstVariable> bpmInstVariables = tempVariables.stream().map(item -> {
            BpmInstVariable bpmInstVariable = new BpmInstVariable();

            BeanUtil.copyProperties(item, bpmInstVariable, ObjFieldUtil.ignoreDefault());
            //设置流程实例ID
            bpmInstVariable.setInstProcessId(instProcess.getId());
            //设置流程变量类型
            if(ObjectUtil.isEmpty(item.getVariableType())){
                bpmInstVariable.setVariableType(LogicTypeEnum.AND.getValue());
            }
            //设置实例动作-节点
            if(instLineMap.containsKey(item.getTempLineId())){
                BpmInstLine bpmInstLine = instLineMap.get(item.getTempLineId());
                bpmInstVariable.setInstLineId(bpmInstLine.getId());
                bpmInstVariable.setTempVariableId(item.getId());
            }
            return bpmInstVariable;
        }).toList();
        bpmInstVariableService.saveBatch(bpmInstVariables);
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
        List<BpmInstVariable> instVariables = bpmInstVariableService.lambdaQuery().eq(BpmInstVariable::getInstProcessId,processVO.getId()).list();
        List<BpmInstVariableVO> instVariableVOS = BeanUtil.copyToList(instVariables, BpmInstVariableVO.class);
        getVO.setVariableVOs(instVariableVOS);
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
}
