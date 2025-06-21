package com.platform.mesh.bpm.biz.modules.temp.line.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.line.domain.po.BpmTempLine;
import com.platform.mesh.bpm.biz.modules.temp.line.service.manual.BpmTempLineServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.line.mapper.BpmTempLineMapper;
import com.platform.mesh.bpm.biz.modules.temp.line.service.IBpmTempLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 流程线信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempLineServiceImpl extends ServiceImpl<BpmTempLineMapper, BpmTempLine> implements IBpmTempLineService {


    @Autowired
    private BpmTempLineServiceManual bpmTempLineServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmTempLineServiceManual}
     * @author 蝉鸣
     */
    @Override
    public BpmTempLineServiceManual getServiceManual() {
        return bpmTempLineServiceManual;
    }

    /**
     * 功能描述:
     * 〈获取模板下线信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempLine> selectLinesByTemplateId(Long tempProcessId) {
        //根据模板ID查询所有的动作
        return this.lambdaQuery().eq(BpmTempLine::getTempProcessId,tempProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取进入节点线信息〉
     * @param nodeId nodeId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempLine> selectInLineByNodeId(Long nodeId) {
        //根据节点ID查询所有的入线
        return this.lambdaQuery().list();
    }

    /**
     * 功能描述:
     * 〈获取出节点线信息〉
     * @param nodeId nodeId
     * @return 正常返回:{@link List<BpmTempLine>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempLine> selectOutLineByNodeId(Long nodeId) {
        //根据节点ID查询所有的出线
        return this.lambdaQuery().list();
    }
}

