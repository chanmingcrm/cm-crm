package com.platform.mesh.bpm.biz.modules.inst.varvalue.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.domain.po.BpmInstVarValue;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.mapper.BpmInstVarValueMapper;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.IBpmInstVarValueService;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.manual.BpmInstVarValueServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 变量值信息
 * @author 蝉鸣
 */
@Service()
public class BpmInstVarValueServiceImpl extends ServiceImpl<BpmInstVarValueMapper, BpmInstVarValue> implements IBpmInstVarValueService {


    @Autowired
    private BpmInstVarValueServiceManual bpmInstVarValueServiceManual;

    /**
     * 功能描述:
     * 〈获取实例下变量值信息〉
     * @param InstProcessId InstProcessId
     * @return 正常返回:{@link List<BpmInstVarValue>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVarValue> selectVarValuesByInstProcessIdId(Long InstProcessId) {
        //根据流程实例ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVarValue::getInstProcessId,InstProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下变量值信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link List<BpmInstVarValue>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmInstVarValue> selectVarValueByInstNodeId(Long instNodeId) {
        //根据实例节点ID查询所有的事件
        return this.lambdaQuery().eq(BpmInstVarValue::getInstNodeId,instNodeId).list();
    }

    /**
     * 功能描述:
     * 〈添加变量值信息〉
     * @param instVarValue instVarValue
     * @return 正常返回:{@link BpmInstVarValue}
     * @author 蝉鸣
     */
    @Override
    public BpmInstVarValue addVarValueInst(BpmInstVarValue instVarValue) {
        //添加事件信息
        return this.lambdaQuery().one();
    }
}

