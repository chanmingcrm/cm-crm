package com.platform.mesh.bpm.biz.modules.temp.process.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessDesignDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessAddDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessEditDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.dto.BpmTempProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.po.BpmTempProcess;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.temp.process.domain.vo.BpmTempProcessVO;
import com.platform.mesh.bpm.biz.modules.temp.process.service.manual.BpmTempProcessServiceManual;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.utils.result.Result;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程过程信息
 * @author 蝉鸣
 */
public interface IBpmTempProcessService extends IService<BpmTempProcess> {

    /**
     * 获取封装方法
     */
    BpmTempProcessServiceManual getServiceManual();


    /**
     * 功能描述:
     * 〈新建流程模板〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<BpmTempProcessVO>}
     * @author 蝉鸣
     */
    PageVO<BpmTempProcessVO> selectPage(BpmTempProcessPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈添加流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    BpmTempProcess addProcessTemp(BpmTempProcessAddDTO bpmTempProcessAddDTO);

    /**
     * 功能描述:
     * 〈编辑流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    BpmTempProcess editProcessTemp(BpmTempProcessEditDTO bpmTempProcessEditDTO);

    /**
     * 功能描述:
     * 〈获取流程模板〉
     * @return 正常返回:{@link Result<BpmTempProcess>}
     * @author 蝉鸣
     */
    BpmTempProcessDesignVO getProcessTemp(Long tempProcessId);

    /**
     * 功能描述:
     * 〈新建流程模板〉
     * @return 正常返回:{@link Result <FlowTempProcess>}
     * @author 蝉鸣
     */
    BpmTempProcess designProcessTemp(BpmTempProcessDesignDTO bpmTempProcessDesignDTO);

    /**
     * 功能描述:
     * 〈发布流程模板〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean pubProcessTemp(Long tempProcessId);

    /**
     * 功能描述:
     * 〈作废流程模板〉
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean cancelProcessTemp(Long tempProcessId);

    /**
     * 功能描述:
     * 〈删除流程模板〉
     * @return 正常返回:{@link Result<Object>}
     * @author 蝉鸣
     */
    Boolean delProcessTemp(Long tempProcessId);

}

