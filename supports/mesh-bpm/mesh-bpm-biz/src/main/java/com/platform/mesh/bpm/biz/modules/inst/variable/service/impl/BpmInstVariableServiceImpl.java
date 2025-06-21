package com.platform.mesh.bpm.biz.modules.inst.variable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.variable.domain.po.BpmInstVariable;
import com.platform.mesh.bpm.biz.modules.inst.line.domain.po.BpmInstLine;
import com.platform.mesh.bpm.biz.modules.inst.variable.mapper.BpmInstVariableMapper;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.IBpmInstVariableService;
import com.platform.mesh.bpm.biz.modules.inst.variable.service.manual.BpmInstVariableServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 变量信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstVariableServiceImpl extends ServiceImpl<BpmInstVariableMapper, BpmInstVariable> implements IBpmInstVariableService {


    @Autowired
    private BpmInstVariableServiceManual bpmInstVariableServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVariable> selectVariablesByInstProcessIdId(Long instProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVariable::getInstProcessId,instProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVariable> selectVariableByInstLineId(Long instLineId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVariable::getInstLineId,instLineId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineIds instLineIds
     * @return 正常返回:{@link List<BpmInstVariable>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVariable> selectVariableByInstLineIds(List<Long> instLineIds) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().in(BpmInstVariable::getInstLineId,instLineIds).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param instVariable instVariable
     * @return 正常返回:{@link BpmInstVariable}
     * @author 蝉鸣
     */
    @Override
    public BpmInstVariable addVariableInst(BpmInstVariable instVariable) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instVariableId instVariableId
     * @author 蝉鸣
     */
    @Override
    public void deleteVariableByInstVariableId(Long instVariableId) {
        //删除事件信息
        this.removeById(instVariableId);
    }

    /**
     * 功能描述:
     * 〈校验变量参数〉
     * @param instLine instLine
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean checkVariableByLine(BpmInstLine instLine){
        return bpmInstVariableServiceManual.checkVariableByLine(instLine);
    }
}

