package com.platform.mesh.crm.biz.modules.bi.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.YearQuarter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.crm.biz.modules.bi.domain.bo.BiTimeBO;
import com.platform.mesh.crm.biz.modules.bi.domain.bo.ModuleBiTimeBO;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.dto.ModuleBiDTO;
import com.platform.mesh.crm.biz.modules.bi.domain.vo.BiSimpVO;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.crm.biz.modules.crm.allgoal.service.ICrmAllGoalService;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.format.TimeUnitEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系统计
 * @author 蝉鸣
 */
@Service
public class CrmBiServiceManual {

    @Autowired
    private ICrmAllGoalService crmAllGoalService;

    @Autowired
    private RemoteAppService remoteAppService;

    /**
     * 功能描述:
     * 〈解析BI搜索参数〉
     * @param biDTO biDTO
     * @author 蝉鸣
     */
    public BiDTO parseBiDTO(BiDTO biDTO) {
        //验证当前批次
        if(ObjectUtil.isEmpty(biDTO.getBatchId())) {
            biDTO.setBatchId(IdUtil.getSnowflakeNextId());
        }else{
            //查询缓存数据
            Boolean existsCache = UserCacheUtil.getExistsCache(CacheConstants.BI_SEARCH_PARAM, biDTO.getBatchId());
            if(existsCache){
                return UserCacheUtil.getUserPrefixCache(biDTO.getBatchId(), CacheConstants.BI_SEARCH_PARAM, BiDTO.class);
            }
        }
        //设置默认时间维度
        if(ObjectUtil.isEmpty(biDTO.getTimeUnit())) {
            biDTO.setTimeUnit(TimeUnitEnum.DAY.getValue());
        }
        //设置默认时间
        if(ObjectUtil.isEmpty(biDTO.getStartTime())) {
            biDTO.setStartTime(LocalDateTime.now().minusDays(NumberConst.NUM_7));
        }
        //设置默认时间
        if(ObjectUtil.isEmpty(biDTO.getEndTime())) {
            biDTO.setEndTime(LocalDateTime.now());
        }
        //设置过滤数据权限
        ScopeBO scopeBO = DataScopeUtil.parseBiDTO(biDTO.getDataScope(), biDTO.getDataFlag(), biDTO.getDataIds());
        biDTO.setDataScope(scopeBO.getDataScope());
        biDTO.setDataFlag(scopeBO.getDataFlag());
        biDTO.setDataIds(scopeBO.getDataIds());
        //设置缓存
        UserCacheUtil.setUserPrefixCache(biDTO.getBatchId(), CacheConstants.BI_SEARCH_PARAM, biDTO);
        return biDTO;
    }

    /**
     * 功能描述:
     * 〈获取模块信息〉
     * @param moduleId moduleId
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public AppModuleBaseBO getModuleBase(Long moduleId) {
        return remoteAppService.getModuleBaseInfoById(moduleId).getData();
    }

    /**
     * 功能描述:
     * 〈获取上期时间段〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public BiDTO getLastPeriod(BiDTO biDTO) {
        LocalDateTime startTime = biDTO.getStartTime();
        // 计算时间差
        Duration duration = Duration.between(biDTO.getStartTime(), biDTO.getEndTime());
        LocalDateTime newStartTime = startTime.minus(duration);
        biDTO.setStartTime(newStartTime);
        biDTO.setEndTime(startTime);
        return biDTO;
    }

    /**
     * 功能描述:
     * 〈获取目标〉
     * @param biDTO biDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public List<CrmAllGoal> getGoalByTime(BiDTO biDTO,List<Long> moduleIds) {
        List<Year> yearPeriod = DateTimeUtil.getYearPeriod(biDTO.getStartTime().toLocalDate(), biDTO.getEndTime().toLocalDate());
        return crmAllGoalService
                .lambdaQuery()
                .in(CrmAllGoal::getModuleId, moduleIds)
                .in(CrmAllGoal::getYearTime, yearPeriod)
                .eq(CrmAllGoal::getDataFlag, biDTO.getDataFlag())
                .in(CrmAllGoal::getDataId, biDTO.getDataIds()).list();
    }

    /**
     * 功能描述:
     * 〈获取时间分隔〉
     * @param biDTO biDTO
     * @return 正常返回:{@link List<BiTimeBO>}
     * @author 蝉鸣
     */
    public List<BiSimpVO> splitDataByTime(BiDTO biDTO,List<CrmAllGoal> goalList, List<ModuleBiTimeBO> timeBOS) {
        List<BiSimpVO> biSimpVOS = splitDataByTime(biDTO.getTimeUnit(), biDTO.getStartTime(), biDTO.getEndTime());
        if(CollUtil.isEmpty(timeBOS)) {
            return biSimpVOS;
        }
        //目标分组
        Map<String, BigDecimal> goalMap = crmAllGoalService.getServiceManual()
                .getGoalMap(biDTO.getTimeUnit(),biDTO.getStartTime().toLocalDate(),biDTO.getEndTime().toLocalDate(), goalList);
        //进行分组
        Map<String, BigDecimal> timeMap = timeBOS.stream().collect(Collectors.groupingBy(
                item -> getDataTimeStr(biDTO.getTimeUnit(), item.getDateTime()),
                Collectors.mapping(
                        BiTimeBO::getValue,
                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                )
        ));
        //组装数据
        for (BiSimpVO biSimpVO : biSimpVOS) {
            //实际数值
            if(timeMap.containsKey(biSimpVO.getName())) {
                BigDecimal bigDecimal = timeMap.get(biSimpVO.getName());
                biSimpVO.setValue(bigDecimal);
            }
            //目标数值
            if(goalMap.containsKey(biSimpVO.getName())) {
                BigDecimal bigDecimal = goalMap.get(biSimpVO.getName());
                biSimpVO.setExtend(bigDecimal);
            }
        }
        return biSimpVOS;
    }

    /**
     * 功能描述:
     * 〈获取时间分隔〉
     * @param timeUtil timeUtil
     * @return 正常返回:{@link List<BiTimeBO>}
     * @author 蝉鸣
     */
    public List<BiSimpVO> splitDataByTime(Integer timeUtil,LocalDateTime startTime, LocalDateTime endTime) {
        if(TimeUnitEnum.YEAR.getValue().equals(timeUtil)){
            return DateTimeUtil.getYearPeriod(startTime.toLocalDate(), endTime.toLocalDate()).stream().map(item->{
                BiSimpVO simpVO = new BiSimpVO();
                simpVO.setName(StrUtil.toString(item.getValue()));
                return simpVO;
            }).toList();
        } else if (TimeUnitEnum.QUARTER.getValue().equals(timeUtil)) {
            return DateTimeUtil.getQuarterPeriod(startTime.toLocalDate(), endTime.toLocalDate()).stream().map(item->{
                BiSimpVO simpVO = new BiSimpVO();
                simpVO.setName(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getQuarterValue())));
                return simpVO;
            }).toList();
        } else if (TimeUnitEnum.MONTH.getValue().equals(timeUtil)) {
            return DateTimeUtil.getMonthPeriod(startTime.toLocalDate(), endTime.toLocalDate()).stream().map(item->{
                BiSimpVO simpVO = new BiSimpVO();
                simpVO.setName(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getMonth())));
                return simpVO;
            }).toList();
        } else {
            return DateTimeUtil.getDayStrPeriod(startTime.toLocalDate(), endTime.toLocalDate()).stream().map(item->{
                BiSimpVO simpVO = new BiSimpVO();
                simpVO.setName(item);
                return simpVO;
            }).toList();
        }
    }

    /**
     * 功能描述:
     * 〈获取时间分隔〉
     * @param timeUtil timeUtil
     * @return 正常返回:{@link List<BiTimeBO>}
     * @author 蝉鸣
     */
    public String getDataTimeStr(Integer timeUtil,LocalDateTime localDateTime) {
        if(TimeUnitEnum.YEAR.getValue().equals(timeUtil)){
            return StrUtil.toString(localDateTime.getYear());
        } else if (TimeUnitEnum.QUARTER.getValue().equals(timeUtil)) {
            YearQuarter yearQuarter = YearQuarter.of(YearMonth.of(localDateTime.getYear(), localDateTime.getMonthValue()));
            return StrUtil.toString(localDateTime.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(yearQuarter.getQuarterValue()));
        } else if (TimeUnitEnum.MONTH.getValue().equals(timeUtil)) {
            return StrUtil.toString(localDateTime.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(localDateTime.getMonth()));
        } else {
            return DateTimeUtil.localDateToStr2(localDateTime.toLocalDate());
        }
    }

    /**
     * 功能描述:
     * 〈业绩指标完成率〉
     * @param goalList goalList
     * @param startDate startDate
     * @param endDate endDate
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public BigDecimal getTotalGoal(List<CrmAllGoal> goalList, LocalDate startDate, LocalDate endDate) {
        //汇总目标总值
        return crmAllGoalService.getServiceManual().getTotalGoal(goalList, startDate, endDate);
    }

    /**
     * 功能描述:
     * 〈业绩指标完成率〉
     * @param biDTO biQueryDTO
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public BiSimpVO countDataAchieve(ModuleBiDTO biDTO, List<CrmAllGoal> goalList, BigDecimal contract) {
        //设置默认值
        BiSimpVO biSimpVO = new BiSimpVO();
        biSimpVO.setValue(contract);
        //汇总目标总值
        BigDecimal totalGoal = crmAllGoalService.getServiceManual().getTotalGoal(goalList, biDTO.getStartTime().toLocalDate(), biDTO.getEndTime().toLocalDate());
        biSimpVO.setExtend(totalGoal);
        return biSimpVO;
    }

    /**
     * 功能描述:
     * 〈查询客户公海模块ID〉
     * @param tClass tClass
     * @return 正常返回:{@link BiSimpVO}
     * @author 蝉鸣
     */
    public <T extends AppPO> List<Long> getOpenModuleIds(Class<T> tClass) {
        String tableName = SqlUtil.getTableName(tClass, TableName.class);
        List<AppModuleBaseBO> moduleBaseBOS = remoteAppService.getModuleBaseInfoBySchema(CollUtil.newArrayList(tableName)).getData();
        if(CollUtil.isEmpty(moduleBaseBOS)) {
            //为空返回一个不存在得值，避免条件过滤失效
            return CollUtil.newArrayList(IdUtil.getSnowflakeNextId());
        }
        List<Long> openModuleIds = moduleBaseBOS.stream()
                .filter(module -> YesOrNoEnum.YES.getValue().equals(module.getOpenFlag()))
                .map(AppModuleBaseBO::getId).distinct().toList();
        if(CollUtil.isEmpty(openModuleIds)) {
            //为空返回一个不存在得值，避免条件过滤失效
            return CollUtil.newArrayList(IdUtil.getSnowflakeNextId());
        }
        return openModuleIds;
    }

}