package com.platform.mesh.bpm.biz.modules.inst.action.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.action.domain.po.BpmInstAction;
import com.platform.mesh.bpm.biz.modules.inst.action.mapper.BpmInstActionMapper;
import com.platform.mesh.bpm.biz.modules.inst.action.service.IBpmInstActionService;
import com.platform.mesh.bpm.biz.modules.inst.action.service.manual.BpmInstActionServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 动作信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstActionServiceImpl extends ServiceImpl<BpmInstActionMapper, BpmInstAction> implements IBpmInstActionService {


    @Autowired
    private BpmInstActionServiceManual bpmInstActionServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstActionServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstActionServiceManual getServiceManual() {
        return bpmInstActionServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取实例下动作信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstAction> selectActionsByInstProcessId(Long instProcessId) {
        //根据流程实例ID查询所有的动作
        return this.lambdaQuery().eq(BpmInstAction::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstAction> selectActionsByInstNodeId(Long instNodeId) {
        //根据节点ID查询所有的动作
        return this.lambdaQuery().eq(BpmInstAction::getInstNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param instNodeId instNodeId
     * @param actionType actionType
     * @return 正常返回:{@link List<BpmInstAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstAction> selectActionsByInstNodeIdAndNodeType(Long instNodeId, Integer actionType) {
        //根据节点ID查询所有的动作
        return this.lambdaQuery()
                .eq(BpmInstAction::getInstNodeId,instNodeId)
                .eq(BpmInstAction::getActionType,actionType)
                .list();
    }

    /**
     * 功能描述:
     * 〈添加动作信息〉
     * @param instAction instAction
     * @return 正常返回:{@link BpmInstAction}
     * @author 蝉鸣
     */
    @Override
    public BpmInstAction addActionInst(BpmInstAction instAction) {
        //添加动作信息
        this.save(instAction);
        return instAction;
    }

    /**
     * 功能描述:
     * 〈删除动作信息〉
     * @param instActionId instActionId
     * @author 蝉鸣
     */
    @Override
    public void deleteActionInstActionId(Long instActionId) {
        //删除动作信息
        this.removeById(instActionId);
    }

    /**
     * 功能描述:
     * 〈执行当前动作信息〉
     * @param instAction instAction
     * @author 蝉鸣
     */
    @Override
    public void handleInstAction(BpmInstAction instAction) {
        //执行当前动作信息
        bpmInstActionServiceManual.handleInstAction(instAction);
    }

    /**
     * 功能描述:
     * 〈执行当前动作信息〉
     * @param instActions instActions
     * @author 蝉鸣
     */
    @Override
    public void handleInstAction(List<BpmInstAction> instActions) {
        if(CollUtil.isEmpty(instActions)){
            return;
        }
        //执行当前动作信息
        bpmInstActionServiceManual.handleInstAction(instActions);
    }
}

