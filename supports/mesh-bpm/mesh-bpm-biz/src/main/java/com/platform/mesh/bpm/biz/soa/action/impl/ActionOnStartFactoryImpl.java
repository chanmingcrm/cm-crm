package com.platform.mesh.bpm.biz.soa.action.impl;

import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.event.domain.po.BpmInstEvent;
import com.platform.mesh.bpm.biz.modules.inst.event.service.IBpmInstEventService;
import com.platform.mesh.bpm.biz.soa.action.ActionService;
import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 动作工厂实现
 * @author 蝉鸣
 */
@Service
public class ActionOnStartFactoryImpl implements ActionService<BpmInstAction> {

    @Autowired
    private IBpmInstEventService bpmInstEventService;

    /**
     * 功能描述:
     * 〈动作类型〉
     * @return 正常返回:{@link ActionTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public ActionTypeEnum actionType() {
        return ActionTypeEnum.ON_START;
    }

    /**
     * 功能描述:
     * 〈动作处理〉
     * @param instAction instAction
     * @return 正常返回:{@link BpmInstAction}
     * @author 蝉鸣
     */
    @Override
    public BpmInstAction handle(BpmInstAction instAction) {
        List<BpmInstEvent> instEvents = bpmInstEventService.selectEventByInstActionId(instAction.getId());
        bpmInstEventService.handleInstEvent(instEvents);
        return instAction;
    }

    /**
     * 功能描述:
     * 〈动作处理〉
     * @param instAction instAction
     * @author 蝉鸣
     */
    @Override
    public void handle(List<BpmInstAction> instAction) {

    }

}
