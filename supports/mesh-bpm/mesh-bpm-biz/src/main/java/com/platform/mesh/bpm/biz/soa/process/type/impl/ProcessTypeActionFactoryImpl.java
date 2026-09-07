package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.platform.mesh.bpm.biz.modules.hist.action.domain.po.BpmHistAction;
import com.platform.mesh.bpm.biz.modules.hist.action.domain.vo.BpmHistActionVO;
import com.platform.mesh.bpm.biz.modules.hist.action.service.IBpmHistActionService;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.vo.BpmInstActionVO;
import com.platform.mesh.bpm.biz.modules.inst.action.service.IBpmInstActionService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.po.BpmTempAction;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.vo.BpmTempActionVO;
import com.platform.mesh.bpm.biz.modules.temp.action.service.IBpmTempActionService;
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
public class ProcessTypeActionFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeActionFactoryImpl.class);

    @Autowired
    private IBpmTempActionService bpmTempActionService;

    @Autowired
    private IBpmInstActionService bpmInstActionService;

    @Autowired
    private IBpmHistActionService bpmHistActionService;

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
        return ProcessTypeEnum.ACTION;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        if(CollUtil.isEmpty(addDTO.getActionDTOs())){
            return;
        }
        List<BpmTempNodeDTO> nodeDTOs = addDTO.getNodeDTOs();
        Map<String, BpmTempNodeDTO> nodeDTOMap = nodeDTOs.stream().collect(Collectors.toMap(BpmTempNodeDTO::getNodeHash, Function.identity()));

        List<BpmTempAction> bpmTempActions = addDTO.getActionDTOs().stream().map(actionDTO -> {
            //生成ID
            Long actionId = IdUtil.getSnowflake().nextId();
            actionDTO.setActionId(actionId);
            BpmTempAction bpmTempAction = BeanUtil.copyProperties(actionDTO, BpmTempAction.class);
            bpmTempAction.setId(actionId);
            bpmTempAction.setTempProcessId(addDTO.getProcessDTO().getProcessId());
            //赋值节点ID
            if (nodeDTOMap.containsKey(bpmTempAction.getTempNodeHash())) {
                BpmTempNodeDTO bpmTempNodeDTO = nodeDTOMap.get(bpmTempAction.getTempNodeHash());
                bpmTempAction.setTempNodeId(bpmTempNodeDTO.getNodeId());
            }
            return bpmTempAction;
        }).toList();
        bpmTempActionService.saveBatch(bpmTempActions);
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempAction> tempActions = bpmTempActionService.lambdaQuery().eq(BpmTempAction::getTempProcessId,processVO.getId()).list();
        List<BpmTempActionVO> tempActionVOS = BeanUtil.copyToList(tempActions, BpmTempActionVO.class);
        getVO.setActionVOs(tempActionVOS);
    }

    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
         bpmTempActionService.lambdaUpdate()
                .eq(BpmTempAction::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        //查询实例节点
        List<BpmInstNode> bpmInstNodes = bpmInstNodeService.selectNodesByInstProcessId(instProcess.getId());
        Map<Long, BpmInstNode> instNodeMap = bpmInstNodes.stream().collect(Collectors.toMap(BpmInstNode::getTempNodeId, Function.identity()));
        //获取模板动作
        List<BpmTempAction> tempActions = bpmTempActionService.selectActionsByTemplateId(instProcess.getTempProcessId());
        //转化实例动作
        List<BpmInstAction> bpmInstActions = tempActions.stream().map(item -> {
            BpmInstAction bpmInstAction = new BpmInstAction();
            BeanUtil.copyProperties(item, bpmInstAction, ObjFieldUtil.ignoreDefault());
            //设置流程实例ID
            bpmInstAction.setInstProcessId(instProcess.getId());
            //设置实例动作-节点
            if(instNodeMap.containsKey(item.getTempNodeId())){
                BpmInstNode bpmInstNode = instNodeMap.get(item.getTempNodeId());
                bpmInstAction.setInstNodeId(bpmInstNode.getId());
                bpmInstAction.setTempActionId(item.getId());
            }
            return bpmInstAction;
        }).toList();
        bpmInstActionService.saveBatch(bpmInstActions);
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
        List<BpmInstAction> bpmInstActions = bpmInstActionService.selectActionsByInstNodeId(instNode.getId());
        List<BpmHistAction> bpmHistActions = BeanUtil.copyToList(bpmInstActions, BpmHistAction.class, ObjFieldUtil.ignoreDefault());
        bpmHistActionService.saveBatch(bpmHistActions);
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @author 蝉鸣
     */
    @Override
    public void getInst(BpmInstProcessDesignVO getVO) {
        BpmInstProcessVO processVO = getVO.getProcessVO();
        List<BpmInstAction> instActions = bpmInstActionService.lambdaQuery().eq(BpmInstAction::getInstProcessId,processVO.getId()).list();
        List<BpmInstActionVO> instActionVOS = BeanUtil.copyToList(instActions, BpmInstActionVO.class);
        getVO.setActionVOs(instActionVOS);
    }

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    @Override
    public void getHist(BpmHistProcessInfoVO getVO) {
        List<BpmHistAction> histActions = bpmHistActionService.lambdaQuery()
                .eq(BpmHistAction::getInstProcessId,getVO.getInstProcessId())
                .list();
        getVO.setActionVOs(BeanUtil.copyToList(histActions, BpmHistActionVO.class));
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
        List<BpmTempAction> bpmTempActions = bpmTempActionService.selectActionsByTemplateId(sourceProcess.getId());
        if(CollUtil.isEmpty(bpmTempActions)){
            return;
        }
        List<BpmTempNode> bpmTempNodes = bpmTempNodeService.selectNodesByTemplateId(targetProcess.getId());
        if(CollUtil.isEmpty(bpmTempNodes)){
            return;
        }
        Map<String, BpmTempNode> nodeMap = bpmTempNodes.stream().collect(Collectors.toMap(BpmTempNode::getNodeHash, Function.identity()));
        bpmTempActions.forEach(bpmTempAction -> {
            bpmTempAction.setId(null);
            bpmTempAction.setTempProcessId(targetProcess.getId());
            bpmTempAction.setCreateTime(LocalDateTime.now());
            bpmTempAction.setCreateUserId(targetProcess.getCreateUserId());
            bpmTempAction.setUpdateTime(LocalDateTime.now());
            bpmTempAction.setUpdateUserId(targetProcess.getUpdateUserId());
            bpmTempAction.setScopeOrgId(targetProcess.getScopeOrgId());
            bpmTempAction.setScopeUserId(targetProcess.getScopeUserId());
            if(nodeMap.containsKey(bpmTempAction.getTempNodeHash())){
                BpmTempNode bpmTempNode = nodeMap.get(bpmTempAction.getTempNodeHash());
                bpmTempAction.setTempNodeId(bpmTempNode.getId());
            }
        });
        bpmTempActionService.saveBatch(bpmTempActions);
    }
}
