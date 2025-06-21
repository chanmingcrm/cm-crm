package com.platform.mesh.bpm.biz.modules.temp.line.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.po.BpmTempLine;
import com.platform.mesh.bpm.biz.modules.temp.line.service.manual.BpmTempLineServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程线信息
 * @author 蝉鸣
 */
public interface IBpmTempLineService extends IService<BpmTempLine> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempLineServiceManual}
     * @author 蝉鸣
     */
    BpmTempLineServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取模板下线信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    List<BpmTempLine> selectLinesByTemplateId(Long tempProcessId);

    /**
     * 功能描述:
     * 〈获取进入节点线信息〉
     * @param instId instId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    List<BpmTempLine> selectInLineByNodeId(Long instId);

    /**
     * 功能描述:
     * 〈获取出节点线信息〉
     * @param instId instId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    List<BpmTempLine> selectOutLineByNodeId(Long instId);
}

