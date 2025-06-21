package com.platform.mesh.bpm.biz.data.inst.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.biz.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.data.inst.mapper.BpmDataInstRelMapper;
import com.platform.mesh.bpm.biz.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.bpm.biz.data.inst.service.manual.BpmDataInstRelServiceManual;
import com.platform.mesh.bpm.biz.soa.process.run.enums.ProcessRunEnum;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 数据流程实例信息
 * @author 蝉鸣
 */
@Service()
public class BpmDataInstRelServiceImpl extends ServiceImpl<BpmDataInstRelMapper, BpmDataInstRel> implements IBpmDataInstRelService {


    @Autowired
    private BpmDataInstRelServiceManual bpmDataInstRelServiceManual;

    /**
     * 获取封装方法
     */
    public BpmDataInstRelServiceManual getServiceManual(){
        return bpmDataInstRelServiceManual;
    }

    /**
     * 功能描述:
     * 〈根据表单Id获取流程与业务表单绑定关系〉
     * @return 正常返回:{@link Result <List<BpmDataInstRel>>}
     * @author 蝉鸣
     */
    @Override
    public List<BpmDataInstRel> getDataInstRelByDataId(Long dataId) {
        return this.lambdaQuery().eq(BpmDataInstRel::getDataId, dataId).list();
    }

    /**
     * 功能描述:
     * 〈校验数据是否已经有运行中的流程审批〉
     * @param dataId dataId
     * @param tempProcessId tempProcessId
     * @param filterProcessId filterProcessId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    @Override
    public Boolean checkDataRelHasRun(Long dataId, Long tempProcessId, Boolean filterProcessId) {
        List<BpmDataInstRel> runInstList = this.baseMapper.checkDataRelHasRun(dataId,tempProcessId,ProcessRunEnum.RUNNING.getValue(),filterProcessId);
        if(CollUtil.isEmpty(runInstList)){
            return Boolean.FALSE;
        }else{
            return Boolean.TRUE;
        }
    }

}

