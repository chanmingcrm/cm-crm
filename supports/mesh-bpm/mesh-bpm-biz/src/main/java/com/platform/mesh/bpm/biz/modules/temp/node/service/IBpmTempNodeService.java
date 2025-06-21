package com.platform.mesh.bpm.biz.modules.temp.node.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.node.service.manual.BpmTempNodeServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点信息
 * @author 蝉鸣
 */
public interface IBpmTempNodeService extends IService<BpmTempNode> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempNodeServiceManual}
     * @author 蝉鸣
     */
    BpmTempNodeServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取模板下节点信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempNode>}
     * @author 蝉鸣
     */
    List<BpmTempNode> selectNodesByTemplateId(Long tempProcessId);

}

