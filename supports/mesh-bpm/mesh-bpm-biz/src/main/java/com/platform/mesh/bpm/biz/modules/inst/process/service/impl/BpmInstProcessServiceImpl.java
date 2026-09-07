package com.platform.mesh.bpm.biz.modules.inst.process.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmProcessStartDTO;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.enums.InitNodeSubEnum;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.bo.BpmInstProcessTodoBO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.dto.BpmInstProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessOaVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessRunVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.process.mapper.BpmInstProcessMapper;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.modules.inst.process.service.manual.BpmInstProcessServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import com.platform.mesh.bpm.biz.soa.node.pass.enums.NodePassEnum;
import com.platform.mesh.bpm.biz.soa.node.run.enums.NodeRunEnum;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.sys.account.domain.bo.SysAccountBO;
import com.platform.mesh.utils.reflect.ObjFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Service
public class BpmInstProcessServiceImpl extends ServiceImpl<BpmInstProcessMapper, BpmInstProcess> implements IBpmInstProcessService {

    private static final Logger log = LoggerFactory.getLogger(BpmInstProcessServiceImpl.class);

    @Autowired
    private BpmInstProcessServiceManual bpmInstProcessServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstProcessServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcessServiceManual getServiceManual() {
        return bpmInstProcessServiceManual;
    }

    /**
     * 功能描述:
     * 〈启动流程实例〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess initProcessInst(Long tempProcessId){
        //初始化实例信息
        return bpmInstProcessServiceManual.initProcessInst(tempProcessId);
    }

    /**
     * 功能描述:
     * 〈初始化子流程实例〉
     * @param bpmTempNodeSub bpmTempNodeSub
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstNodeSub initSubProcessInst(BpmTempNodeSub bpmTempNodeSub) {
        BpmInstNodeSub instNodeSub = new BpmInstNodeSub();
        BpmInstProcess bpmInstProcess = bpmInstProcessServiceManual.initProcessInst(bpmTempNodeSub.getTempChildProcessId());
        BeanUtil.copyProperties(bpmTempNodeSub, instNodeSub, ObjFieldUtil.ignoreDefault());
        instNodeSub.setInstChildProcessId(bpmInstProcess.getId());
        instNodeSub.setInitFlag(InitNodeSubEnum.INIT.getValue());
        instNodeSub.setChildProcessRunFlag(NodeRunEnum.STAND.getValue());
        instNodeSub.setChildProcessPassFlag(NodePassEnum.INIT.getValue());
        return instNodeSub;
    }

    /**
     * 功能描述:
     * 〈运行流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess runProcessInst(Long instProcessId){
        BpmInstProcess bpmInstProcess = getById(instProcessId);
        //执行开始节点
        bpmInstProcessServiceManual.runProcessInst(bpmInstProcess);
        //返回流程实例
        return bpmInstProcess;
    }

    /**
     * 功能描述:
     * 〈启动流程实例〉
     * @param startDTO startDTO
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess startProcessInst(BpmProcessStartDTO startDTO){
        if(ObjectUtil.isEmpty(startDTO.getTempProcessId())){
            return null;
        }
        //校验当前数据是否已有运行中的流程
        Boolean checkDataRel = bpmInstProcessServiceManual.checkDataRelHasRun(startDTO.getDataId(),startDTO.getTempProcessId(),Boolean.TRUE);
        if(checkDataRel){
            return null;
//            log.info(InstProcessExceptionEnum.DATA_HAS_RUNNING.getDesc());
//            throw InstProcessExceptionEnum.DATA_HAS_RUNNING.getBaseException();
        }
        //初始化流程实例
        BpmInstProcess bpmInstProcess = bpmInstProcessServiceManual.initProcessInst(startDTO.getTempProcessId());
        //保存流程实例与数据关系
        BpmDataInstRel bpmDataInstRel = bpmInstProcessServiceManual.addDataInstRel(bpmInstProcess, startDTO);
        if(YesOrNoEnum.YES.getValue().equals(startDTO.getAutoStart())){
            //判断是否需要执行开始节点
            bpmInstProcessServiceManual.runProcessInst(bpmInstProcess);
            bpmInstProcess = this.getById(bpmInstProcess.getId());
        }
        //持久化消息信息
        bpmInstProcessServiceManual.saveAndSendBpmMsg(bpmInstProcess, bpmDataInstRel);
        return bpmInstProcess;
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcessRunVO}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcessRunVO getProcessInstRunInfo(Long instProcessId){
        //获取流程实例
        BpmInstProcess instProcess = getById(instProcessId);
        //获取VO对象
        return bpmInstProcessServiceManual.getProcessInstRunInfo(instProcess);
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcessDesignVO getProcessInst(Long instProcessId) {
        BpmInstProcessDesignVO processDesignVO = new BpmInstProcessDesignVO();
        //获取流程信息
        BpmInstProcess instProcess = this.getById(instProcessId);
        BpmInstProcessVO instProcessVO = BeanUtil.copyProperties(instProcess, BpmInstProcessVO.class);
        processDesignVO.setProcessVO(instProcessVO);
        return bpmInstProcessServiceManual.getProcessInst(processDesignVO);
    }

    /**
     * 功能描述:
     * 〈停止流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess stopProcessInst(Long instProcessId){
        BpmInstProcess instProcess = getById(instProcessId);
        instProcess.setRunFlag(ProcessRunEnum.STAND.getValue());
        return instProcess;
    }

    /**
     * 功能描述:
     * 〈作废流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess cancelProcessInst(Long instProcessId){
        BpmInstProcess instProcess = getById(instProcessId);
        instProcess.setRunFlag(ProcessRunEnum.CANCEL.getValue());
        return instProcess;
    }

    /**
     * 功能描述:
     * 〈关闭流程实例〉
     * @param instNodeSubDTO instNodeSubDTO
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess addSubProcessInst(BpmInstNodeSubDTO instNodeSubDTO){
        return null;
    }

    /**
     * 功能描述:
     * 〈获取流程实例-待办〉
     * @param pageDTO pageDTO
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmInstProcessOaVO> getProcessInstTodo(BpmInstProcessPageDTO pageDTO, Long accountId) {
        //获取当前可以查询的审批moduleIds
        List<Long> allModules = bpmInstProcessServiceManual.getAllModules(pageDTO.getAppId(),pageDTO.getModuleId());
        if(CollUtil.isEmpty(allModules)){
            return new PageVO<>();
        }
        BpmInstProcessTodoBO processTodoBO = new BpmInstProcessTodoBO();
        processTodoBO.setAccountId(accountId);
        processTodoBO.setRunFlag(ProcessRunEnum.RUNNING.getValue());
        processTodoBO.setInstRootId(NumberConst.NUM_0.longValue());
        processTodoBO = bpmInstProcessServiceManual.getProcessTodoBO(processTodoBO);
        processTodoBO.setModuleIds(allModules);
        MPage<BpmInstProcess> processMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmInstProcess.class);
        MPage<BpmInstProcessOaVO> instToDo = this.baseMapper.getProcessInstTodo(processMPage, processTodoBO);
        return MPageUtil.convertToVO(instToDo, BpmInstProcessOaVO.class);
    }

    /**
     * 功能描述:
     * 〈获取流程实例-已办〉
     * @param pageDTO pageDTO
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmInstProcessOaVO> getProcessInstDone(BpmInstProcessPageDTO pageDTO, Long accountId) {
        //获取当前可以查询的审批moduleIds
        List<Long> allModules = bpmInstProcessServiceManual.getAllModules(pageDTO.getAppId(),pageDTO.getModuleId());
        if(CollUtil.isEmpty(allModules)){
            return new PageVO<>();
        }
        BpmInstProcessTodoBO processTodoBO = new BpmInstProcessTodoBO();
        processTodoBO.setAccountId(accountId);
        processTodoBO.setRunFlag(ProcessRunEnum.RUNNING.getValue());
        processTodoBO.setInstRootId(NumberConst.NUM_0.longValue());
        processTodoBO = bpmInstProcessServiceManual.getProcessTodoBO(processTodoBO);
        processTodoBO.setModuleIds(allModules);
        processTodoBO.setAuditPass(CollUtil.newArrayList(NodePassEnum.PASS.getValue(),NodePassEnum.UN_PASS.getValue(),NodePassEnum.UN_VALID.getValue()));
        MPage<BpmInstProcess> processMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmInstProcess.class);
        MPage<BpmInstProcessOaVO> instToDo = this.baseMapper.getProcessInstDone(processMPage, processTodoBO);
        return MPageUtil.convertToVO(instToDo, BpmInstProcessOaVO.class);
    }

    /**
     * 功能描述:
     * 〈获取流程实例-归档〉
     * @param pageDTO pageDTO
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmInstProcessOaVO> getProcessInstEnd(BpmInstProcessPageDTO pageDTO, Long accountId) {
        //获取当前可以查询的审批moduleIds
        List<Long> allModules = bpmInstProcessServiceManual.getAllModules(pageDTO.getAppId(),pageDTO.getModuleId());
        if(CollUtil.isEmpty(allModules)){
            return new PageVO<>();
        }
        BpmInstProcessTodoBO processTodoBO = new BpmInstProcessTodoBO();
        processTodoBO.setAccountId(accountId);
        processTodoBO.setRunFlag(ProcessRunEnum.END.getValue());
        processTodoBO.setInstRootId(NumberConst.NUM_0.longValue());
        processTodoBO = bpmInstProcessServiceManual.getProcessTodoBO(processTodoBO);
        processTodoBO.setModuleIds(allModules);
        MPage<BpmInstProcess> processMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmInstProcess.class);
        MPage<BpmInstProcessOaVO> instToDo = this.baseMapper.getProcessInstEnd(processMPage, processTodoBO);
        return MPageUtil.convertToVO(instToDo, BpmInstProcessOaVO.class);
    }


    /**
     * 功能描述:
     * 〈获取流程实例-跟进〉
     * @param pageDTO pageDTO
     * @param accountId accountId
     * @return 正常返回:{@link List<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    @Override
    public PageVO<BpmInstProcessOaVO> getProcessInstFollow(BpmInstProcessPageDTO pageDTO, Long accountId) {
        //获取当前可以查询的审批moduleIds
        List<Long> allModules = bpmInstProcessServiceManual.getAllModules(pageDTO.getAppId(),pageDTO.getModuleId());
        if(CollUtil.isEmpty(allModules)){
            return new PageVO<>();
        }
        SysAccountBO accountBO = UserCacheUtil.getAccountInfoCache(accountId);
        if(ObjectUtil.isEmpty(accountBO)){
            return new PageVO<>();
        }
        BpmInstProcessTodoBO processTodoBO = new BpmInstProcessTodoBO();
        processTodoBO.setUserId(accountBO.getUserId());
        processTodoBO.setAccountId(accountId);
        processTodoBO.setInstRootId(NumberConst.NUM_0.longValue());
        processTodoBO = bpmInstProcessServiceManual.getProcessTodoBO(processTodoBO);
        processTodoBO.setModuleIds(allModules);
        MPage<BpmInstProcess> processMPage = MPageUtil.pageEntityToMPage(pageDTO, BpmInstProcess.class);
        MPage<BpmInstProcessOaVO> instToDo = this.baseMapper.getProcessInstFollow(processMPage, processTodoBO);
        return MPageUtil.convertToVO(instToDo, BpmInstProcessOaVO.class);
    }


    /**
     * 功能描述:
     * 〈处理流程实例消息〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    @Override
    public void handleInstProcessMsg(Long instProcessId){
        BpmInstProcess bpmInstProcess = this.getById(instProcessId);
        //保存流程实例与数据关系
        BpmDataInstRel bpmDataInstRel = bpmInstProcessServiceManual.getDataInstRel(instProcessId);
        //持久化消息信息，发送审批回调消息
        bpmInstProcessServiceManual.saveAndSendBpmMsg(bpmInstProcess, bpmDataInstRel);
    }

    /**
     * 功能描述:
     * 〈提交流程,可以进行审批〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    @Override
    public Boolean commitInstProcess(Long instProcessId) {
        BpmInstProcess instProcess = getById(instProcessId);
        instProcess.setCommitFlag(YesOrNoEnum.YES.getValue());
        this.updateById(instProcess);
        return Boolean.TRUE;
    }

}

