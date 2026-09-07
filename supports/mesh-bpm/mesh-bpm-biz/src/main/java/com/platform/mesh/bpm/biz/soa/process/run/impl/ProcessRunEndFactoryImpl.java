package com.platform.mesh.bpm.biz.soa.process.run.impl;

import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 定时节点工厂实现
 * @author 蝉鸣
 */
@Service
public class ProcessRunEndFactoryImpl implements ProcessRunService<BpmInstProcess> {

    private final static Logger log = LoggerFactory.getLogger(ProcessRunEndFactoryImpl.class);

    /**
     * 功能描述:
     * 〈过程运行类型〉
     * @return 正常返回:{@link ProcessRunEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessRunEnum processRun() {
        return ProcessRunEnum.END;
    }

    /**
     * 功能描述:
     * 〈单流程处理〉
     * @param instProcess instNode
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess handle(BpmInstProcess instProcess) {
        IBpmInstProcessService instProcessService = SpringContextHolderUtil.getBean(IBpmInstProcessService.class);
        BpmInstProcess bpmInstProcess = instProcessService.runProcessInst(instProcess.getId());
        //如果是顶层流程则发送消息
        if(bpmInstProcess.getInstRootId().equals(NumberConst.NUM_0.longValue())){
            //发送审批回调消息
            instProcessService.handleInstProcessMsg(instProcess.getId());
        }
        return instProcess;
    }


}
