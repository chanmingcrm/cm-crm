package com.platform.mesh.bpm.biz.modules.temp.varrefer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.domain.po.BpmTempVarRefer;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.mapper.BpmTempVarReferMapper;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;
import com.platform.mesh.bpm.biz.modules.temp.varrefer.service.IBpmTempVarReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 变量值信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempVarReferServiceImpl extends ServiceImpl<BpmTempVarReferMapper, BpmTempVarRefer> implements IBpmTempVarReferService {


    @Autowired
    private BpmInstVarValueServiceManual bpmInstVarValueServiceManual;

    /**
     * 获取封装方法
     */
    public BpmInstVarValueServiceManual getServiceManual(){
        return this.bpmInstVarValueServiceManual;
    };

    /**
     * 功能描述:
     * 〈获取实例下事件信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempVarRefer> selectVarReferByTempProcessIdId(Long tempProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempVarRefer::getTempProcessId,tempProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempLineId tempLineId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempVarRefer> selectVarReferByTempLineId(Long tempLineId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempVarRefer::getTempLineId,tempLineId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下事件信息〉
     * @param tempVariableId tempVariableId
     * @return 正常返回:{@link List<BpmTempVarRefer>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempVarRefer> selectVarReferByTempVariableId(Long tempVariableId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmTempVarRefer::getTempVariableId,tempVariableId).list();
    }

    /**
     * 功能描述:
     * 〈添加事件信息〉
     * @param tempVarRefer tempVarRefer
     * @return 正常返回:{@link BpmTempVarRefer}
     * @author 蝉鸣
     */
    @Override
    public BpmTempVarRefer addVarReferInst(BpmTempVarRefer tempVarRefer) {
        //添加事件信息
        return this.lambdaQuery().one();
    }

    /**
     * 功能描述:
     * 〈删除事件信息〉
     * @param tempVarReferId tempVarReferId
     * @author 蝉鸣
     */
    @Override
    public void deleteVarReferByInstVarReferId(Long tempVarReferId) {
        //删除事件信息
        this.removeById(tempVarReferId);
    }
}

