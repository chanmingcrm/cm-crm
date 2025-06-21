package com.platform.mesh.bpm.biz.modules.temp.variable.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.po.BpmTempVariable;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 变量信息
 * @author 蝉鸣
 */
public interface IBpmTempVariableService extends IService<BpmTempVariable> {

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempVariable>}
     * @author 蝉鸣
     */
    List<BpmTempVariable> selectVariablesByTempProcessIdId(Long tempProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempLineId tempLineId
     * @return 正常返回:{@link List<BpmTempVariable>}
     * @author 蝉鸣
     */
    List<BpmTempVariable> selectVariableByTempLineId(Long tempLineId);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param tempVariable tempVariable
     * @return 正常返回:{@link BpmTempVariable}
     * @author 蝉鸣
     */
    BpmTempVariable addVariableTemp(BpmTempVariable tempVariable);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempVariableId tempVariableId
     * @author 蝉鸣
     */
    void deleteVariableByTempVariableId(Long tempVariableId);

}

