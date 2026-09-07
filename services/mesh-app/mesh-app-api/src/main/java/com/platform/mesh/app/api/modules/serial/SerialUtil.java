package com.platform.mesh.app.api.modules.serial;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.app.api.modules.app.domain.po.AppPO;
import com.platform.mesh.app.api.modules.app.util.AppUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.mybatis.plus.utils.SqlUtil;
import com.platform.mesh.app.api.modules.serial.domain.bo.ColumnCompBO;
import com.platform.mesh.app.api.modules.serial.domain.bo.SerialBO;
import com.platform.mesh.app.api.modules.serial.domain.bo.SerialSetBO;
import com.platform.mesh.app.api.modules.serial.enums.SerialReSetEnum;
import com.platform.mesh.app.api.modules.serial.enums.SerialTypeEnum;
import com.platform.mesh.app.api.modules.serial.type.SerialTypeService;
import com.platform.mesh.app.api.modules.serial.type.factory.SerialTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description 业务序列号生成
 * @author 蝉鸣
 */
@Component
@ConditionalOnClass(SerialTypeFactory.class)
public class SerialUtil {


	private static SerialTypeFactory serialTypeFactory;

	@Autowired
	public SerialUtil(SerialTypeFactory serialTypeFactory) {
		SerialUtil.serialTypeFactory = serialTypeFactory;
	}

	/**
	 * 功能描述:
	 * 〈生成序列号对象〉
	 * @author 蝉鸣
	 */
	public static <T extends AppPO> T genDataSerial(Class<T> clazz, List<ColumnCompBO> columnBOS, Map<String, Object> docData) {
		ColumnCompBO serialComp = getSerialComp(columnBOS);
		if(ObjectUtil.isNull(serialComp)) {
			docData.put(StrConst.DATA_MAC, IdUtil.getSnowflake().nextId());
			return BeanUtil.copyProperties(docData,clazz);
		}else{
			//只存在一组序列号组件
            Object setDataValue = serialComp.getSetDataValue();
			//获取重置规则
			SerialSetBO numSetBO = SerialUtil.getNumSetBO(setDataValue);
			LocalDateTime createTime = SerialUtil.getReSetTime(numSetBO);
			T maxOne = SqlUtil.getMaxOne(clazz,q->q
                    .eq(StrConst.MODULE_ID,serialComp.getModuleId())
                    .gt(StrConst.CREATE_TIME, createTime)
            );
			if(ObjectUtil.isNotEmpty(maxOne)){
				Map<String, Object> beanToMap = AppUtil.beanToMap(maxOne);
				if(beanToMap.containsKey(StrConst.DATA_SERIAL)){
					String maxSerial = beanToMap.get(StrConst.DATA_SERIAL).toString();
					docData.put(StrConst.DATA_SERIAL,maxSerial);
				}
			}
			SerialBO serialBO = SerialUtil.genSerialNO(setDataValue, docData);
			docData.put(StrConst.DATA_MAC,serialBO.getDataMac());
			docData.put(StrConst.DATA_SERIAL,serialBO.getDataSerial());
            if(ObjectUtil.isEmpty(docData.get(StrConst.DATA_NAME))){
                docData.put(StrConst.DATA_NAME,serialBO.getDataMac());
            }
            return BeanUtil.copyProperties(docData, clazz);
		}
	}

	/**
	 * 功能描述:
	 * 〈生成序列号对象〉
	 * @author 蝉鸣
	 */
	public static SerialBO genDataSerial(ColumnCompBO serialComp,Map<String, Object> beanToMap) {
		if(ObjectUtil.isNull(serialComp)) {
			return new SerialBO();
		}
		//只存在一组序列号组件
		Object setDataValue = serialComp.getSetDataValue();
		return SerialUtil.genSerialNO(setDataValue, beanToMap);
	}


	/***
	 * 功能描述:
	 * 〈获取数字序号重置开始时间〉
	 * @author 蝉鸣
	 * @since 2024/8/29 9:37
	 */
	private static SerialSetBO getNumSetBO(Object serialSet) {
		List<SerialSetBO> numSetBOS = JSONUtil.parseArray(serialSet).stream()
				.map(setObj -> BeanUtil.toBean(setObj, SerialSetBO.class))
				.filter(setBO -> NumberUtil.isNumber(setBO.getRule()) && SerialTypeEnum.NUM.getValue().equals(setBO.getType()))
				.toList();
		if(CollUtil.isNotEmpty(numSetBOS)){
			return CollUtil.getFirst(numSetBOS);
		}else{
			SerialSetBO serialSetBO = new SerialSetBO();
			//设置默认步长为1
			serialSetBO.setFormat(NumberConst.NUM_1.toString());
			serialSetBO.setRule(NumberConst.NUM_1.toString());
			return serialSetBO;
		}
	}

	/**
	 * 功能描述:
	 * 〈生成序列号对象〉
	 * @author 蝉鸣
	 */
	public static LocalDateTime getReSetTime(ColumnCompBO serialComp) {
		if(ObjectUtil.isNull(serialComp)) {
			return null;
		}
		//只存在一组序列号组件
		Object setDataValue = serialComp.getSetDataValue();
		//获取重置规则
		SerialSetBO numSetBO = SerialUtil.getNumSetBO(setDataValue);
		return SerialUtil.getReSetTime(numSetBO);
	}

	/***
	 * 功能描述:
	 * 〈获取数字序号重置开始时间〉
	 * @author 蝉鸣
	 * @since 2024/8/29 9:37
	 */
	private static LocalDateTime getReSetTime(SerialSetBO numSetBO) {
		if(ObjectUtil.isEmpty(numSetBO) || Objects.isNull(numSetBO.getRule())){
			return SerialReSetEnum.NONE.getBeginTime();
		}
		SerialReSetEnum enumByValue = BaseEnum.getEnumByValue(SerialReSetEnum.class, Integer.parseInt(numSetBO.getRule()));
		return enumByValue.getBeginTime();
	}

	/***
	 * 功能描述:
	 * 〈是否需要生成序列号〉
	 * @author 蝉鸣
	 * @since 2024/8/29 9:37
	 */
	public static ColumnCompBO getSerialComp(List<ColumnCompBO> columnBOS) {
        if(CollUtil.isEmpty(columnBOS)){
            //不包含序列号组件
            return null;
        }
		//同类型表单只能存在一个序列号组件
		List<ColumnCompBO> seCodes = columnBOS.stream().filter(comp -> comp.getCompMac().equals(StrConst.SE_CODE)).toList();
		if(CollUtil.isEmpty(seCodes)){
			//不包含序列号组件
			return null;
		}else{
			//包含序列号组件
			ColumnCompBO columnCompBO = CollUtil.getFirst(seCodes);
			//如果存在序列号组件，配置为空则视为手动输入编号，配置不为空则视为自动生成不可输入
			if(ObjectUtil.isEmpty(columnCompBO.getSetDataValue())){
				//不包含序列号组件
				return null;
			}else{
				//包含序列号组件
				return columnCompBO;
			}
		}
	}

	/***
	 * 功能描述:
	 * 〈将下划线转驼峰〉
	 * @author 蝉鸣
	 * @since 2024/8/29 9:37
	 */
	public static SerialBO genSerialNO(Object serialSet, Map<String, Object> instData) {
		SerialBO serialBO = new SerialBO();
		if(ObjectUtil.isEmpty(serialSet)) {
			return serialBO;
		}
		StringBuilder builder = StrUtil.builder();
		JSONArray setArray = JSONUtil.parseArray(serialSet);
		for (Object setObj : setArray) {
			SerialSetBO setBO = BeanUtil.toBean(setObj, SerialSetBO.class);
			SerialTypeEnum enumByValue = BaseEnum.getEnumByValue(SerialTypeEnum.class, setBO.getType());
			SerialTypeService serialTypeService = serialTypeFactory.getSerialTypeService(enumByValue);
			String serial = serialTypeService.handle(setBO, instData);
			if(SerialTypeEnum.NUM.getValue().equals(setBO.getType())) {
				serialBO.setDataSerial(Integer.parseInt(serial));
			}
			builder.append(serial);
		}
		serialBO.setDataMac(builder.toString());
		return serialBO;
	}


}
