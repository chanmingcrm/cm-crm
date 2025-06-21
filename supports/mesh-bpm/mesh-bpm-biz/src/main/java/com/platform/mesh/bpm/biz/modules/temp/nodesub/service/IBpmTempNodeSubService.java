package com.platform.mesh.bpm.biz.modules.temp.nodesub.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.service.manual.BpmTempNodeSubServiceManual;

import java.util.List;

/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 流程节点子项信息
 * @author 蝉鸣
 */
public interface IBpmTempNodeSubService extends IService<BpmTempNodeSub> {

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempNodeSubServiceManual}
     * @author 蝉鸣
     */
    BpmTempNodeSubServiceManual getServiceManual();

    /**
     * 功能描述:
     * 〈获取模板下节点信息〉
     * @param templateId templateId
     * @return 正常返回:{@link List<BpmTempNodeSub>}
     * @author 蝉鸣
     */
    List<BpmTempNodeSub> selectNodeSubsByTemplateId(Long templateId);

}

