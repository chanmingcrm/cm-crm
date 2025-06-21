package com.platform.mesh.bpm.biz.modules.hist.node.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.vo.BpmHistNodeVO;
import com.platform.mesh.bpm.biz.modules.hist.node.mapper.BpmHistNodeMapper;
import com.platform.mesh.bpm.biz.modules.hist.node.service.IBpmHistNodeService;
import com.platform.mesh.bpm.biz.modules.hist.node.service.manual.BpmHistNodeServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.node.service.manual.BpmInstNodeServiceManual;
import com.platform.mesh.bpm.biz.modules.hist.node.domain.po.BpmHistNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Service()
public class BpmHistNodeServiceImpl extends ServiceImpl<BpmHistNodeMapper, BpmHistNode> implements IBpmHistNodeService {


    @Autowired
    private BpmHistNodeServiceManual bpmHistNodeServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstNodeServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmHistNodeServiceManual getServiceManual() {
        return bpmHistNodeServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmHistNodeVO>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmHistNodeVO> selectHistNodeByInstProcessId(Long instProcessId) {
        return this.baseMapper.selectHistNodeByInstProcessId(instProcessId);
    }

}

