package com.platform.mesh.bpm.biz.modules.hist.process.service.manual;


import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.soa.process.type.ProcessTypeService;
import com.platform.mesh.bpm.biz.soa.process.type.enums.ProcessTypeEnum;
import com.platform.mesh.bpm.biz.soa.process.type.factory.ProcessTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 处理功能细化
 * @author 蝉鸣
 */
@Service()
public class BpmHistProcessServiceManual {

    private final static Logger log = LoggerFactory.getLogger(BpmHistProcessServiceManual.class);

    @Autowired
    private ProcessTypeFactory processTypeFactory;

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param bpmHistProcessInfoVO bpmHistProcessInfoVO
     * @return 正常返回:{@link BpmInstProcessDesignVO}
     * @author 蝉鸣
     */
    public BpmHistProcessInfoVO getProcessHistInfo(BpmHistProcessInfoVO bpmHistProcessInfoVO) {
        //构造流程模板
        for (ProcessTypeEnum processTypeEnum : ProcessTypeEnum.values()) {
            //获取工厂服务
            ProcessTypeService processTypeService = processTypeFactory.getProcessTypeService(processTypeEnum);
            if(ObjectUtil.isEmpty(processTypeService)){
                continue;
            }
            //获取实例节点
            processTypeService.getHist(bpmHistProcessInfoVO);
        }
        //返回流程实例历史VO
        return bpmHistProcessInfoVO;
    }
}

