package com.platform.mesh.crm.biz.bi.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.app.api.modules.bi.domain.bo.ModuleBiTimeBO;
import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.app.api.modules.bi.domain.dto.ModuleBiDTO;
import com.platform.mesh.app.api.modules.bi.domain.vo.BiIdSimpVO;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.crm.biz.bi.crm.mapper.CrmBiMapper;
import com.platform.mesh.crm.biz.bi.crm.service.ICrmBiService;
import com.platform.mesh.crm.biz.bi.crm.service.manual.CrmBiServiceManual;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.crm.biz.modules.crm.precustomer.domain.po.CrmPreCustomer;
import com.platform.mesh.mybatis.plus.handler.FormatTableNameHandler;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.org.member.domain.bo.OrgMemberRelBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description 客户关系BI
 * @author 蝉鸣
 */
@Service
public class CrmBiServiceImpl implements ICrmBiService {


    @Autowired
    private CrmBiMapper crmBiMapper;

    @Autowired
    private CrmBiServiceManual crmBiServiceManual;

    /**
     * 功能描述:
     * 〈获取新增客户统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biCustomerNew(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biCustomerNew(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取新增联系人统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biContactsNew(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        return crmBiMapper.biContactsNew(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取新增商机统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biBusinessNew(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        return crmBiMapper.biBusinessNew(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取新增合同统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biContractNew(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biContractNew(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取新增跟进统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biFollowNew(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biFollowNew(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取合同金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biContractMoney(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biContractMoney(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取商机金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biBusinessMoney(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biBusinessMoney(biDTO);
    }

    /**
     * 功能描述:
     * 〈获取回款金额统计〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    public SimpVO biPaymentMoney(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //获取本期数据
        return crmBiMapper.biPaymentMoney(biDTO);
    }

    /**
     * 功能描述:
     * 〈合同目标及完成情况〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<SimpVO> biContractEstAndAct(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //重置数据权限
        Long accountId = UserCacheUtil.getAccountId();
        List<OrgMemberRelBO> accountChildCache = UserCacheUtil.getAccountChildCache(accountId);
        //获取关联数据ID
//        List<Long> dataIds = DataScopeUtil.dataScopeDataIdList(biDTO.getDataScope(), biDTO.getDataFlag(), biDTO.getDataIds());
//        biDTO.setDataIds(dataIds);
        //获取合同
        List<ModuleBiTimeBO> contract = crmBiMapper.biContractWithTime(biDTO);
        if(CollUtil.isEmpty(contract)) {
            return CollUtil.newArrayList();
        }
        List<Long> moduleIds = contract.stream().map(ModuleBiTimeBO::getModuleId).toList();
        //获取目标
        List<CrmAllGoal> goalList = crmBiServiceManual.getGoalByTime(biDTO,moduleIds);
        //获取时间分隔
        return crmBiServiceManual.splitDataByTime(biDTO,goalList,contract);
    }

    /**
     * 功能描述:
     * 〈业绩指标完成率〉
     * @param biDTO biDTO
     * @return 正常返回:{@link SimpVO}
     * @author 蝉鸣
     */
    @Override
    public SimpVO biAchieveEstAndAct(ModuleBiDTO biDTO) {
        //解析数据权限
        BiDTO parseBiDTO = AppUtil.parseBiDTO(biDTO);
        BeanUtil.copyProperties(parseBiDTO, biDTO);
        SimpVO simpVO = new SimpVO();
        simpVO.setValue(BigDecimal.ZERO);
        simpVO.setExtendValue(BigDecimal.ZERO);
        //获取模块信息
        AppModuleBaseBO moduleBase = crmBiServiceManual.getModuleBase(biDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleBase)) {
            return simpVO;
        }
        BigDecimal estValue;
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(moduleBase.getModuleSchema());
        try {
            estValue = crmBiMapper.countAchieveData(biDTO);
        } finally {
            //取消动态表名
            FormatTableNameHandler.removeTableName();
            FormatTableNameHandler.unEnableTableName();
        }

        simpVO.setValue(estValue);
        //获取目标
        List<CrmAllGoal> goalList = crmBiServiceManual.getGoalByTime(biDTO, CollUtil.newArrayList(biDTO.getModuleId()));
        if(CollUtil.isEmpty(goalList)) {
            return simpVO;
        }
        BigDecimal totalGoal = crmBiServiceManual.getTotalGoal(goalList, biDTO.getStartTime().toLocalDate(), biDTO.getEndTime().toLocalDate());
        simpVO.setExtendValue(totalGoal);
        return simpVO;
    }

    /**
     * 功能描述:
     * 〈排行榜〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result <SimpVO>}
     * @author 蝉鸣
     */
    @Override
    public List<BiIdSimpVO> biRankEstAndAct(ModuleBiDTO biDTO) {
        //解析数据权限
        BiDTO parseBiDTO = AppUtil.parseBiDTO(biDTO);
        BeanUtil.copyProperties(parseBiDTO, biDTO);
        //获取模块信息
        AppModuleBaseBO moduleBase = crmBiServiceManual.getModuleBase(biDTO.getModuleId());
        if(ObjectUtil.isEmpty(moduleBase)) {
            return CollUtil.newArrayList();
        }
        //开启动态表名
        FormatTableNameHandler.enableTableName(Boolean.TRUE);
        FormatTableNameHandler.setTableName(moduleBase.getModuleSchema());
        List<BiIdSimpVO> estValues = crmBiMapper.biRankEstAndAct(biDTO);
        //取消动态表名
        FormatTableNameHandler.removeTableName();
        FormatTableNameHandler.unEnableTableName();
        //获取目标
        Map<Long, BigDecimal> goalMap;
        List<CrmAllGoal> goalList = crmBiServiceManual.getGoalByTime(biDTO, CollUtil.newArrayList(biDTO.getModuleId()));
        if(CollUtil.isEmpty(goalList)) {
             goalMap = goalList.stream().collect(Collectors.groupingBy(CrmAllGoal::getDataId, Collectors.collectingAndThen(Collectors.toList(),
                    item -> crmBiServiceManual.getTotalGoal(item, biDTO.getStartTime().toLocalDate(), biDTO.getEndTime().toLocalDate()))));
        }else{
             goalMap = new HashMap<>();
        }
        //填充数据
        for (BiIdSimpVO estValue : estValues) {
            SysUserBO sysUserBO = UserCacheUtil.getSysUserInfoCache(estValue.getId());
            estValue.setName(sysUserBO.getNickName());
            if(ObjectUtil.isNotEmpty(goalMap) && goalMap.containsKey(estValue.getId())) {
                BigDecimal goal = goalMap.get(estValue.getId());
                estValue.setExtendValue(goal);
            }
        }
        return estValues;
    }

    /**
     * 功能描述:
     * 〈遗忘提醒〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    @Override
    public Map<String, Integer> biNotice(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        return crmBiMapper.biFollowNotice(biDTO);
    }

    /**
     * 功能描述:
     * 〈数据汇总〉
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<Map>}
     * @author 蝉鸣
     */
    @Override
    public Map<String, Object> biDataCount(BiDTO biDTO) {
        //解析数据权限
        biDTO = AppUtil.parseBiDTO(biDTO);
        //查询客户下公海模块Id
        List<Long> openModuleIds = crmBiServiceManual.getOpenModuleIds(CrmPreCustomer.class);
        return crmBiMapper.biDataCount(biDTO,openModuleIds);
    }
}