package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.hist.event.domain.po.BpmHistEvent;
import com.platform.mesh.bpm.biz.modules.hist.event.domain.vo.BpmHistEventVO;
import com.platform.mesh.bpm.biz.modules.hist.event.service.IBpmHistEventService;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.action.service.IBpmInstActionService;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.vo.BpmInstEventVO;
import com.platform.mesh.bpm.biz.modules.inst.event.enums.InstEventHandleEnum;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.dto.BpmTempActionDTO;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.po.BpmTempEvent;
import com.platform.mesh.bpm.biz.modules.temp.event.domain.vo.BpmTempEventVO;
import com.platform.mesh.bpm.biz.modules.temp.event.service.IBpmTempEventService;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.dto.BpmTempNodeDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
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
public class ProcessTypeEventFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeEventFactoryImpl.class);

    @Autowired
    private IBpmTempEventService bpmTempEventService;

    @Autowired
    private IBpmInstEventService bpmInstEventService;

    @Autowired
    private IBpmHistEventService bpmHistEventService;

    @Autowired
    private IBpmInstActionService bpmInstActionService;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.EVENT;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getEventDTOs())){
            return;
        }
        Map<String, BpmTempNodeDTO> nodeDTOMap = addDTO.getNodeDTOs()
                .stream().collect(Collectors.toMap(BpmTempNodeDTO::getNodeHash, Function.identity()));
        Map<String, BpmTempActionDTO> actionDTOMap = addDTO.getActionDTOs()
                .stream().filter(item-> ObjectUtil.isNotEmpty(item.getActionHash()))
                .collect(Collectors.toMap(BpmTempActionDTO::getActionHash, Function.identity()));

        List<BpmTempEvent> bpmTempEvents = addDTO.getEventDTOs().stream().map(eventDTO -> {
            BpmTempEvent bpmTempEvent = BeanUtil.copyProperties(eventDTO, BpmTempEvent.class);
            bpmTempEvent.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //赋值节点ID
            if (nodeDTOMap.containsKey(bpmTempEvent.getTempNodeHash())) {
                BpmTempNodeDTO bpmTempNodeDTO = nodeDTOMap.get(bpmTempEvent.getTempNodeHash());
                bpmTempEvent.setTempNodeId(bpmTempNodeDTO.getNodeId());
            }
            //赋值节点ID
            if (actionDTOMap.containsKey(bpmTempEvent.getTempActionHash())) {
                BpmTempActionDTO bpmTempActionDTO = actionDTOMap.get(bpmTempEvent.getTempActionHash());
                bpmTempEvent.setTempActionId(bpmTempActionDTO.getActionId());
            }
            return bpmTempEvent;
        }).toList();
        bpmTempEventService.saveBatch(bpmTempEvents);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempEvent> tempEvents = bpmTempEventService.lambdaQuery().eq(BpmTempEvent::getTempProcessId,processVO.getId()).list();
        List<BpmTempEventVO> tempEventVOS = BeanUtil.copyToList(tempEvents, BpmTempEventVO.class);
        getVO.setEventVOs(tempEventVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        bpmTempEventService.lambdaUpdate()
                .eq(BpmTempEvent::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化事件");
        List<BpmInstAction> bpmInstActions = bpmInstActionService.selectActionsByInstProcessId(instProcess.getId());
        Map<Long, BpmInstAction> instActionMap = bpmInstActions.stream().collect(Collectors.toMap(BpmInstAction::getTempActionId, Function.identity()));
        //获取模板动作
        List<BpmTempEvent> tempEvents = bpmTempEventService.selectEventsByTempProcessIdId(instProcess.getTempProcessId());
        //转化实例动作
        List<BpmInstEvent> bpmInstEvents = tempEvents.stream().map(item -> {
            BpmInstEvent bpmInstEvent = new BpmInstEvent();
            BeanUtil.copyProperties(item, bpmInstEvent, ObjFieldUtil.ignoreDefault());
            //设置流程实例ID
            bpmInstEvent.setInstProcessId(instProcess.getId());

            //设置实例动作-节点
            if(instActionMap.containsKey(item.getTempActionId())){
                BpmInstAction bpmInstAction = instActionMap.get(item.getTempActionId());
                bpmInstEvent.setInstActionId(bpmInstAction.getId());
                bpmInstEvent.setInstNodeId(bpmInstAction.getInstNodeId());
                bpmInstEvent.setHandleFlag(InstEventHandleEnum.UNDO.getValue());
                bpmInstEvent.setTempEventId(item.getId());
            }
            return bpmInstEvent;
        }).toList();
        bpmInstEventService.saveBatch(bpmInstEvents);
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
        List<BpmInstEvent> bpmInstEvents = bpmInstEventService.selectEventByInstNodeId(instNode.getId());
        List<BpmHistEvent> bpmHistActions = BeanUtil.copyToList(bpmInstEvents, BpmHistEvent.class, ObjFieldUtil.ignoreDefault());
        bpmHistEventService.saveBatch(bpmHistActions);
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @author 蝉鸣
     */
    @Override
    public void getInst(BpmInstProcessDesignVO getVO) {
        BpmInstProcessVO processVO = getVO.getProcessVO();
        List<BpmInstEvent> instEvents = bpmInstEventService.lambdaQuery().eq(BpmInstEvent::getInstProcessId,processVO.getId()).list();
        List<BpmInstEventVO> instEventVOS = BeanUtil.copyToList(instEvents, BpmInstEventVO.class);
        getVO.setEventVOs(instEventVOS);
    }

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    @Override
    public void getHist(BpmHistProcessInfoVO getVO) {
        List<BpmHistEvent> histEvents = bpmHistEventService.lambdaQuery()
                .eq(BpmHistEvent::getInstProcessId,getVO.getInstProcessId())
                .list();
        getVO.setEventVOs(BeanUtil.copyToList(histEvents, BpmHistEventVO.class));
    }
}
