package com.platform.mesh.crm.biz.modules.crm.allgoal.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.date.YearQuarter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.crm.biz.modules.crm.allgoal.domain.po.CrmAllGoal;
import com.platform.mesh.utils.format.DateTimeUtil;
import com.platform.mesh.utils.format.TimeUnitEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 约定当前模块Manual 不引入当前模块Service,Manual是供Service引入，避免循环引入依赖
 * @description 客户关系目标
 * @author 蝉鸣
 */
@Service
public class CrmAllGoalServiceManual {

    /**
     * 功能描述:
     * 〈判断是否平均分配〉
     * @param allValue allValue
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public Boolean isAvg(List<BigDecimal> allValue) {
        //如果当前年度所有月份是平均分配目标则计算天目标进行保存，方便后续逻辑计算
        return allValue.stream().allMatch(e -> Objects.equals(e, allValue.getFirst()));
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoal crmAllGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getMonthGoal(CrmAllGoal crmAllGoal,LocalDate localDate) {
        Month month = localDate.getMonth();
        return getMonthGoal(crmAllGoal,month);
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoal crmAllGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getMonthGoal(CrmAllGoal crmAllGoal,Month month) {
        if(ObjectUtil.isEmpty(crmAllGoal)) {
            return BigDecimal.ZERO;
        }
        return switch (month) {
            case JANUARY -> crmAllGoal.getJanGoal();
            case FEBRUARY -> crmAllGoal.getFebGoal();
            case MARCH -> crmAllGoal.getMarGoal();
            case APRIL -> crmAllGoal.getAprGoal();
            case MAY -> crmAllGoal.getMayGoal();
            case JUNE -> crmAllGoal.getJunGoal();
            case JULY -> crmAllGoal.getJulGoal();
            case AUGUST -> crmAllGoal.getAugGoal();
            case SEPTEMBER -> crmAllGoal.getSepGoal();
            case OCTOBER -> crmAllGoal.getOctGoal();
            case NOVEMBER -> crmAllGoal.getNovGoal();
            case DECEMBER -> crmAllGoal.getDecGoal();
        };
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoal crmAllGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getQuarterGoal(CrmAllGoal crmAllGoal,YearQuarter yearQuarter) {
        if(ObjectUtil.isEmpty(crmAllGoal)) {
            return BigDecimal.ZERO;
        }
        Quarter quarter = yearQuarter.getQuarter();
        return switch (quarter) {
            case Q1 -> crmAllGoal.getJanGoal().add(crmAllGoal.getFebGoal()).add(crmAllGoal.getMarGoal());
            case Q2 -> crmAllGoal.getAprGoal().add(crmAllGoal.getMayGoal()).add(crmAllGoal.getJunGoal());
            case Q3 -> crmAllGoal.getJulGoal().add(crmAllGoal.getAugGoal()).add(crmAllGoal.getSepGoal());
            case Q4 -> crmAllGoal.getOctGoal().add(crmAllGoal.getNovGoal()).add(crmAllGoal.getDecGoal());
        };
    }

    /**
     * 功能描述:
     * 〈获取开始结束总目标〉
     * @param crmAllGoals crmAllGoals
     * @param startDate startDate
     * @param endDate endDate
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getTotalGoal(List<CrmAllGoal> crmAllGoals, LocalDate startDate, LocalDate endDate) {
        Map<Year, CrmAllGoal> goalMap = crmAllGoals.stream().collect(Collectors.toMap(CrmAllGoal::getYearTime, Function.identity(),(v1,v2)->v2));
        List<Year> yearPeriod = DateTimeUtil.getYearPeriod(startDate, endDate);
        //获取月份总目标
        BigDecimal sum = BigDecimal.ZERO;
        for (Year year : yearPeriod) {
            //获取年度目标
            if(goalMap.containsKey(year) && ObjectUtil.isEmpty(goalMap.get(year))) {
                continue;
            }
            CrmAllGoal yearGoal = goalMap.get(year);
            //获取年度开始时间
            LocalDate yearStartDate = DateTimeUtil.getYearStartDate(year);
            //获取年度结束时间
            LocalDate yearEndDate = DateTimeUtil.getYearEndDate(year);
            LocalDate countStartDate;
            LocalDate countEndDate;
            //如果开始时间大于年度开始时间，则以开始时间为起点
            if(startDate.isAfter(yearStartDate)) {
                countStartDate = startDate;
            }else {
                countStartDate = yearStartDate;
            }
            //如果结束时间小于于年度结束时间，则以结束时间为起点
            if(endDate.isBefore(yearEndDate)) {
                countEndDate = endDate;
            }else {
                countEndDate = yearEndDate;
            }

            //获取相差多少月份
            List<YearMonth> monthPeriod = DateTimeUtil.getMonthPeriod(countStartDate, countEndDate);
            for (YearMonth yearMonth : monthPeriod) {
                //获取当月总绩效
                BigDecimal monthGoal = getMonthGoal(yearGoal, yearMonth.getMonth());
                YearMonth countStartYearMonth = YearMonth.of(countStartDate.getYear(), countStartDate.getMonth());
                YearMonth countEndYearMonth = YearMonth.of(countEndDate.getYear(), countEndDate.getMonth());
                //如果使开始月份
                if(yearMonth.equals(countStartYearMonth)){
                    //当月天数
                    int lengthOfMonth = yearMonth.lengthOfMonth();
                    //实际天数
                    List<LocalDate> dayPeriod = DateTimeUtil.getDayPeriod(countStartDate, countStartYearMonth.atEndOfMonth());
                    //如果满月直接累计
                    if(lengthOfMonth == dayPeriod.size()) {
                        sum = sum.add(monthGoal);
                    }else{
                        //如果不满一个月
                        sum = sum.add(monthGoal
                                .divide(new BigDecimal(lengthOfMonth), NumberConst.NUM_2, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(dayPeriod.size())));
                    }
                } else if (yearMonth.equals(countEndYearMonth)) {
                    //当月天数
                    int lengthOfMonth = yearMonth.lengthOfMonth();
                    //实际天数
                    List<LocalDate> dayPeriod = DateTimeUtil.getDayPeriod(DateTimeUtil.getMonthStartDate(countEndDate), countEndDate);
                    //如果满月直接累计
                    if(lengthOfMonth == dayPeriod.size()) {
                        sum = sum.add(monthGoal);
                    }else{
                        //如果不满一个月
                        sum = sum.add(monthGoal
                                .divide(new BigDecimal(lengthOfMonth), NumberConst.NUM_2, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(dayPeriod.size())));
                    }
                }else{
                    sum = sum.add(monthGoal);
                }
            }
        }
        return sum;
    }
    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoals crmAllGoals
     * @param startDate startDate
     * @param endDate endDate
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getAvgDayGoal(List<CrmAllGoal> crmAllGoals, LocalDate startDate, LocalDate endDate) {
        BigDecimal totalGoal = getTotalGoal(crmAllGoals, startDate, endDate);
        //获取月份多少天数
        int length = DateTimeUtil.getPeriod(startDate, endDate).getDays();;
        return totalGoal.divide(new BigDecimal(length), NumberConst.NUM_2, RoundingMode.HALF_UP);
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoal crmAllGoal
     * @param month month
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getAvgDayGoal(CrmAllGoal crmAllGoal, Month month) {
        BigDecimal monthGoal = getMonthGoal(crmAllGoal, month);
        //获取当前月有多少天;
        int length = DateTimeUtil.getMonthDays(crmAllGoal.getYearTime(), month);
        return monthGoal.divide(new BigDecimal(length), NumberConst.NUM_2, RoundingMode.HALF_UP);
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param monthGoal monthGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getAvgDayGoal(BigDecimal monthGoal,Year year, Month month) {
        //获取当前月有多少天;
        int length = DateTimeUtil.getMonthDays(year, month);
        return monthGoal.divide(new BigDecimal(length), NumberConst.NUM_2, RoundingMode.HALF_UP);
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param yearGoal yearGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getAvgDayGoal(BigDecimal yearGoal, Year year) {
        //获取当前月有多少天;
        int length = year.length();
        return yearGoal.divide(new BigDecimal(length), NumberConst.NUM_2, RoundingMode.HALF_UP);
    }

    /**
     * 功能描述:
     * 〈获取添加时天目标〉
     * @param crmAllGoal crmAllGoal
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public BigDecimal getAddDayGoal(CrmAllGoal crmAllGoal) {
        //如果当前年度所有月份是平均分配目标则计算天目标进行保存，方便后续逻辑计算
        List<BigDecimal> goalValue =  CollUtil.newArrayList();
        goalValue.add(crmAllGoal.getJanGoal());
        goalValue.add(crmAllGoal.getFebGoal());
        goalValue.add(crmAllGoal.getMarGoal());
        goalValue.add(crmAllGoal.getAprGoal());
        goalValue.add(crmAllGoal.getMayGoal());
        goalValue.add(crmAllGoal.getJunGoal());
        goalValue.add(crmAllGoal.getJulGoal());
        goalValue.add(crmAllGoal.getAugGoal());
        goalValue.add(crmAllGoal.getSepGoal());
        goalValue.add(crmAllGoal.getOctGoal());
        goalValue.add(crmAllGoal.getNovGoal());
        goalValue.add(crmAllGoal.getDecGoal());
        if(isAvg(goalValue)){
            return getAvgDayGoal(crmAllGoal.getYearGoal(),crmAllGoal.getYearTime());
        }else{
            //否则返回空 视为非平均分配
            return null;
        }
    }

    /**
     * 功能描述:
     * 〈获取分隔后的目标〉
     * @param timeUnit timeUnit
     * @return 正常返回:{@link BigDecimal}
     * @author 蝉鸣
     */
    public Map<String,BigDecimal> getGoalMap(Integer timeUnit,LocalDate startDate,LocalDate endDate, List<CrmAllGoal> goalList) {
        Map<String, BigDecimal> map = new HashMap<>();
        Map<Year, CrmAllGoal> yearGoalMap = goalList.stream().collect(Collectors.toMap(CrmAllGoal::getYearTime, Function.identity()));
        if(TimeUnitEnum.YEAR.getValue().equals(timeUnit)){
            List<Year> yearPeriod = DateTimeUtil.getYearPeriod(startDate, endDate);
            yearPeriod.forEach(item->{
                if(yearGoalMap.containsKey(item)){
                    CrmAllGoal yearGoal = yearGoalMap.get(item);
                    map.put(StrUtil.toString(item.getValue()), yearGoal.getYearGoal());
                }else{
                    map.put(StrUtil.toString(item.getValue()), BigDecimal.ZERO);
                }
            });
            return map;
        }else if(TimeUnitEnum.QUARTER.getValue().equals(timeUnit)){
            List<YearQuarter> quarterPeriod = DateTimeUtil.getQuarterPeriod(startDate, endDate);
            quarterPeriod.forEach(item->{
                if(yearGoalMap.containsKey(Year.of(item.getYear()))){
                    CrmAllGoal yearGoal = yearGoalMap.get(Year.of(item.getYear()));
                    BigDecimal quarterGoal = getQuarterGoal(yearGoal, item);
                    map.put(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getQuarterValue())), quarterGoal);
                }else{
                    map.put(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getQuarterValue())), BigDecimal.ZERO);
                }
            });
            return map;
        }else if(TimeUnitEnum.MONTH.getValue().equals(timeUnit)){
            List<YearMonth> monthPeriod = DateTimeUtil.getMonthPeriod(startDate, endDate);
            monthPeriod.forEach(item->{
                if(yearGoalMap.containsKey(Year.of(item.getYear()))){
                    CrmAllGoal yearGoal = yearGoalMap.get(Year.of(item.getYear()));
                    BigDecimal monthGoal = getMonthGoal(yearGoal, item.getMonth());
                    map.put(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getMonth())), monthGoal);
                }else{
                    map.put(StrUtil.toString(item.getYear()).concat(SymbolConst.DASH).concat(StrUtil.toString(item.getMonth())), BigDecimal.ZERO);
                }
            });
            return map;
        }else{
            List<LocalDate> dayPeriod = DateTimeUtil.getDayPeriod(startDate, endDate);
            dayPeriod.forEach(item->{
                if(yearGoalMap.containsKey(Year.of(item.getYear()))){
                    CrmAllGoal yearGoal = yearGoalMap.get(Year.of(item.getYear()));
                    BigDecimal dayGoal = getAvgDayGoal(yearGoal, item.getMonth());
                    map.put(DateTimeUtil.localDateToStr2(item), dayGoal);
                }else{
                    map.put(DateTimeUtil.localDateToStr2(item), BigDecimal.ZERO);
                }
            });
            return map;
        }
    }
}