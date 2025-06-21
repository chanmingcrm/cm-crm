package com.platform.mesh.app.api.modules.app.util;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppFormColumnBO;
import com.platform.mesh.app.api.modules.app.domain.dto.DataEditSimpDTO;
import com.platform.mesh.app.api.modules.app.domain.po.AppDataPO;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.utils.excel.enums.DataTypeEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			data.setEditFormId(dataEditSimpDTO.getFormId());
			if(docData.containsKey(data.getColumnMac())) {
				Object value = docData.get(data.getColumnMac());
				String dataValue = BaseEnum.getEnumByValue(DataTypeEnum.class, data.getDataType(),DataTypeEnum.STRING).getDefaultValueStr(value);
				data.setDataValue(dataValue);
			}
		}
		return dataList;
	}

}