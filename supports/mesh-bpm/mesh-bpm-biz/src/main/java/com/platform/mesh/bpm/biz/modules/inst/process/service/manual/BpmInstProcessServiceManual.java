package com.platform.mesh.bpm.biz.modules.inst.process.service.manual;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmProcessStartDTO;
import com.platform.mesh.bpm.biz.data.form.domain.po.BpmDataFormRel;
import com.platform.mesh.bpm.biz.data.form.service.IBpmDataFormRelService;
import com.platform.mesh.bpm.biz.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.domain.po.BpmInstNodeAudit;
import com.platform.mesh.bpm.biz.modules.inst.nodeaudit.service.IBpmInstNodeAuditService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessAuditVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessRunVO;
import com.platform.mesh.bpm.biz.modules.inst.process.exception.InstProcessExceptionEnum;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.service.IBpmTempProcessService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.NodeAuditDataService;
import com.platform.mesh.bpm.biz.soa.node.auditdata.enums.NodeAuditDataTypeEnum;
import com.platform.mesh.bpm.biz.soa.node.auditdata.factory.NodeAuditDataFactory;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.node.type.enums.NodeTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.factory.ProcessTypeFactory;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmInstProcessServiceManual {

    private final static Logger log = LoggerFactory.getLogger(BpmInstProcessServiceManual.class);

    @Autowired
    private IBpmTempProcessService bpmTempProcessService;

    @Autowired
    private IBpmInstNodeService bpmInstNodeService;

    @Autowired
    private IBpmInstNodeSubService bpmInstNodeSubService;

    @Autowired
    private IBpmInstNodeAuditService bpmInstNodeAuditService;

    @Autowired
    private ProcessTypeFactory processTypeFactory;

    @Autowired
    private IBpmDataInstRelService bpmDataInstRelService;

    @Autowired
    private NodeAuditDataFactory nodeAuditDataFactory;

    @Autowired
    private IBpmDataFormRelService bpmDataFormRelService;


    /**
     * 功能描述:
     * 〈初始化实例信息〉
     * @param templateId templateId
     * @return 正常返回:{@link Long}
     * @author 蝉鸣
     */
    public BpmInstProcess initProcessInst(Long templateId) {
        //查询模板流程
        BpmTempProcess tempProcess = bpmTempProcessService.getById(templateId);
        if(ObjectUtil.isEmpty(tempProcess)) {
            throw InstProcessExceptionEnum.ADD_NO_INVALID.getBaseException();
        }
        //初始化流程实例
        BpmInstProcess bpmInstProcess = new BpmInstProcess();
        BeanUtil.copyProperties(tempProcess, bpmInstProcess, ObjFieldUtil.ignoreDefault());
        bpmInstProcess.setTempProcessId(tempProcess.getId());
        //初始化所有过程
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //初始化实例节点
//            processTypeService.initInst(bpmInstProcess);
            FutureHandleUtil.runNoResult(bpmInstProcess,processTypeService::initInst);
        }
        //返回流程实例
        return bpmInstProcess;
    }

    /**
     * 功能描述:
     * 〈绑定实例与数据ID〉
     * @param bpmInstProcess bpmInstProcess
     * @param startDTO startDTO
     * @author 蝉鸣
     */
    public void addDataInstRel(BpmInstProcess bpmInstProcess, BpmProcessStartDTO startDTO) {
        //校验当前数据是否已有运行中的流程
        Boolean checkDataRel = bpmDataInstRelService.checkDataRelHasRun(startDTO.getDataId(),bpmInstProcess.getTempProcessId(),Boolean.TRUE);
        if(checkDataRel){
            throw InstProcessExceptionEnum.DATA_HAS_RUNNING.getBaseException();
        }
        BpmDataInstRel bpmDataInstRel = BeanUtil.copyProperties(startDTO, BpmDataInstRel.class);
        bpmDataInstRel.setTempProcessId(bpmInstProcess.getTempProcessId());
        bpmDataInstRel.setInstProcessId(bpmInstProcess.getId());
        bpmDataInstRel.setProcessName(bpmInstProcess.getProcessName());
        bpmDataInstRel.setProcessVersion(bpmInstProcess.getProcessVersion());
        bpmDataInstRel.setProcessFlag(bpmInstProcess.getProcessFlag());
        bpmDataInstRelService.save(bpmDataInstRel);
    }

    /**
     * 功能描述:
     * 〈启动实例〉
     * @param bpmInstProcess bpmInstProcess
     * @author 蝉鸣
     */
    public void runProcessInst(BpmInstProcess bpmInstProcess) {
        //校验实例是否有效
        if(!ProcessRunEnum.STAND.getValue().equals(bpmInstProcess.getRunFlag())){
            return;
        }
        List<BpmInstNode> bpmInstNodes = bpmInstNodeService.selectNextNode(bpmInstProcess);
        //递归执行
        bpmInstNodeService.handleTargetNode(bpmInstNodes);

    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcess instProcess
     * @return 正常返回:{@link BpmInstProcessRunVO}
     * @author 蝉鸣
     */
    public BpmInstProcessRunVO getProcessInstRunInfo(BpmInstProcess instProcess) {
        BpmInstProcessRunVO processVO = new BpmInstProcessRunVO();
        processVO.setInstProcessId(instProcess.getId());
        //获取当前流程的父流程
        BpmInstNodeSub bpmInstNodeSub = bpmInstNodeSubService.selectNodeSubByChildProcessId(instProcess.getId());
        if (ObjectUtil.isNotEmpty(bpmInstNodeSub)) {
            processVO.setParentProcessId(bpmInstNodeSub.getInstProcessId());
        }
        //查询流程节点信息
        List<BpmInstNode> bpmInstNodes = bpmInstNodeService.selectNodesByInstProcessId(instProcess.getId());
        if(ObjectUtil.isEmpty(bpmInstNodes)){
            return processVO;
        }
        //获取当前节点运行中的节点
        List<Long> runNodeIds = bpmInstNodes.stream().filter(node -> NodeRunEnum.RUNNING.getValue().equals(node.getRunFlag())).map(BpmInstNode::getId).toList();
        processVO.setRunNodeIds(runNodeIds);
        //设置运行中的节点当前登录人员是否可以参与审批节点
        List<Long> runAuditNodeIds = bpmInstNodes.stream()
                .filter(node -> NodeRunEnum.RUNNING.getValue().equals(node.getRunFlag())
                        && NodeTypeEnum.AUDIT_NODE.getValue().equals(node.getNodeFlag())
                )
                .map(BpmInstNode::getId).toList();
        //获取当前节点审批人是否可以审批节点
        if(CollUtil.isEmpty(runAuditNodeIds)){
            return processVO;
        }
        //获取节点审批信息
        List<BpmInstNodeAudit> nodeAudits = bpmInstNodeAuditService.lambdaQuery().in(BpmInstNodeAudit::getInstNodeId, runAuditNodeIds).list();
        Map<Long, List<BpmInstNodeAudit>> nodeAuditMap = nodeAudits.stream().collect(Collectors.groupingBy(BpmInstNodeAudit::getInstNodeId));
        List<BpmInstProcessAuditVO> auditVOS = CollUtil.newArrayList();
        //组装数据
        nodeAuditMap.forEach((key,value)->{
            BpmInstProcessAuditVO bpmInstProcessAuditVO = new BpmInstProcessAuditVO();
            bpmInstProcessAuditVO.setInstNodeId(key);
            BpmInstNodeAudit nodeAudit = CollUtil.getFirst(value);
            List<Long> auditDataIds = value.stream().map(BpmInstNodeAudit::getAuditDataId).toList();
            NodeAuditDataTypeEnum enumByValue = BaseEnum.getEnumByValue(NodeAuditDataTypeEnum.class, nodeAudit.getAuditDataType());
            NodeAuditDataService nodeAuditService = nodeAuditDataFactory.getNodeAuditService(enumByValue);
            if(ObjectUtil.isNotEmpty(nodeAuditService)){
                Boolean canAudit = nodeAuditService.getCanAudit(auditDataIds, SecurityUtils.getLoginUser().getAccountId());
                bpmInstProcessAuditVO.setCanAudit(canAudit);
            }
            auditVOS.add(bpmInstProcessAuditVO);
        });
        processVO.setAuditNodes(auditVOS);
        return processVO;
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param bpmInstProcessDesignVO bpmInstProcessDesignVO
     * @return 正常返回:{@link BpmInstProcessDesignVO}
     * @author 蝉鸣
     */
    public BpmInstProcessDesignVO getProcessInst(BpmInstProcessDesignVO bpmInstProcessDesignVO) {
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //获取实例节点
            processTypeService.getInst(bpmInstProcessDesignVO);
        }
        //返回流程实例VO
        return bpmInstProcessDesignVO;
    }

    /**
     * 功能描述:
     * 〈获取待办审批数据范围〉
     * @param processTodoBO processTodoBO
     * @author 蝉鸣
     */
    public BpmInstProcessTodoBO getProcessTodoBO(BpmInstProcessTodoBO processTodoBO) {
        for (NodeAuditDataTypeEnum dataTypeEnum : NodeAuditDataTypeEnum.values()) {
            NodeAuditDataService nodeAuditService = nodeAuditDataFactory.getNodeAuditService(dataTypeEnum);
            if(ObjectUtil.isEmpty(nodeAuditService)){
                continue;
            }
            nodeAuditService.getAuditDataScopeBO(processTodoBO);
        }
        return processTodoBO;
    }

    /**
     * 功能描述:
     * 〈根据应用ID获取审批模块〉
     * @param appId appId
     * @param moduleId moduleId
     * @return 正常返回:{@link List<Long>}
     * @author 蝉鸣
     */
    public List<Long> getAllModules(Long appId,Long moduleId) {
        LambdaQueryChainWrapper<BpmDataFormRel> lambdaQuery = bpmDataFormRelService.lambdaQuery();
//        if(ObjectUtil.isNotEmpty(moduleId)){
//            lambdaQuery.eq(BpmDataFormRel::getModuleId, moduleId);
//        }
        List<BpmDataFormRel> relList = lambdaQuery.eq(BpmDataFormRel::getAppId, appId).list();
        if(CollUtil.isEmpty(relList)){
            return CollUtil.newArrayList();
        }
        return relList.stream().map(BpmDataFormRel::getModuleId).distinct().toList();
    }
}

