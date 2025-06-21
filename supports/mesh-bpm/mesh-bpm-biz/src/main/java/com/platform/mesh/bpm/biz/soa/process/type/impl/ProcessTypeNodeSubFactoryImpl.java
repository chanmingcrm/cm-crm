package com.platform.mesh.bpm.biz.soa.process.type.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.hist.process.service.IBpmHistProcessService;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.enums.InitNodeSubEnum;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.service.IBpmInstNodeSubService;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.vo.BpmTempNodeVO;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.service.IBpmTempNodeSubService;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.process.service.IBpmTempProcessService;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.utils.function.FutureHandleUtil;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ProcessTypeNodeSubFactoryImpl implements ProcessTypeService {

    private final static Logger log = LoggerFactory.getLogger(ProcessTypeNodeSubFactoryImpl.class);

    @Autowired
    private IBpmTempNodeSubService bpmTempNodeSubService;

    @Autowired
    private IBpmInstNodeSubService bpmInstNodeSubService;

    /**
     * 功能描述:
     * 〈过程类型〉
     * @return 正常返回:{@link ProcessTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessTypeEnum processType() {
        return ProcessTypeEnum.NODE_SUB;
    }

    /**
     * 功能描述:
     * 〈添加模板〉
     * @author 蝉鸣
     */
    @Override
    public void addTemp(BpmTempProcessDesignDTO addDTO) {
        //节点有子流程
        addDTO
            .getNodeDTOs()
            .stream()
            .filter(item->CollUtil.isNotEmpty(item.getProcessAddDTOs()))
            .forEach(nodeDTO->{
                //有流程子项
                IBpmTempProcessService tempProcessService = SpringContextHolderUtil.getBean(IBpmTempProcessService.class);
                //递归创建流程模板
                List<BpmTempProcess> bpmTempProcesses = FutureHandleUtil.runWithResult(nodeDTO.getProcessAddDTOs(), tempProcessService::designProcessTemp);
                List<BpmTempNodeSub> nodeSubs = CollUtil.newArrayList();
                //设置流程子项
                bpmTempProcesses.forEach(item->{
                    BpmTempNodeSub nodeSub = new BpmTempNodeSub();
                    nodeSub.setTempProcessId(nodeDTO.getTempProcessId());
                    nodeSub.setTempProcessHash(addDTO.getProcessDTO().getProcessHash());
                    nodeSub.setTempNodeHash(nodeDTO.getNodeHash());
                    nodeSub.setTempNodeId(nodeDTO.getNodeId());
                    nodeSub.setNodeFlag(nodeDTO.getNodeFlag());
                    nodeSub.setTempChildProcessId(item.getId());
                    nodeSub.setTempChildProcessHash(item.getProcessHash());
                    nodeSubs.add(nodeSub);
                });
                //保存流程子项
                bpmTempNodeSubService.saveBatch(nodeSubs);
            });
    }

    /**
     * 功能描述:
     * 〈获取模板〉
     * @author 蝉鸣
     */
    @Override
    public void getTemp(BpmTempProcessDesignVO getVO) {
        BpmTempProcessVO processVO = getVO.getProcessVO();
        List<BpmTempNodeVO> nodeVOs = getVO.getNodeVOs();
        //获取子流程
        List<BpmTempNodeSub> tempSubNodes = bpmTempNodeSubService.lambdaQuery()
                .eq(BpmTempNodeSub::getTempProcessId,processVO.getId()).list();
        if(CollUtil.isEmpty(tempSubNodes)){
            return;
        }
        Map<Long, List<BpmTempNodeSub>> nodeSubMap = tempSubNodes.stream().collect(Collectors.groupingBy(BpmTempNodeSub::getTempNodeId));
        nodeVOs.forEach(nodeVO->{
            if (nodeSubMap.containsKey(nodeVO.getId())) {
                List<BpmTempNodeSub> bpmTempNodeSubs = nodeSubMap.get(nodeVO.getId());
                List<Long> childProcessIds = bpmTempNodeSubs.stream().map(BpmTempNodeSub::getTempChildProcessId).toList();
                //查询流程子项
                IBpmTempProcessService tempProcessService = SpringContextHolderUtil.getBean(IBpmTempProcessService.class);
                //递归创建流程模板
                List<BpmTempProcessDesignVO> processDesignVOS = FutureHandleUtil.runWithResult(childProcessIds, tempProcessService::getProcessTemp);
                processDesignVOS.forEach(processDesignVO->{
                    processDesignVO.getProcessVO().setParentProcessId(processVO.getId());
                });
                //填充流程模板信息
                nodeVO.setProcessDesignVOs(processDesignVOS);
            }
        });
    }


    /**
     * 功能描述:
     * 〈删除模板〉
     * @author 蝉鸣
     */
    @Override
    public void delTemp(Long tempProcessId) {
        //获取子流程
        List<BpmTempNodeSub> tempSubNodes = bpmTempNodeSubService.lambdaQuery().eq(BpmTempNodeSub::getTempProcessId,tempProcessId).list();
        if(CollUtil.isEmpty(tempSubNodes)){
            return;
        }
        List<Long> childProcessIds = tempSubNodes.stream().map(BpmTempNodeSub::getTempChildProcessId).distinct().toList();
        //查询流程子项
        IBpmTempProcessService tempProcessService = SpringContextHolderUtil.getBean(IBpmTempProcessService.class);
        boolean exists = tempProcessService.lambdaQuery().in(BpmTempProcess::getId, childProcessIds).exists();
        if(exists){
            //递归创建流程模板
            FutureHandleUtil.runWithResult(childProcessIds, tempProcessService::delProcessTemp);
        }
        //删除子流程
        bpmTempNodeSubService.lambdaUpdate().eq(BpmTempNodeSub::getTempProcessId,tempProcessId).remove();
    }

    /**
     * 功能描述:
     * 〈初始化实例〉
     * @param instProcess instProcess
     * @author 蝉鸣
     */
    @Override
    public void initInst(BpmInstProcess instProcess) {
        log.info("初始化节点子项");
        //获取模板节点子项
        List<BpmTempNodeSub> bpmTempNodeSubs = bpmTempNodeSubService.selectNodeSubsByTemplateId(instProcess.getTempProcessId());
        if(CollUtil.isEmpty(bpmTempNodeSubs)){
            return;
        }
        List<Long> tempNodeIds = bpmTempNodeSubs.stream().map(BpmTempNodeSub::getTempNodeId).toList();
        IBpmInstNodeService instNodeService = SpringContextHolderUtil.getBean(IBpmInstNodeService.class);
        //查询父节点实例
        List<BpmInstNode> bpmInstNodes = instNodeService.lambdaQuery().eq(BpmInstNode::getInstProcessId,instProcess.getId())
                .in(BpmInstNode::getTempNodeId,tempNodeIds).list();
        Map<Long, List<BpmInstNode>> instNodeMap = bpmInstNodes.stream().collect(Collectors.groupingBy(BpmInstNode::getTempNodeId));
        //初始化子流程
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        List<BpmInstNodeSub> instNodeSubs = FutureHandleUtil.runWithResult(bpmTempNodeSubs, instProcessService::initSubProcessInst);
        //补充子流程信息
        for (BpmInstNodeSub instNodeSub : instNodeSubs) {
            //设置父流程实例ID
            instNodeSub.setInstProcessId(instProcess.getId());
            //设置父节点实例ID
            if(ObjectUtil.isNotEmpty(instNodeMap) && instNodeMap.containsKey(instNodeSub.getTempNodeId())) {
                instNodeSub.setInstNodeId(CollUtil.getFirst(instNodeMap.get(instNodeSub.getTempNodeId())).getId());
            }
        }
        IBpmInstNodeSubService instNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        instNodeSubService.saveBatch(instNodeSubs);
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
        List<BpmInstNodeVO> nodeVOs = getVO.getNodeVOs();
        IBpmInstNodeSubService bpmInstNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        //获取子流程
        List<BpmInstNodeSub> instSubNodes = bpmInstNodeSubService.lambdaQuery()
                .eq(BpmInstNodeSub::getInstProcessId,processVO.getId()).list();
        if(CollUtil.isEmpty(instSubNodes)){
            return;
        }
        Map<Long, List<BpmInstNodeSub>> nodeSubMap = instSubNodes.stream().collect(Collectors.groupingBy(BpmInstNodeSub::getInstNodeId));
        nodeVOs.forEach(nodeVO->{
            if (nodeSubMap.containsKey(nodeVO.getId())) {
                List<BpmInstNodeSub> bpmInstNodeSubs = nodeSubMap.get(nodeVO.getId());
                List<Long> childProcessIds = bpmInstNodeSubs.stream().map(BpmInstNodeSub::getInstChildProcessId).toList();
                //查询流程子项
                IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
                //递归创建流程模板
                List<BpmInstProcessDesignVO> processDesignVOS = FutureHandleUtil.runWithResult(childProcessIds, instProcessService::getProcessInst);
                processDesignVOS.forEach(processDesignVO->{
                    processDesignVO.getProcessVO().setParentProcessId(processVO.getId());
                });
                //填充流程模板信息
                nodeVO.setProcessDesignVOs(processDesignVOS);
            }
        });
    }

    /**
     * 功能描述:
     * 〈获取实例历史〉
     * @param getVO getVO
     * @author 蝉鸣
     */
    @Override
    public void getHist(BpmHistProcessInfoVO getVO) {
        List<BpmHistNodeVO> nodeVOs = getVO.getNodeVOs();
        IBpmInstNodeSubService bpmInstNodeSubService = SpringContextHolderUtil.getBean(IBpmInstNodeSubService.class);
        //获取子流程
        List<BpmInstNodeSub> instSubNodes = bpmInstNodeSubService.lambdaQuery()
                .eq(BpmInstNodeSub::getInstProcessId,getVO.getInstProcessId()).list();
        if(CollUtil.isEmpty(instSubNodes)){
            return;
        }
        Map<Long, List<BpmInstNodeSub>> nodeSubMap = instSubNodes.stream().collect(Collectors.groupingBy(BpmInstNodeSub::getInstNodeId));
        nodeVOs.forEach(nodeVO->{
            if (nodeSubMap.containsKey(nodeVO.getId())) {
                List<BpmInstNodeSub> bpmInstNodeSubs = nodeSubMap.get(nodeVO.getId());
                List<Long> childProcessIds = bpmInstNodeSubs.stream().map(BpmInstNodeSub::getInstChildProcessId).toList();
                //查询流程子项
                IBpmHistProcessService histProcessService = SpringContextHolderUtil.getBean(IBpmHistProcessService.class);
                //递归创建流程模板
                List<BpmHistProcessInfoVO> processHistVOS = FutureHandleUtil.runWithResult(childProcessIds, histProcessService::getProcessHistInfo);
                //填充流程模板信息
                nodeVO.setProcessVOs(processHistVOS);
            }
        });
    }
}
