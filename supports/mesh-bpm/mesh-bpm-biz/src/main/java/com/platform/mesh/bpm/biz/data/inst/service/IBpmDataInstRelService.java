package com.platform.mesh.bpm.biz.data.inst.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.data.inst.service.manual.BpmDataInstRelServiceManual;
import com.platform.mesh.utils.result.Result;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 数据流程实例
 * @author 蝉鸣
 */
public interface IBpmDataInstRelService extends IService<BpmDataInstRel> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmDataInstRelServiceManual}
     * @author 蝉鸣
     */
    BpmDataInstRelServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link List<BpmDataInstRel>}
     * @author 蝉鸣
     */
    List<BpmDataInstRel> getDataInstRelByDataId(Long dataId);

    /**
     * 功能描述:
     * 〈校验数据是否已经有运行中的流程审批〉
     * @param dataId dataId
     * @param tempProcessId tempProcessId
     * @param filterProcessId filterProcessId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean checkDataRelHasRun(Long dataId, Long tempProcessId, Boolean filterProcessId);
}

