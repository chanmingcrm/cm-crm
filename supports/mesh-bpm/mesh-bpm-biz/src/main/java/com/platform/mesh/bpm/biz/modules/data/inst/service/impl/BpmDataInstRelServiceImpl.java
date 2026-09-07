package com.platform.mesh.bpm.biz.modules.data.inst.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmPDTO;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.po.BpmDataInstRel;
import com.platform.mesh.bpm.biz.modules.data.inst.domain.vo.BpmDataInstRelVO;
import com.platform.mesh.bpm.biz.modules.data.inst.mapper.BpmDataInstRelMapper;
import com.platform.mesh.bpm.biz.modules.data.inst.service.IBpmDataInstRelService;
import com.platform.mesh.bpm.biz.modules.data.inst.service.manual.BpmDataInstRelServiceManual;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<BpmDataInstRelVO> getDataInstRelByDataId(Long dataId) {
        return this.getBaseMapper().getDataInstRelByDataId(dataId);
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

    /**
     * 功能描述:
     * 〈根据模块获取运行中待审批数据〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<Long>}
     * @author 蝉鸣
     */
    @Override
    public MPage<Long> getRunDataIdsByModuleSchema(BpmPDTO pageDTO) {
        MPage<Long> mPage = MPageUtil.pageEntityToMPage(pageDTO, Long.class);
        return this.baseMapper.getRunDataIdsByModuleSchema(mPage,pageDTO);
    }

    /**
     * 功能描述:
     * 〈获取模块待审批数量总计〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    public Map<String, Long> getRunDataNumByModuleSchema(BpmPDTO pageDTO) {
        List<SimpVO> numList = this.baseMapper.getRunDataNumByModuleSchema(pageDTO);
        if(CollUtil.isEmpty(numList)){
            return new HashMap<>();
        }
        return numList.stream().collect(Collectors.toMap(SimpVO::getName, value->Long.parseLong(value.getValue().toString())));
    }

    /**
     * 功能描述:
     * 〈删除流程数据关联〉
     * @param moduleId moduleId
     * @param dataIs dataIs
     * @param tenantId tenantId
     * @author 蝉鸣
     */
    @Override
    public void delBpmDataRel(Long moduleId, List<Long> dataIs) {
        if(CollUtil.isEmpty(dataIs)){
            return;
        }
        this.getBaseMapper().delBpmDataRel(moduleId, dataIs);
    }

}

