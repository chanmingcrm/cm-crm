package com.platform.mesh.utils.format;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.YearQuarter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.DateConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description 时间格式工具
 * @author 蝉鸣
 */
public class DateTimeUtil{

	private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

	/**
	 * 功能描述:
	 * 〈获取当前时间〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localTimeNow() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateConst.HH_MM_SS));
	}

	/**
	 * 功能描述:
	 * 〈获取当前时间〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateNow() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD));
	}

	/**
	 * 功能描述:
	 * 〈获取当前时间〉
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateTimeNow() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS));
	}

	/**
	 * 功能描述:
	 * 〈获取当前时间〉
	 * @param format format
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateTimeNow(String format) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * 功能描述:
	 * 〈Object转时间字符串〉
	 * @param object object
	 * @param format format
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String parseDateToStr(Object object, String format) {
		if(ObjectUtil.isEmpty(object)){
			return StrUtil.EMPTY;
		}
		if(ObjectUtil.isEmpty(format)){
			format = DateConst.YYYY_MM_DD_HH_MM_SS;
		}
		if (object instanceof Date) {
			return dateToStr(strToUtilDate(object.toString(),format));
		}
		else if (object instanceof LocalDateTime) {
			return localDateTimeToStr(strToLocalDateTime(object.toString()));
		}
		else if (object instanceof LocalDate) {
			return localDateToStr1(strToLocalDate(object.toString()));
		}
		return object.toString();
	}

	/**
	 * 功能描述: 
	 * 〈日期型字符串转化为日期 格式〉
	 * @param str str  
	 * @return 正常返回:{@link Date}
	 * @author 蝉鸣
	 */
	public static Date toDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return DateUtils.parseDate(str.toString(), DateConst.PARSE_PATTERNS);
		}
		catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 功能描述: 
	 * 〈增加 LocalDateTime ==> Date〉
	 * @param localDateTime localDateTime  
	 * @return 正常返回:{@link Date}
	 * @author 蝉鸣
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	/**
	 * 功能描述: 
	 * 〈增加 LocalDate ==> Date〉
	 * @param localDate localDate  
	 * @return 正常返回:{@link Date}
	 * @author 蝉鸣
	 */
	public static Date toDate(LocalDate localDate) {
		LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.of(NumberConst.NUM_0, NumberConst.NUM_0, NumberConst.NUM_0));
        return toDate(localDateTime);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * strDate 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
	 * dateFormat 传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
	 * @author 蝉鸣
	 */
	public static Date strToUtilDate (String strDate, String dateFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sf.parse(strDate);
		} catch (ParseException e) {
			log.error("DateTimeUtil -> strToUtilDate exception message", e);
		}
		return date;
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * strDate 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
	 * dateFormat 传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
	 * @author 蝉鸣
	 */
	public static Timestamp strToSqlDate (String strDate, String dateFormat) {
		if(StrUtil.isBlank(strDate)){
			return null;
		}
		Date date = strToUtilDate(strDate, dateFormat);
		if(ObjectUtil.isEmpty(date) || BeanUtil.isEmpty(date)){
			return null;
		}
		return new Timestamp(date.getTime());
	}


	/**
	 * 功能描述:
	 * 〈将java.util.Date对象转化为java.sql.Timestamp对象〉
	 * date 要转化的java.util.Date对象
	 * @return 转化后的java.sql.Timestamp对象
	 * @author 蝉鸣
	 */
	public static Timestamp longToSqlTime(long timestamp) {
		return new Timestamp(timestamp);
	}

	/**
	 * 功能描述:
	 * 〈将java.util.Date对象转化为java.sql.Timestamp对象〉
	 * date 要转化的java.util.Date对象
	 * @return 转化后的java.sql.Timestamp对象
	 * @author 蝉鸣
	 */
	public static Timestamp dateToSqlTime(Date date) {
		String strDate = dateToStr(date, DateConst.YYYY_MM_DD_HH_MM_SS_SSS);
		return strToSqlDate(strDate, DateConst.YYYY_MM_DD_HH_MM_SS_SSS);
	}

	/**
	 * 功能描述:
	 * 〈将java.sql.Timestamp对象转化为String字符串〉
	 * time 要格式的java.sql.Timestamp对象
	 * @return strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
	 * @author 蝉鸣
	 */
	public static String sqlTimeToStr(Timestamp time, String strFormat) {
		if(ObjectUtil.isEmpty(time)){
			return StrUtil.EMPTY;
		}
		DateFormat df = new SimpleDateFormat(strFormat);
		return df.format(time);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param date date
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String dateToStr(Date date){
		//格式化查询日期
		String dateStr = StrUtil.EMPTY;
		if(ObjectUtil.isNotEmpty(date)){
			dateStr = DateUtil.formatDateTime(date);
		}
		return dateStr;
	}

	/**
	 * 功能描述:
	 * 〈将java.util.Date对象转化为String字符串〉
	 * date 要格式的java.util.Date对象
	 * @return strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
	 * @author 蝉鸣
	 */
	public static String dateToStr(Date date, String strFormat) {
		SimpleDateFormat sf = new SimpleDateFormat(strFormat);
		return sf.format(date);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param localDateTime localDateTime
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateTimeToStr(LocalDateTime localDateTime){
		//格式化查询日期
		if(ObjectUtil.isEmpty(localDateTime)){
			return StrUtil.EMPTY;
		}
		return localDateTime.format(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS));
	}

	/**
	 * 功能描述:
	 * 〈日期格式转换 月-日〉
	 * @param localDate localDate
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateToStr1(LocalDate localDate){
		if(ObjectUtil.isEmpty(localDate)){
			return StrUtil.EMPTY;
		}
		return localDate.getMonthValue() + SymbolConst.DASH + localDate.getDayOfMonth();
	}

	/**
	 * 功能描述:
	 * 〈日期格式转换 年-月-日〉
	 * @param localDate localDate
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localDateToStr2(LocalDate localDate){
		if(ObjectUtil.isEmpty(localDate)){
			return StrUtil.EMPTY;
		}
		return localDate.format(DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD));
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param localTime localTime
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String localTimeToStr(LocalTime localTime){
		//格式化查询日期
		DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConst.HH_MM_SS);
		String dateStr = StrUtil.EMPTY;
		if(ObjectUtil.isNotEmpty(localTime)){
			dateStr = df.format(localTime);
		}
		return dateStr;
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param utcTime utcTime
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String strUTCFormat(String utcTime){

		LocalDateTime dateTime = strUTCToLocalDateTime(utcTime);
		DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS);
		return dateTime.format(df);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param utcTime utcTime
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDateTime strUTCToLocalDateTime(String utcTime){
		if(StrUtil.isEmpty(utcTime)){
			return LocalDateTime.now();
		}
		Instant instant = ZonedDateTime.parse(utcTime).toInstant();
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param dateTime dateTime
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDateTime strToLocalDateTime(String dateTime){

		DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD_HH_MM_SS);
		return LocalDateTime.parse(dateTime,df);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * strDate 将String字符串转换为java.sql.Timestamp格式日期,用于数据库保存
	 * dateFormat 传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
	 * @return java.sql.Timestamp类型日期对象（如果转换失败则返回null）
	 * @author 蝉鸣
	 */
	public static LocalDateTime sqlDateToLocalDateTime (Timestamp sqlTime) {
		return sqlTime.toLocalDateTime();
	}


	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param dateTime dateTime
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDate strToLocalDate(String dateTime){

		DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConst.YYYY_MM_DD);
		return LocalDate.parse(dateTime,df);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param dateTime dateTime
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalTime strToLocalTime(String dateTime){

		DateTimeFormatter df = DateTimeFormatter.ofPattern(DateConst.HH_MM_SS);
		return LocalTime.parse(dateTime,df);
	}

	/**
	 * 功能描述:
	 * 〈格式化日期〉
	 * @param utcTime utcTime
	 * @return 正常返回:{@link Integer}
	 * @author 蝉鸣
	 */
	public static Integer getMonthValue(String utcTime){
		LocalDateTime dateTime = strUTCToLocalDateTime(utcTime);
		Month month = dateTime.getMonth();
		return month.getValue();
	}

	/**
	 * 功能描述:
	 * 〈获取月份集合〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<String>}
	 * @author 蝉鸣
	 */
	public static List<String> getAllMonths(LocalDate startDate, LocalDate endDate) {
		List<String> list = CollUtil.newArrayList();
		long distance = ChronoUnit.MONTHS.between(startDate, endDate);
		Stream.iterate(startDate, d -> d.plusMonths(NumberConst.NUM_1)).limit(distance + NumberConst.NUM_1).forEach(f -> {
			list.add(f.toString());
		});
		return list;
	}

	/**
	 * 功能描述:
	 * 〈获取月份日期集合〉
	 * @param date date
	 * @return 正常返回:{@link List<LocalDate>}
	 * @author 蝉鸣
	 */
	public static List<LocalDate> getMonthAllDays(LocalDate date) {
		//获取当前天月开始日期
		LocalDate monthStartDate = getMonthStartDate(date);
		//获取当前天月结束日期
		LocalDate monthEndDate = getMonthEndDate(date);
		//获取间隔天数
		return getDayPeriod(monthStartDate, monthEndDate);
	}

	/**
	 * 功能描述:
	 * 〈获取天的开始日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDateTime getDayStartDateTime(LocalDate date){
		return LocalDateTime.of(date, LocalTime.MIN);
	}

	/**
	 * 功能描述:
	 * 〈获取天的结束日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDateTime getDayEndDateTime(LocalDate date){
		return LocalDateTime.of(date, LocalTime.MAX);

	}

	/**
	 * 功能描述:
	 * 〈获取日期的周开始日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getWeekStartDate(LocalDate date){
		return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	}

	/**
	 * 功能描述:
	 * 〈获取日期的周结束日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getWeekEndDate(LocalDate date){
		return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
	}

	/**
	 * 功能描述:
	 * 〈获取日期的月开始日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getMonthStartDate(LocalDate date){
		return date.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 功能描述:
	 * 〈获取日期的月结束日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getMonthEndDate(LocalDate date){
		return date.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 功能描述:
	 * 〈获取日期的季度开始日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getQuarterStartDate(LocalDate date){
		Month month = date.getMonth();
		Month firstMonthOfQuarter = month.firstMonthOfQuarter();
		return LocalDate.of(date.getYear(), firstMonthOfQuarter, NumberConst.NUM_1);
	}

	/**
	 * 功能描述:
	 * 〈获取日期的季度结束日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getQuarterEndDate(LocalDate date){
		Month month = date.getMonth();
		Month firstMonthOfQuarter = month.firstMonthOfQuarter();
		Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + NumberConst.NUM_2);
		return LocalDate.of(date.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(date.isLeapYear()));
	}

	/**
	 * 功能描述:
	 * 〈获取日期的年开始日期〉
	 * @param year year
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getYearStartDate(Year year){
		return LocalDate.of(year.getValue(), Month.JANUARY, NumberConst.NUM_1);
	}

	/**
	 * 功能描述:
	 * 〈获取日期的年开始日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getYearStartDate(LocalDate date){
		return LocalDate.of(date.getYear(), Month.JANUARY, NumberConst.NUM_1);
	}

	/**
	 * 功能描述:
	 * 〈获取日期的年结束日期〉
	 * @param year year
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getYearEndDate(Year year){
		return LocalDate.of(year.getValue(), Month.DECEMBER, Month.DECEMBER.length(year.isLeap()));
	}

	/**
	 * 功能描述:
	 * 〈获取日期的年结束日期〉
	 * @param date date
	 * @return 正常返回:{@link LocalDate}
	 * @author 蝉鸣
	 */
	public static LocalDate getYearEndDate(LocalDate date){
		return LocalDate.of(date.getYear(), Month.DECEMBER, Month.DECEMBER.length(date.isLeapYear()));
	}

	/**
	 * 功能描述:
	 * 〈获取日期期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<String>}
	 * @author 蝉鸣
	 */
	public static List<String> getDayStrPeriod(LocalDate startDate, LocalDate endDate){
		List<String> dateList = CollUtil.newArrayList();

		if (ObjectUtil.isEmpty(endDate)){
			endDate = LocalDate.now();
		}
		if(ObjectUtil.isEmpty(startDate)){
			startDate = endDate.minusDays(NumberConst.NUM_7);
		}
		long days = endDate.toEpochDay() - startDate.toEpochDay();
		if(ObjectUtil.isEmpty(days) || days < NumberConst.NUM_0){
			return dateList;
		}
		return Stream.iterate(startDate, date -> date.plusDays(NumberConst.NUM_1))
				.limit(ChronoUnit.DAYS.between(startDate, endDate)+NumberConst.NUM_1)
				.map(DateTimeUtil::localDateToStr2)
				.collect(Collectors.toList());
	}

	/**
	 * 功能描述:
	 * 〈获取日期期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<LocalDate>}
	 * @author 蝉鸣
	 */
	public static List<LocalDate> getDayPeriod(LocalDate startDate, LocalDate endDate){
		List<LocalDate> dateList = CollUtil.newArrayList();

		if (ObjectUtil.isEmpty(endDate)){
			endDate = LocalDate.now();
		}
		if(ObjectUtil.isEmpty(startDate)){
			startDate = endDate.minusDays(NumberConst.NUM_7);
		}
		long days = endDate.toEpochDay() - startDate.toEpochDay();
		if(ObjectUtil.isEmpty(days) || days < NumberConst.NUM_0){
			return dateList;
		}
		return Stream.iterate(startDate, date -> date.plusDays(NumberConst.NUM_1))
				.limit(ChronoUnit.DAYS.between(startDate, endDate)+NumberConst.NUM_1)
				.collect(Collectors.toList());
	}

	/**
	 * 功能描述:
	 * 〈获取月份期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<LocalDate>}
	 * @author 蝉鸣
	 */
	public static List<YearMonth> getMonthPeriod(LocalDate startDate, LocalDate endDate){
		List<YearMonth> dateList = CollUtil.newArrayList();

		if (ObjectUtil.isEmpty(endDate)){
			endDate = LocalDate.now();
		}
		if(ObjectUtil.isEmpty(startDate)){
			startDate = endDate.minusDays(NumberConst.NUM_7);
		}
		long days = endDate.toEpochDay() - startDate.toEpochDay();
		if(ObjectUtil.isEmpty(days) || days < NumberConst.NUM_0){
			return dateList;
		}
		return Stream.iterate(startDate, date -> date.plusMonths(NumberConst.NUM_1))
				.limit(ChronoUnit.MONTHS.between(startDate, endDate)+NumberConst.NUM_1)
				.map(item->YearMonth.of(item.getYear(), item.getMonth()))
				.collect(Collectors.toList());

	}

	/**
	 * 功能描述:
	 * 〈获取季度期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<LocalDate>}
	 * @author 蝉鸣
	 */
	public static List<YearQuarter> getQuarterPeriod(LocalDate startDate, LocalDate endDate){
		List<YearMonth> monthPeriod = getMonthPeriod(startDate, endDate);
		return monthPeriod.stream().map(YearQuarter::of).distinct().toList();
	}

	/**
	 * 功能描述:
	 * 〈获取年度期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link List<LocalDate>}
	 * @author 蝉鸣
	 */
	public static List<Year> getYearPeriod(LocalDate startDate, LocalDate endDate){
		List<Year> dateList = CollUtil.newArrayList();

		if (ObjectUtil.isEmpty(endDate)){
			endDate = LocalDate.now();
		}
		if(ObjectUtil.isEmpty(startDate)){
			startDate = endDate.minusDays(NumberConst.NUM_7);
		}
		long days = endDate.toEpochDay() - startDate.toEpochDay();
		if(ObjectUtil.isEmpty(days) || days < NumberConst.NUM_0){
			return dateList;
		}
		return Stream.iterate(startDate, date -> date.plusYears(NumberConst.NUM_1))
				.limit(ChronoUnit.YEARS.between(startDate, endDate)+NumberConst.NUM_1)
				.map(item->Year.of(item.getYear()))
				.collect(Collectors.toList());
	}

	/**
	 * 功能描述:
	 * 〈获取日期期间数据〉
	 * @param startDate startDate
	 * @param endDate endDate
	 * @return 正常返回:{@link Period}
	 * @author 蝉鸣
	 */
	public static Period getPeriod(LocalDate startDate, LocalDate endDate){

		if (ObjectUtil.isEmpty(endDate)){
			endDate = LocalDate.now();
		}
		if(ObjectUtil.isEmpty(startDate)){
			startDate = endDate.minusDays(NumberConst.NUM_7);
		}
		long days = endDate.toEpochDay() - startDate.toEpochDay();
		if(ObjectUtil.isEmpty(days) || days < NumberConst.NUM_0){
			return Period.between(LocalDate.now(), LocalDate.now());
		}
		return Period.between(startDate, endDate);
	}

	/**
	 * 功能描述:
	 * 〈获取月份集合〉
	 * @return 正常返回:{@link Map}
	 * @author 蝉鸣
	 */
	public static Map<Integer, String> getAllMonthStr() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1,"一月份");
		map.put(2,"二月份");
		map.put(3,"三月份");
		map.put(4,"四月份");
		map.put(5,"五月份");
		map.put(6,"六月份");
		map.put(7,"七月份");
		map.put(8,"八月份");
		map.put(9,"九月份");
		map.put(10,"十月份");
		map.put(11,"十一月份");
		map.put(12,"十二月份");
		return map;
	}

	/**
	 * 功能描述:
	 * 〈计算两个时间差〉
	 * @param endDate endDate
	 * @param nowDate nowDate
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	/**
	 * 功能描述:
	 * 〈判断是否是闰年〉
	 * @param year year
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isLeapYear(Year year) {
		return (year.getValue() % 4 == 0 && year.getValue() % 100!= 0) || (year.getValue() % 400 == 0);
	}

	/**
	 * 功能描述:
	 * 〈判断是否是闰年〉
	 * @param year year
	 * @param month month
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static int getMonthDays(Year year,Month month) {
		YearMonth yearMonth = YearMonth.of(year.getValue(), month);
		return yearMonth.lengthOfMonth();
	}

	/**
	 * 功能描述:
	 * 〈判断是否是日期〉
	 * @param string string
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
    public static boolean isDate(String string) {
		try {
			DateUtil.parse(string, DateConst.PARSE_PATTERNS);
			return true;
		} catch (Exception e) {
			return false;
		}
    }



	/**
	 * 功能描述:
	 * 〈获取下次提醒时间〉
	 * @param localDateTime localDateTime
	 * @param value value
	 * @param unit unit
	 * @return 正常返回:{@link LocalDateTime}
	 * @author 蝉鸣
	 */
	public static LocalDateTime getNextTime(LocalDateTime localDateTime,Integer value,Integer unit) {
		LocalDateTime now = LocalDateTime.now();
		if(ObjectUtil.isEmpty(localDateTime) || localDateTime.isBefore(now)){
			localDateTime = now;
		}
		if(ObjectUtil.isEmpty(unit)){
			return localDateTime;
		}
		TimeUnitEnum enumByValue = BaseEnum.getEnumByValue(TimeUnitEnum.class, unit);
		return switch (enumByValue){
			case YEAR -> localDateTime.plusYears(value);
			case MONTH -> localDateTime.plusMonths(value);
			case WEEK -> localDateTime.plusWeeks(value);
			case DAY -> localDateTime.plusDays(value);
			case HOUR -> localDateTime.plusHours(value);
			case MINUTE -> localDateTime.plusMinutes(value);
			case SECOND -> localDateTime.plusSeconds(value);
			default -> localDateTime;
		};
	}
}
