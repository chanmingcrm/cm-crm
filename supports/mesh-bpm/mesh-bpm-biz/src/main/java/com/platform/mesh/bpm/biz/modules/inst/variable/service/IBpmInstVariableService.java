package com.platform.mesh.bpm.biz.modules.inst.variable.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 变量信息
 * @author 蝉鸣
 */
public interface IBpmInstVariableService extends IService<BpmInstVariable> {

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    List<BpmInstVariable> selectVariablesByInstProcessIdId(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    List<BpmInstVariable> selectVariableByInstLineId(Long instLineId);

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineIds instLineIds
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    List<BpmInstVariable> selectVariableByInstLineIds(List<Long> instLineIds);

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param instVariable instVariable
     * @return 正常返回:{@link BpmInstVariable}
     * @author 蝉鸣
     */
    BpmInstVariable addVariableInst(BpmInstVariable instVariable);

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instVariableId instVariableId
     * @author 蝉鸣
     */
    void deleteVariableByInstVariableId(Long instVariableId);

    /**
     * 功能描述:
     * 〈校验变量参数〉
     * @param instLine instLine
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean checkVariableByLine(BpmInstLine instLine);
}

