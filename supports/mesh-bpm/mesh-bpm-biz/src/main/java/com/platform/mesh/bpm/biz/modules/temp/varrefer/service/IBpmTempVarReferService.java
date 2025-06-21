package com.platform.mesh.bpm.biz.modules.temp.varrefer.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.po.BpmTempVarRefer;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 变量值信息
 * @author 蝉鸣
 */
public interface IBpmTempVarReferService extends IService<BpmTempVarRefer> {

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
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    List<BpmTempVarRefer> selectVarReferByTempProcessIdId(Long tempProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempLineId tempLineId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    List<BpmTempVarRefer> selectVarReferByTempLineId(Long tempLineId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempVariableId tempVariableId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    List<BpmTempVarRefer> selectVarReferByTempVariableId(Long tempVariableId);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param tempVarValue tempVarValue
     * @return 正常返回:{@link BpmTempVarRefer}
     * @author 蝉鸣
     */
    BpmTempVarRefer addVarReferInst(BpmTempVarRefer tempVarValue);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempVarReferId tempVarReferId
     * @author 蝉鸣
     */
    void deleteVarReferByInstVarReferId(Long tempVarReferId);

}

