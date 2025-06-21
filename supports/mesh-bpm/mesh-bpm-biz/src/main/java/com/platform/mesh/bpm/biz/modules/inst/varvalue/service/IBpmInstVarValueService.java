package com.platform.mesh.bpm.biz.modules.inst.varvalue.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.domain.po.BpmInstVarValue;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 变量值信息
 * @author 蝉鸣
 */
public interface IBpmInstVarValueService extends IService<BpmInstVarValue> {

    /**
     * 功能描述:
     * 〈获取实例下变量值信息〉
     * @param InstProcessId InstProcessId
     * @return 正常返回:{@link List<BpmInstVarValue>}
     * @author 蝉鸣
     */
    List<BpmInstVarValue> selectVarValuesByInstProcessIdId(Long InstProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下变量值信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstVarValue>}
     * @author 蝉鸣
     */
    List<BpmInstVarValue> selectVarValueByInstNodeId(Long instNodeId);

    /**
     * 功能描述:
     * 〈添加变量值信息〉
     * @param instVarValue instVarValue
     * @return 正常返回:{@link BpmInstVarValue}
     * @author 蝉鸣
     */
    BpmInstVarValue addVarValueInst(BpmInstVarValue instVarValue);
}

