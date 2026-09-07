package com.platform.mesh.app.api.modules.app.util;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.app.api.modules.app.enums.comp.CompMacEnum;
import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.bpm.ProcessPassEnum;
import com.platform.mesh.core.enums.bpm.ProcessRunEnum;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.datascope.domain.ScopeBO;
import com.platform.mesh.datascope.utils.DataScopeUtil;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.upms.api.modules.dict.base.domian.bo.DictBaseValueBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.ModifyDataBO;
import com.platform.mesh.utils.excel.enums.CompTypeEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;
import com.platform.mesh.utils.format.TimeUnitEnum;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description 应用工具类
 * @author 蝉鸣
 */
public class AppUtil {

	/**
	 * 功能描述:
	 * 〈获取唯一字段〉
	 * @author 蝉鸣
	 */
	public static Map<String, Object> getUniqueMap(List<AppFormColumnBO> columnBOS) {
		if(CollUtil.isEmpty(columnBOS)) {
			return new HashMap<>();
		}
		//获取唯一类型字段Map
		return columnBOS.stream()
				.filter(column -> ObjectUtil.isNotEmpty(column.getUniqueFlag()) && column.getUniqueFlag().equals(YesOrNoEnum.YES.getValue()))
				.collect(Collectors.toMap(AppFormColumnBO::getColumnMac, AppFormColumnBO::getColumnName));
	}

	/**
	 * 功能描述:
	 * 〈获取唯一字段附带值的数据〉
	 * @author 蝉鸣
	 */
	public static Map<String, Object> checkUniqueMap(List<AppFormColumnBO> columnBOS, Map<String, Object> docMap) {
		if(CollUtil.isEmpty(columnBOS)) {
			return new HashMap<>();
		}
		//获取唯一类型字段Map
		return  columnBOS.stream()
				.filter(column -> ObjectUtil.isNotEmpty(column.getUniqueFlag()) && column.getUniqueFlag().equals(YesOrNoEnum.YES.getValue()))
				.collect(Collectors.toMap(AppFormColumnBO::getColumnMac, column -> docMap.getOrDefault(column.getColumnMac(), StrUtil.EMPTY)));
	}

	/**
	 * 功能描述:
	 * 〈获取唯一字段附带值的数据〉
	 * @author 蝉鸣
	 */
	public static Map<String, String> checkEmptyMap(List<AppFormColumnBO> columnBOS, Map<String, Object> docMap) {
		if(CollUtil.isEmpty(columnBOS)) {
			return new HashMap<>();
		}
		//获取唯一类型字段Map
		return  columnBOS.stream()
				.filter(column -> (ObjectUtil.isNotEmpty(column.getEmptyFlag()) && column.getEmptyFlag().equals(YesOrNoEnum.YES.getValue())))
				.filter(column -> ObjectUtil.isEmpty(docMap.get(column.getColumnMac())) || StrUtil.isBlank(docMap.get(column.getColumnMac()).toString()))
				.collect(Collectors.toMap(AppFormColumnBO::getColumnMac, AppFormColumnBO::getColumnName));
	}

	/**
	 * 功能描述:
	 * 〈获取唯一字段附带值的数据〉
	 * @author 蝉鸣
	 */
	public static Map<String, String> checkUserMap(List<AppFormColumnBO> columnBOS, Set<String> ignores, Map<String, Object> docMap) {
		if(CollUtil.isEmpty(columnBOS)) {
			return new HashMap<>();
		}
		//获取人员权限数据为空
		return  columnBOS.stream()
				.filter(column -> CompTypeEnum.USER.getDesc().equals(column.getCompMac()))
				.filter(column -> !ignores.contains(column.getColumnMac()))
				.filter(column -> ObjectUtil.isEmpty(docMap.get(column.getColumnMac())) || StrUtil.isBlank(docMap.get(column.getColumnMac()).toString()))
				.collect(Collectors.toMap(AppFormColumnBO::getColumnMac, AppFormColumnBO::getColumnName));
	}

	/**
	 * 功能描述:
	 * 〈获取唯一字段附带值的数据〉
	 * @author 蝉鸣
	 */
	public static Map<String, String> checkOrgMap(List<AppFormColumnBO> columnBOS, Set<String> ignores, Map<String, Object> docMap) {
		if(CollUtil.isEmpty(columnBOS)) {
			return new HashMap<>();
		}
		//获取组织权限数据为空
		return  columnBOS.stream()
				.filter(column -> CompTypeEnum.DEP.getDesc().equals(column.getCompMac()))
				.filter(column -> !ignores.contains(column.getColumnMac()))
				.filter(column -> ObjectUtil.isEmpty(docMap.get(column.getColumnMac())) || StrUtil.isBlank(docMap.get(column.getColumnMac()).toString()))
				.collect(Collectors.toMap(AppFormColumnBO::getColumnMac, AppFormColumnBO::getColumnName));
	}

	/**
	 * 功能描述:
	 * 〈修改信息转换Db Data列表对象〉
	 * @param dataList dataList
	 * @param dataEditSimpDTO dataEditSimpDTO
	 * @author 蝉鸣
	 */
	public static <D extends AppDataPO> List<D> editDbData(List<D> dataList, DataEditSimpDTO dataEditSimpDTO) {
		if(CollUtil.isEmpty(dataList)) {
			return dataList;
		}
		Map<String, Object> docData = dataEditSimpDTO.getDocData();
		for (D data : dataList) {
//			data.setEditFormId(dataEditSimpDTO.getFormId());
			if(docData.containsKey(data.getColumnMac())) {
				Object value = docData.get(data.getColumnMac());
				String dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, data.getDataType(),DataTypeEnum.STRING).getDefaultValueStr(value);
				data.setDataValue(dataValue);
			}
		}
		return dataList;
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化〉
	 * @author 蝉鸣
	 */
    public static List<ModifyDataBO> getDiffData(List<AppFormColumnBO> columnBOS, Map<String, Object> oldMap, Map<String, Object> newMap) {
		List<ModifyDataBO> modifyDataBOS = CollUtil.newArrayList();
		if(ObjectUtil.isNull(columnBOS) || ObjectUtil.isNull(oldMap) || ObjectUtil.isNull(newMap)){
			return modifyDataBOS;
		}
        //过滤子表字段
        List<Long> childTableIds = columnBOS.stream().filter(columnBO -> columnBO.getCompMac().equals(CompMacEnum.CHILD_TABLE.getDesc())).map(AppFormColumnBO::getId).toList();
        Map<String, AppFormColumnBO> columnBOMap = columnBOS.stream()
                .filter(columnBO->!childTableIds.contains(columnBO.getId()))
                .filter(columnBO->!childTableIds.contains(columnBO.getParentId()))
                .collect(Collectors.toMap(AppFormColumnBO::getColumnMac, Function.identity()));
		// 使用Set操作优化差异比较
		Set<String> allKeys = new HashSet<>();
		allKeys.addAll(oldMap.keySet());
		allKeys.addAll(newMap.keySet());
		for (String key : allKeys) {
			//值
			Object oldValue = null;
			Object newValue = null;
			if(oldMap.containsKey(key)){
				oldValue = oldMap.get(key);
			}
			if(newMap.containsKey(key)){
				newValue = newMap.get(key);
			}
			if(columnBOMap.containsKey(key) && (!Objects.equals(toStr(oldValue), toStr(newValue)))){
				ModifyDataBO modifyDataBO = getModifyDataBO(key, columnBOMap, oldValue, newValue);
				modifyDataBOS.add(modifyDataBO);
			}
		}
		return modifyDataBOS;
	}

	/**
	 * 功能描述:
	 * 〈转化数值〉
	 * @author 蝉鸣
	 */
	private static String toStr(Object obj) {
		if(isEmpty(obj)){
			return StrUtil.EMPTY;
		}
		return JSONUtil.toJsonStr(obj);
	}

	/**
	 * 功能描述:
	 * 〈转化数值〉
	 * @author 蝉鸣
	 */
	private static Boolean isEmpty(Object obj) {
		if (obj == null) return Boolean.TRUE;
		String str = obj.toString().trim();
		// 将 null、{}、[]、"" 都转为空字符串
		if (str.isEmpty() || str.equalsIgnoreCase(StrUtil.NULL) || str.equals("{}") || str.equals("[]")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static ModifyDataBO getModifyDataBO(String key,Map<String, AppFormColumnBO> columnBOMap, Object oldValue, Object newValue){
		ModifyDataBO modifyDataBO = new ModifyDataBO();
		modifyDataBO.setColumnMac(key);
		if(ObjectUtil.isEmpty(oldValue)){
			modifyDataBO.setOperateType(OperateTypeEnum.INSERT.getValue());
		} else if (ObjectUtil.isNotEmpty(oldValue) && ObjectUtil.isEmpty(newValue)) {
			modifyDataBO.setOperateType(OperateTypeEnum.DELETE.getValue());
		}else{
			modifyDataBO.setOperateType(OperateTypeEnum.UPDATE.getValue());
		}
		//旧值
		if(JSONUtil.isTypeJSONArray(toStr(oldValue))){
			oldValue = JSONUtil.parseArray(toStr(oldValue));
		} else if (JSONUtil.isTypeJSONObject(toStr(oldValue))) {
			oldValue = JSONUtil.parseObj(toStr(oldValue));
		} else{
			oldValue = toStr(oldValue);
		}
		//新增值
		if(JSONUtil.isTypeJSONArray(toStr(newValue))){
			newValue = JSONUtil.parseArray(toStr(newValue));
		} else if (JSONUtil.isTypeJSONObject(toStr(newValue))) {
			newValue = JSONUtil.parseObj(toStr(newValue));
		}else{
			newValue = toStr(newValue);
		}
		modifyDataBO.setValueOld(oldValue);
		modifyDataBO.setValueNew(newValue);
		if(ObjectUtil.isNotNull(columnBOMap) && columnBOMap.containsKey(key)) {
			AppFormColumnBO columnBO = columnBOMap.get(key);
			modifyDataBO.setCompMac(columnBO.getCompMac());
			modifyDataBO.setColumnName(columnBO.getColumnName());
			modifyDataBO.setDataType(columnBO.getDefaultDataType());
		}
		return modifyDataBO;
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static String getJsonName(String column){
        return column.concat(EsConst.MAPPING_SUFFIX_JSON);
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static String getIdName(String column){
        return column.concat(EsConst.MAPPING_SUFFIX_ID);
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static String getArrayName(String column){
        return column.replace(EsConst.MAPPING_SUFFIX_JSON,EsConst.MAPPING_SUFFIX_ARRAY);
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static Object getColumnValue(String column, Map<String, Object> docData){
		if(docData.containsKey(column)) {
			return docData.get(column);
		}
		String jsonName = AppUtil.getJsonName(column);
        if(docData.containsKey(jsonName)) {
			return docData.get(jsonName);
        }
        return null;
	}

	/**
	 * 功能描述:
	 * 〈获取新旧参数变化对象〉
	 * @author 蝉鸣
	 */
	public static Long getSingleColumnIdValue(String column, Map<String, Object> docData){
        //数组形式
        Object object = getColumnValue(column, docData);
        if(ObjectUtil.isNull(object)) {
            return null;
        }
        //解析对象并获取ID值
        List<Long> ids = JSONUtil.parseArray(object).stream()
                .filter(ObjectUtil::isNotEmpty)
                .map(item -> {
                    JSONObject parseObj = JSONUtil.parseObj(item);
                    if (ObjectUtil.isNotEmpty(parseObj) && parseObj.containsKey(StrConst.ID)) {
                        return MapUtil.getLong(parseObj, StrConst.ID);
                    }
                    return null;
                })
                .filter(ObjectUtil::isNotEmpty)
                .toList();
        if(CollUtil.isEmpty(ids)) {
            return null;
        }
        return CollUtil.getFirst(ids);
	}

	/**
	 * 功能描述:
	 * 〈获取获取key所有值〉
	 * @author 蝉鸣
	 */
	public static List<String> getListColumnNameValue(String column, Map<String, Object> docData) {
		//数组形式
		Object object = getColumnValue(column, docData);
		if(ObjectUtil.isNull(object)) {
			return CollUtil.newArrayList();
		}
		//解析对象并获取ID值
		return JSONUtil.parseArray(object).stream()
				.filter(ObjectUtil::isNotEmpty)
				.map(item -> {
					JSONObject parseObj = JSONUtil.parseObj(item);
					if (ObjectUtil.isNotEmpty(parseObj) && parseObj.containsKey(StrConst.NAME)) {
						return MapUtil.getStr(parseObj, StrConst.NAME);
					}
					return null;
				})
				.filter(ObjectUtil::isNotEmpty)
				.toList();

	}

	/**
	 * 功能描述:
	 * 〈获取JSON 中的ID ，用于聚合查询〉
	 * @author 蝉鸣
	 */
	public static List<String> getUniColumnIdValue(String column, Object value) {
		//数组形式
		if(ObjectUtil.isNull(value)) {
			return CollUtil.newArrayList();
		}
		if(!JSONUtil.isTypeJSONArray(JSONUtil.toJsonStr(value))){
			return CollUtil.newArrayList();
		}
		//解析对象并获取ID值
		return JSONUtil.parseArray(value).stream()
				.filter(ObjectUtil::isNotEmpty)
				.map(item -> {
					JSONObject parseObj = JSONUtil.parseObj(item);
					if (ObjectUtil.isNotEmpty(parseObj) && parseObj.containsKey(StrConst.ID)) {
						String id = MapUtil.getStr(parseObj, StrConst.ID);
						return column.concat(SymbolConst.COLON).concat(StrConst.ID).concat(SymbolConst.COLON).concat(id);
					}
					return null;
				})
				.filter(ObjectUtil::isNotEmpty)
				.toList();
	}

	/**
	 * 功能描述:
	 * 〈获取JSON 中的Name ，用于聚合查询〉
	 * @author 蝉鸣
	 */
	public static List<String> getUniColumnNameValue(String column, Object value) {
		//数组形式
		if(ObjectUtil.isNull(value)) {
			return CollUtil.newArrayList();
		}
		if(!JSONUtil.isTypeJSONArray(JSONUtil.toJsonStr(value))){
			return CollUtil.newArrayList();
		}
		//解析对象并获取ID值
		return JSONUtil.parseArray(value).stream()
				.filter(ObjectUtil::isNotEmpty)
				.map(item -> {
					JSONObject parseObj = JSONUtil.parseObj(item);
					if (ObjectUtil.isNotEmpty(parseObj) && parseObj.containsKey(StrConst.NAME)) {
						String name = MapUtil.getStr(parseObj, StrConst.NAME);
						return column.concat(SymbolConst.COLON).concat(StrConst.NAME).concat(SymbolConst.COLON).concat(name);
					}
					return null;
				})
				.filter(ObjectUtil::isNotEmpty)
				.toList();

	}

    /**
     * 功能描述:
     * 〈获取列表对象〉
     * @author 蝉鸣
     */
    public static <T>  List<T> getListColumnValue(Class<T> clazz, String column, Map<String, Object> docData) {
        //数组形式
        Object object = getColumnValue(column, docData);
        if(ObjectUtil.isNull(object)) {
            return CollUtil.newArrayList();
        }
        return JSONUtil.toList(JSONUtil.parseArray(object), clazz);
    }

    /**
     * 功能描述:
     * 〈将对象转换成Map〉
     * @author 蝉鸣
     */
    public static Map<String,Object> beanToMap(Object bean){
        CopyOptions copyOptions = CopyOptions.create()
                .setIgnoreNullValue(Boolean.TRUE)
                .setFieldNameEditor(StrUtil::toUnderlineCase)
                .setFieldValueEditor((fieldName, fieldValue) -> {
                    if (fieldValue instanceof LocalDateTime) {
                        // 将LocalDateTime转换为指定格式字符串
                        return LocalDateTimeUtil.format((LocalDateTime) fieldValue, DatePattern.NORM_DATETIME_FORMAT.getDateTimeFormatter());
                    }
                    return fieldValue;
                });
        Map<String, Object> dataMap = new HashMap<>();
        BeanUtil.beanToMap(bean, dataMap, copyOptions);
        return dataMap;
    }

    /**
     * 功能描述:
     * 〈获取流程审批状态〉
     * @author 蝉鸣
     */
    public static JSONArray getProcessPassEsData(Integer processPass){
        JSONArray array = JSONUtil.createArray();
        if(ObjectUtil.isEmpty(processPass)){
            return array;
        }
		//获取字典值
		DictBaseValueBO dictByMac = UserCacheUtil.getSysDictByMac(EsConst.BPM_PROCESS_PASS, processPass);
		String name = StrUtil.EMPTY;
		String color = StrUtil.EMPTY;
		if(ObjectUtil.isEmpty(dictByMac)){
			ProcessRunEnum runEnum = BaseEnum.getEnumByValue(ProcessRunEnum.class, processPass);
			if(ObjectUtil.isEmpty(runEnum)){
				//流程未结束
				ProcessPassEnum passEnum = BaseEnum.getEnumByValue(ProcessPassEnum.class, processPass);
				if(ObjectUtil.isNotEmpty(passEnum)){
					name = passEnum.getDesc();
				}
			}else{
				name = runEnum.getDesc();
			}
		}else{
			name = dictByMac.getDictName();
			color = dictByMac.getDictColor();
		}
		JSONObject jsonObject = JSONUtil.createObj();
		jsonObject.set(StrConst.ID, processPass);
		jsonObject.set(StrConst.NAME, name);
		jsonObject.set(StrConst.DATA_COLOR, color);
		array.add(jsonObject);
        return array;
    }

    /**
     * 功能描述:
     * 〈获取流程审批阶段〉
     * @author 蝉鸣
     */
    public static Object getExtendJsonValue(Map<String,Object> extendJson,String key){
		if(CollUtil.isEmpty(extendJson)){
			return SymbolConst.BLANK;
		}
		if(extendJson.containsKey(key)){
			return ObjectUtil.isEmpty(extendJson.get(key))? SymbolConst.BLANK:extendJson.get(key).toString();
		}
		return SymbolConst.BLANK;
    }

	/**
	 * ES数据类型
	 * @param compMac compMac
	 * @param columnMac columnMac
	 */
	public static Aggregate.Kind getEsAggregateKind(String compMac, String columnMac){

		if(columnMac.endsWith(EsConst.MAPPING_SUFFIX_JSON)){
			return Aggregate.Kind.Nested;
		} else if (isTimeRelated(compMac,columnMac)) {
			return Aggregate.Kind.DateHistogram;
		} else if (compMac.equals(CompMacEnum.NUMBER.getDesc()) || columnMac.endsWith(EsConst.MAPPING_SUFFIX_NUM)) {
			return Aggregate.Kind.Stats;
		}else {
			return Aggregate.Kind.Sterms;
		}
	}

	/**
	 * 提取时间判断为独立方法
	 * @param compMac compMac
	 * @param columnMac columnMac
	 */
	private static boolean isTimeRelated(String compMac, String columnMac) {
		return compMac.equals(CompMacEnum.DATE.getDesc()) ||
				compMac.equals(CompMacEnum.TIME.getDesc()) ||
				compMac.equals(CompMacEnum.DATE_TIME.getDesc()) ||
				columnMac.endsWith(EsConst.MAPPING_SUFFIX_DATE) ||
				columnMac.endsWith(EsConst.MAPPING_SUFFIX_TIME);
	}

	/**
	 * 功能描述:
	 * 〈解析BI搜索参数〉
	 * @param biDTO biDTO
	 * @author 蝉鸣
	 */
	public static BiDTO parseBiDTO(BiDTO biDTO) {
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
	 * 〈批量修改ES参数〉
	 * @param dataPage dataPage
	 * @param moduleIndex moduleIndex
	 * @param dataId dataId
	 * @param name name
	 * @author 蝉鸣
	 */
    public static <D extends AppDataPO> List<EsDocPutBO> getBatchEsDocPut(MPage<D> dataPage,String moduleIndex, Long dataId, Object name) {
		if(CollUtil.isEmpty(dataPage.getRecords())){
			return CollUtil.newArrayList();
		}
		List<EsDocPutBO> docs = CollUtil.newArrayList();
		for (D data : dataPage.getRecords()) {
			//解析数据
			Object dataValue = data.getDataValue();
			if(ObjectUtil.isEmpty(dataValue)){
				continue;
			}
			JSONArray newArray = new JSONArray();
			JSONArray jsonArray = JSONUtil.parseArray(dataValue);
			for (Object obj : jsonArray) {
				JSONObject entries = JSONUtil.parseObj(obj);
				if (entries.containsKey(StrConst.ID) && dataId.equals(Long.parseLong(entries.get(StrConst.ID).toString()))) {
					entries.set(StrConst.NAME, name);
				}
				newArray.add(entries);
			}
			data.setDataValue(newArray);
			EsDocPutBO esDocPutBO = new EsDocPutBO();
			esDocPutBO.setIndexName(moduleIndex);
			esDocPutBO.setDataIds(CollUtil.newArrayList(data.getDataId()));
			Map<String, Object> docMap = new HashMap<>();
			docMap.put(data.getColumnMac(),newArray);
			esDocPutBO.setDocMap(docMap);
			docs.add(esDocPutBO);
		}
		return docs;
    }

	/**
	 * 功能描述:
	 * 〈组装数据〉
	 * @param id id
	 * @param name name
	 * @author 蝉鸣
	 */
	public static JSONArray getJsonData(Long id,String name){
		JSONArray array = JSONUtil.createArray();
		JSONObject object = JSONUtil.createObj();
		object.set(StrConst.ID, id);
		object.set(StrConst.NAME, name);
		array.add(object);
		return array;
	}

	/**
	 * 功能描述:
	 * 〈获取包含key值的key形式〉R/crm/db/trans/data
	 * @author 蝉鸣
	 */
	public static String getMapKey(Map<String, ?> docData,String key) {
		if(docData.containsKey(key)){
			return key;
		}
		if(docData.containsKey(StrUtil.toUnderlineCase(key))){
			return StrUtil.toUnderlineCase(key);
		}
		return null;
	}

}
