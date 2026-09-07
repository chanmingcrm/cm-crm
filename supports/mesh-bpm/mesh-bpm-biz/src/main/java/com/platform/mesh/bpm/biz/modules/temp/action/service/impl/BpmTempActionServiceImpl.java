package com.platform.mesh.bpm.biz.modules.temp.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.modules.temp.action.domain.po.BpmTempAction;
import com.platform.mesh.bpm.biz.modules.temp.action.mapper.BpmTempActionMapper;
import com.platform.mesh.bpm.biz.modules.temp.action.service.IBpmTempActionService;
import com.platform.mesh.bpm.biz.modules.temp.action.service.manual.BpmTempActionServiceManual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 动作信息
 * @author 蝉鸣
 */
@Service()
public class BpmTempActionServiceImpl extends ServiceImpl<BpmTempActionMapper, BpmTempAction> implements IBpmTempActionService {


    @Autowired
    private BpmTempActionServiceManual bpmTempActionServiceManual;

    /**
     * 获取封装方法
     */
    public BpmTempActionServiceManual getServiceManual(){
        return bpmTempActionServiceManual;
    };

    /**
     * 功能描述:
     * 〈获取模板下动作信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link List<BpmTempAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempAction> selectActionsByTemplateId(Long tempProcessId) {
        //根据模板ID查询所有的动作
        return this.lambdaQuery().eq(BpmTempAction::getTempProcessId,tempProcessId).list();
    }

    /**
     * 功能描述:
     * 〈获取当前节点下动作信息〉
     * @param tempNodeId tempNodeId
     * @return 正常返回:{@link List<BpmTempAction>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmTempAction> selectActionsByNodeId(Long tempNodeId) {
        //根据节点ID查询所有的动作
        return this.lambdaQuery().eq(BpmTempAction::getTempNodeId,tempNodeId).list();
    }

}

