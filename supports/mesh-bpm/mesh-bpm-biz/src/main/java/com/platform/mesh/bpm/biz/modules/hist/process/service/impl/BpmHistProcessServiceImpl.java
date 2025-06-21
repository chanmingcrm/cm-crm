package com.platform.mesh.bpm.biz.modules.hist.process.service.impl;

import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.hist.process.service.IBpmHistProcessService;
import com.platform.mesh.bpm.biz.modules.hist.process.service.manual.BpmHistProcessServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Service
public class BpmHistProcessServiceImpl implements IBpmHistProcessService {


    @Autowired
    private BpmHistProcessServiceManual bpmHistProcessServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmHistProcessServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmHistProcessServiceManual getServiceManual() {
        return bpmHistProcessServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取流程实例历史信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmHistProcessInfoVO}
     * @author 蝉鸣
     */
    @Override
    public BpmHistProcessInfoVO getProcessHistInfo(Long instProcessId) {
        BpmHistProcessInfoVO histProcessInfoVO = new BpmHistProcessInfoVO();
        histProcessInfoVO.setInstProcessId(instProcessId);
        return bpmHistProcessServiceManual.getProcessHistInfo(histProcessInfoVO);
    }


}

