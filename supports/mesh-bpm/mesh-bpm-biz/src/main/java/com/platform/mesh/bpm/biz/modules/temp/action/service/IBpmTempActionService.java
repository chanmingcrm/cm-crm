package com.platform.mesh.bpm.biz.modules.temp.action.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.po.BpmTempAction;
import com.platform.mesh.bpm.biz.modules.temp.action.service.manual.BpmTempActionServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 动作信息
 * @author 蝉鸣
 */
public interface IBpmTempActionService extends IService<BpmTempAction> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempActionServiceManual}
     * @author 蝉鸣
     */
    BpmTempActionServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取模板下动作信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempAction>}
     * @author 蝉鸣
     */
    List<BpmTempAction> selectActionsByTemplateId(Long tempProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link List<BpmTempAction>}
     * @author 蝉鸣
     */
    List<BpmTempAction> selectActionsByNodeId(Long tempNodeId);

}

