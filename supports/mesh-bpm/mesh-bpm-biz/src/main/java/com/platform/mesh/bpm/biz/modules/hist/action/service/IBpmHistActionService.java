package com.platform.mesh.bpm.biz.modules.hist.action.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.hist.action.domain.po.BpmHistAction;
import com.platform.mesh.bpm.biz.modules.hist.action.service.manual.BpmHistActionServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 动作信息
 * @author 蝉鸣
 */
public interface IBpmHistActionService extends IService<BpmHistAction> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmHistActionServiceManual}
     * @author 蝉鸣
     */
    BpmHistActionServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取实例下动作信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmHistAction>}
     * @author 蝉鸣
     */
    List<BpmHistAction> selectActionsByInstProcessId(Long instProcessId);

    /**
     * 功能描述:
     * 〈添加动作信息〉
     * @param HistAction HistAction
     * @return 正常返回:{@link BpmHistAction}
     * @author 蝉鸣
     */
    BpmHistAction addActionHist(BpmHistAction HistAction);

    /**
     * 功能描述:
     * 〈删除动作信息〉
     * @param actionId actionId
     * @author 蝉鸣
     */
    void deleteActionHistActionId(Long actionId);

}

