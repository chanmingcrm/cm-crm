package com.platform.mesh.bpm.biz.modules.inst.process.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmProcessStartDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.po.BpmInstNodeSub;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.dto.BpmInstProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessOaVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessRunVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.process.service.manual.BpmInstProcessServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import com.platform.mesh.core.application.domain.vo.PageVO;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程过程信息
 * @author 蝉鸣
 */
public interface IBpmInstProcessService extends IService<BpmInstProcess> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstProcessServiceManual}
     * @author 蝉鸣
     */
    BpmInstProcessServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈初始化流程实例〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess initProcessInst(Long tempProcessId);

    /**
     * 功能描述:
     * 〈初始化子流程实例〉
     * @param bpmTempNodeSub bpmTempNodeSub
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstNodeSub initSubProcessInst(BpmTempNodeSub bpmTempNodeSub);

    /**
     * 功能描述:
     * 〈运行流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess runProcessInst(Long instProcessId);

    /**
     * 功能描述:
     * 〈启动流程实例〉
     * @param startDTO startDTO
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess startProcessInst(BpmProcessStartDTO startDTO);

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcessRunVO}
     * @author 蝉鸣
     */
    BpmInstProcessRunVO getProcessInstRunInfo(Long instProcessId);

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcessDesignVO getProcessInst(Long instProcessId);

    /**
     * 功能描述:
     * 〈停止流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess stopProcessInst(Long instProcessId);

    /**
     * 功能描述:
     * 〈作废流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess cancelProcessInst(Long instProcessId);

    /**
     * 功能描述:
     * 〈关闭流程实例〉
     * @param instNodeSubDTO instNodeSubDTO
     * @return 正常返回:{@link BpmInstProcess}
     * @author 蝉鸣
     */
    BpmInstProcess addSubProcessInst(BpmInstNodeSubDTO instNodeSubDTO);

    /**
     * 功能描述:
     * 〈获取流程实例-待办〉
     * @param pageDTO pageDTO
     * @param accountId accountId
     * @return 正常返回:{@link PageVO<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    PageVO<BpmInstProcessOaVO> getProcessInstTodo(BpmInstProcessPageDTO pageDTO, Long accountId);

    /**
     * 功能描述:
     * 〈获取流程实例-已办〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    PageVO<BpmInstProcessOaVO> getProcessInstDone(BpmInstProcessPageDTO pageDTO, Long accountId);

    /**
     * 功能描述:
     * 〈获取流程实例-归档〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    PageVO<BpmInstProcessOaVO> getProcessInstEnd(BpmInstProcessPageDTO pageDTO, Long accountId);

    /**
     * 功能描述:
     * 〈获取流程实例-跟进〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<BpmInstProcessVO>}
     * @author 蝉鸣
     */
    PageVO<BpmInstProcessOaVO> getProcessInstFollow(BpmInstProcessPageDTO pageDTO, Long accountId);

    /**
     * 功能描述:
     * 〈处理流程实例消息〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    void handleInstProcessMsg(Long instProcessId);

    /**
     * 功能描述:
     * 〈提交流程,可以进行审批〉
     * @param instProcessId instProcessId
     * @author 蝉鸣
     */
    Boolean commitInstProcess(Long instProcessId);

}

