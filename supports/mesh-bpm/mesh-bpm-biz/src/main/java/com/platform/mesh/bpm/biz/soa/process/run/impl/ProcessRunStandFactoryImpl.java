package com.platform.mesh.bpm.biz.soa.process.run.impl;

import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 结束节点工厂实现
 * @author 蝉鸣
 */
@Service
public class ProcessRunStandFactoryImpl implements ProcessRunService<BpmInstProcess> {

    private final static Logger log = LoggerFactory.getLogger(ProcessRunStandFactoryImpl.class);

    /**
     * 功能描述:
     * 〈过程运行类型〉
     * @return 正常返回:{@link ProcessRunEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessRunEnum processRun() {
        return ProcessRunEnum.STAND;
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
        return bpmInstProcess;
    }

}
