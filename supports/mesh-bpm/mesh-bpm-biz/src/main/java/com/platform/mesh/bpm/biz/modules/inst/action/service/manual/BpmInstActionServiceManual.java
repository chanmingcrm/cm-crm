package com.platform.mesh.bpm.biz.modules.inst.action.service.manual;


import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.soa.action.ActionService;
import com.platform.mesh.bpm.biz.soa.action.enums.ActionTypeEnum;
import com.platform.mesh.bpm.biz.soa.action.factory.ActionFactory;
import com.platform.mesh.utils.function.FutureHandleUtil;
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
public class BpmInstActionServiceManual {

    @Autowired
    ActionFactory<BpmInstAction> actionFactory;


    /**
     * 执行当前动作信息
     * @param instAction
     */
    public void handleInstAction(BpmInstAction instAction) {
        //判断动作类型
        //执行动作流程
        //查询动作流程下所有事件
    }

    public void handleInstAction(List<BpmInstAction> bpmInstActions) {
        //将节点按照类型分类
        Map<Integer, List<BpmInstAction>> instActionMap = bpmInstActions
                .stream()
                .filter(item -> ObjectUtil.isNotEmpty(item.getActionType()))
                .collect(Collectors.groupingBy(BpmInstAction::getActionType));
        //执行节点
        instActionMap.forEach((key,value)->{
            //执行节点信息，可直接执行的不需要将节点再放入工厂中

            //按照不同的分类调用不同的工厂服务
            ActionTypeEnum enumByValue = BaseEnum.getEnumByValue(ActionTypeEnum.class, key);
            ActionService<BpmInstAction> actionService = actionFactory.getActionService(enumByValue);
            FutureHandleUtil.runWithResult(value,actionService::handle);
        });
    }
}

