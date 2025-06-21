package com.platform.mesh.bpm.biz.modules.temp.variable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.variable.service.manual.BpmTempVariableServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.variable.domain.po.BpmTempVariable;
import com.platform.mesh.bpm.biz.modules.temp.variable.mapper.BpmTempVariableMapper;
import com.platform.mesh.bpm.biz.modules.temp.variable.service.IBpmTempVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description
 * @author 蝉鸣
 */
@Service()
public class BpmTempVariableServiceImpl extends ServiceImpl<BpmTempVariableMapper, BpmTempVariable> implements IBpmTempVariableService {


    @Autowired
    private BpmTempVariableServiceManual bpmTempVariableServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempVariable>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempVariable> selectVariablesByTempProcessIdId(Long tempProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempVariable::getTempProcessId,tempProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempLineId tempLineId
     * @return 正常返回:{@link List<BpmTempVariable>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempVariable> selectVariableByTempLineId(Long tempLineId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempVariable::getTempLineId,tempLineId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param bpmTempVariable bpmTempVariable
     * @return 正常返回:{@link BpmTempVariable}
     * @author 蝉鸣
     */
    @Override
    public BpmTempVariable addVariableTemp(BpmTempVariable bpmTempVariable) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempVariableId tempVariableId
     * @author 蝉鸣
     */
    @Override
    public void deleteVariableByTempVariableId(Long tempVariableId) {
        //删除事件信息
        this.removeById(tempVariableId);
    }
}

