package com.platform.mesh.bpm.biz.modules.hist.process.service;


import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.hist.process.service.manual.BpmHistProcessServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程过程信息
 * @author 蝉鸣
 */
public interface IBpmHistProcessService {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmHistProcessServiceManual}
     * @author 蝉鸣
     */
    BpmHistProcessServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取流程实例历史信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmHistProcessInfoVO}
     * @author 蝉鸣
     */
    BpmHistProcessInfoVO getProcessHistInfo(Long instProcessId);
}

