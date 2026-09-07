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

}

