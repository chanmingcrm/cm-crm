package com.platform.mesh.bpm.biz.modules.temp.nodesub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.service.manual.BpmTempNodeSubServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.mapper.BpmTempNodeSubMapper;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.service.IBpmTempNodeSubService;
import com.platform.mesh.bpm.biz.modules.temp.nodesub.domain.po.BpmTempNodeSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点子项信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempNodeSubServiceImpl extends ServiceImpl<BpmTempNodeSubMapper, BpmTempNodeSub> implements IBpmTempNodeSubService {


    @Autowired
    private BpmTempNodeSubServiceManual bpmTempNodeServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempNodeSubServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmTempNodeSubServiceManual getServiceManual() {
        return bpmTempNodeServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取模板下节点信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempNodeSub>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempNodeSub> selectNodeSubsByTemplateId(Long tempProcessId) {
        //根据模板ID查询所有的节点信息
        return this.lambdaQuery().eq(BpmTempNodeSub::getTempProcessId,tempProcessId).list();
    }
}

