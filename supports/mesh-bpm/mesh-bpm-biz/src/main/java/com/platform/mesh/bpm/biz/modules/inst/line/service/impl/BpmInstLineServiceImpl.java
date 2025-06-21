package com.platform.mesh.bpm.biz.modules.inst.line.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.line.mapper.BpmInstLineMapper;
import com.platform.mesh.bpm.biz.modules.inst.line.service.IBpmInstLineService;
import com.platform.mesh.bpm.biz.modules.inst.line.service.manual.BpmInstLineServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程线信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstLineServiceImpl extends ServiceImpl<BpmInstLineMapper, BpmInstLine> implements IBpmInstLineService {


    @Autowired
    private BpmInstLineServiceManual bpmInstLineServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstLineServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmInstLineServiceManual getServiceManual() {
        return bpmInstLineServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取实例下线信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstLine> selectLinesByInstProcessId(Long instProcessId) {
        //根据流程实例查询线信息
        return this.lambdaQuery().eq(BpmInstLine::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取实例下线信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstLine> selectInLineByInstNodeId(Long instNodeId) {
        //查询节点所有的入线信息
        return this.lambdaQuery().eq(BpmInstLine::getInstInNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈获取出节点线信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstLine> selectOutLineByInstNodeId(Long instNodeId) {
        //查询节点所有的出线信息
        return this.lambdaQuery().eq(BpmInstLine::getInstOutNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈校验当前线是否通过〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstLine>}
     * @author 蝉鸣
     */
    @Override
    public Boolean checkLinePass(Long instLineId) {
        //查询当前的信息
        BpmInstLine instLine = this.getById(instLineId);
        //校验当前线是否通过
        return bpmInstLineServiceManual.checkLinePass(instLine);
    }

}

