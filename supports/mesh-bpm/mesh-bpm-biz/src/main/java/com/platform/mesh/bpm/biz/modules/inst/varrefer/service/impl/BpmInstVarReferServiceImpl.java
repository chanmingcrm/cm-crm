package com.platform.mesh.bpm.biz.modules.inst.varrefer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.domain.po.BpmInstVarRefer;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.mapper.BpmInstVarReferMapper;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.service.IBpmInstVarReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 变量值信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstVarReferServiceImpl extends ServiceImpl<BpmInstVarReferMapper, BpmInstVarRefer> implements IBpmInstVarReferService {


    @Autowired
    private BpmInstVarValueServiceManual bpmInstVarValueServiceManual;

    /**
     * 功能描述:
     * 〈获取封装方法〉
     * @return 正常返回:{@link BpmInstVarValueServiceManual}
     * @author 蝉鸣
     */
    public BpmInstVarValueServiceManual getServiceManual(){
        return this.bpmInstVarValueServiceManual;
    };

    @Override
    public List<BpmInstVarRefer> selectVarReferByInstProcessIdId(Long InstProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVarRefer::getInstProcessId,InstProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instLineId instLineId
     * @return 正常返回:{@link List<BpmInstVarRefer>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVarRefer> selectVarReferByInstLineId(Long instLineId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVarRefer::getInstLineId,instLineId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param instVariableId instVariableId
     * @return 正常返回:{@link List<BpmInstVarRefer>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVarRefer> selectVarReferByInstVariableId(Long instVariableId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVarRefer::getInstVariableId,instVariableId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param instVarRefer instVarRefer
     * @return 正常返回:{@link BpmInstVarRefer}
     * @author 蝉鸣
     */
    @Override
    public BpmInstVarRefer addVarReferInst(BpmInstVarRefer instVarRefer) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param instVarReferId instVarReferId
     * @author 蝉鸣
     */
    @Override
    public void deleteVarReferByInstVarReferId(Long instVarReferId) {
        //删除事件信息
        this.removeById(instVarReferId);
    }
}

