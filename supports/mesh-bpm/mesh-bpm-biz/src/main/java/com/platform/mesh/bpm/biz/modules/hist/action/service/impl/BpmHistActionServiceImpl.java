package com.platform.mesh.bpm.biz.modules.hist.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.hist.action.domain.po.BpmHistAction;
import com.platform.mesh.bpm.biz.modules.hist.action.mapper.BpmHistActionMapper;
import com.platform.mesh.bpm.biz.modules.hist.action.service.IBpmHistActionService;
import com.platform.mesh.bpm.biz.modules.hist.action.service.manual.BpmHistActionServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 历史动作信息
 * @author 蝉鸣
 */
@Service()
public class BpmHistActionServiceImpl extends ServiceImpl<BpmHistActionMapper, BpmHistAction> implements IBpmHistActionService {


    @Autowired
    private BpmHistActionServiceManual bpmHistActionServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmHistActionServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmHistActionServiceManual getServiceManual() {
        return bpmHistActionServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取实例下动作信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmHistAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmHistAction> selectActionsByInstProcessId(Long instProcessId) {
        //根据流程实例ID查询所有的动作
        return this.lambdaQuery().eq(BpmHistAction::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈添加动作信息〉
     * @param HistAction HistAction
     * @return 正常返回:{@link BpmHistAction}
     * @author 蝉鸣
     */
    @Override
    public BpmHistAction addActionHist(BpmHistAction HistAction) {
        //添加动作信息
        this.save(HistAction);
        return HistAction;
    }

    /**
     * 功能描述:
     * 〈删除动作信息〉
     * @param histActionId histActionId
     * @author 蝉鸣
     */
    @Override
    public void deleteActionHistActionId(Long histActionId) {
        //删除动作信息
        this.removeById(histActionId);
    }
}

