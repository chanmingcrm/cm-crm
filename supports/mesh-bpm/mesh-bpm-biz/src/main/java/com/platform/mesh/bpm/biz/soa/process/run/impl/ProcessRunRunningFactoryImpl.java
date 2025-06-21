package com.platform.mesh.bpm.biz.soa.process.run.impl;

import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.soa.process.run.ProcessRunService;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description 开始节点工厂实现
 * @author 蝉鸣
 */
@Service
public class ProcessRunRunningFactoryImpl implements ProcessRunService<BpmInstProcess> {

    private final static Logger log = LoggerFactory.getLogger(ProcessRunRunningFactoryImpl.class);

    /**
     * 功能描述:
     * 〈过程运行类型〉
     * @return 正常返回:{@link ProcessRunEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessRunEnum processRun() {
        return ProcessRunEnum.RUNNING;
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
        return instProcess;
    }

}
