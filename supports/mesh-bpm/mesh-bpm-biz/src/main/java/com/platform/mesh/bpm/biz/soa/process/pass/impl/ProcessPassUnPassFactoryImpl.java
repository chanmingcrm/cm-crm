package com.platform.mesh.bpm.biz.soa.process.pass.impl;

import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.soa.process.pass.enums.ProcessPassEnum;
import com.platform.mesh.bpm.biz.soa.action.factory.ActionFactory;
import com.platform.mesh.bpm.biz.soa.process.pass.ProcessPassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 流程通过状态工厂实现
 * @author 蝉鸣
 */
@Service
public class ProcessPassUnPassFactoryImpl implements ProcessPassService<BpmInstProcess> {

    private final static Logger log = LoggerFactory.getLogger(ProcessPassUnPassFactoryImpl.class);

    @Autowired
    private ActionFactory<BpmInstAction> actionFactory;

    @Autowired
    private ActionFactory<BpmInstLine> lineFactory;

    @Autowired
    private ActionFactory<BpmInstVariable> variableFactory;

    @Autowired
    private ActionFactory<BpmInstEvent> eventFactory;

    /**
     * 功能描述:
     * 〈过程通过类型〉
     * @return 正常返回:{@link ProcessPassEnum}
     * @author 蝉鸣
     */
    @Override
    public ProcessPassEnum processPass() {
        return ProcessPassEnum.UN_PASS;
    }

    /**
     * 功能描述:
     * 〈单流程处理〉
     * @param instProcess instProcess
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    @Override
    public BpmInstProcess handle(BpmInstProcess instProcess) {
        return instProcess;
    }

}
