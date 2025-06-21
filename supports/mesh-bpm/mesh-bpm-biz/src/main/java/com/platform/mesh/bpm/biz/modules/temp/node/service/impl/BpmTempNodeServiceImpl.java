package com.platform.mesh.bpm.biz.modules.temp.node.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.node.mapper.BpmTempNodeMapper;
import com.platform.mesh.bpm.biz.modules.temp.node.service.IBpmTempNodeService;
import com.platform.mesh.bpm.biz.modules.temp.node.service.manual.BpmTempNodeServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempNodeServiceImpl extends ServiceImpl<BpmTempNodeMapper, BpmTempNode> implements IBpmTempNodeService {


    @Autowired
    private BpmTempNodeServiceManual bpmTempNodeServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempNodeServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmTempNodeServiceManual getServiceManual() {
        return bpmTempNodeServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取模板下节点信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempNode>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempNode> selectNodesByTemplateId(Long tempProcessId) {
        //根据模板ID查询所有的节点信息
        return this.lambdaQuery().eq(BpmTempNode::getTempProcessId,tempProcessId).list();
    }
}

