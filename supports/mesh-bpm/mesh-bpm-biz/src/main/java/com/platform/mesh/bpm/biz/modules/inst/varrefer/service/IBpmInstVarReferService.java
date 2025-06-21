package com.platform.mesh.bpm.biz.modules.inst.varrefer.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.po.BpmInstVarRefer;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 变量值信息
 * @author 蝉鸣
 */
public interface IBpmInstVarReferService extends IService<BpmInstVarRefer> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstVarValueServiceManual}
     * @author 蝉鸣
     */
    BpmInstVarValueServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param InstProcessId InstProcessId
     * @return 正常返回:{@link List<BpmInstVarRefer>}
     * @author 蝉鸣
     */
    List<BpmInstVarRefer> selectVarReferByInstProcessIdId(Long InstProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstVarRefer>}
     * @author 蝉鸣
     */
    List<BpmInstVarRefer> selectVarReferByInstLineId(Long instLineId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instVariableId instVariableId
     * @return 正常返回:{@link List<BpmInstVarRefer>}
     * @author 蝉鸣
     */
    List<BpmInstVarRefer> selectVarReferByInstVariableId(Long instVariableId);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param instVarRefer instVarRefer
     * @return 正常返回:{@link BpmInstVarRefer}
     * @author 蝉鸣
     */
    BpmInstVarRefer addVarReferInst(BpmInstVarRefer instVarRefer);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instVarReferId instVarReferId
     * @author 蝉鸣
     */
    void deleteVarReferByInstVarReferId(Long instVarReferId);

}

