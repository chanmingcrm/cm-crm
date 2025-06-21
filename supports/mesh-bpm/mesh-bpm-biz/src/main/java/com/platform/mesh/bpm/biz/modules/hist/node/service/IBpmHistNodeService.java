package com.platform.mesh.bpm.biz.modules.hist.node.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.po.BpmHistNode;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import com.platform.mesh.bpm.biz.modules.hist.node.service.manual.BpmHistNodeServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.node.service.manual.BpmInstNodeServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点信息
 * @author 蝉鸣
 */
public interface IBpmHistNodeService extends IService<BpmHistNode> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeServiceManual}
     * @author 蝉鸣
     */
    BpmHistNodeServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmHistNodeVO>}
     * @author 蝉鸣
     */
    List<BpmHistNodeVO> selectHistNodeByInstProcessId(Long instProcessId);
}

